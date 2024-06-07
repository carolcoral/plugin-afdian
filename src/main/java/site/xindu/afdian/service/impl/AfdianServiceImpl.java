package site.xindu.afdian.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import run.halo.app.infra.utils.JsonUtils;
import run.halo.app.plugin.ReactiveSettingFetcher;
import site.xindu.afdian.entity.SponsorEntity;
import site.xindu.afdian.service.AfdianService;
import site.xindu.afdian.utils.DataUtils;
import site.xindu.afdian.utils.EncryptUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AfdianServiceImpl implements AfdianService {

    private final ReactiveSettingFetcher settingFetcher;

    public AfdianServiceImpl(ReactiveSettingFetcher settingFetcher) {
        this.settingFetcher = settingFetcher;
    }

    // 创建webClient
    private WebClient webClient = WebClient.builder().baseUrl("https://afdian.net").build();

    /**
     * 分页获取爱发电赞助用户
     *
     * @param pageNumber 页码
     * @return 赞助用户集合
     */
    @Override
    public Mono<JsonNode> getSponsorList(int pageNumber) {
        var mono = this.settingFetcher.get("basic").flatMap(base -> {
            String token = base.get("token").asText();
            String userId = base.get("userId").asText();
            String url = "/api/open/query-sponsor";
            Map<String, Object> params = new HashMap<>();
            var timeInMillis = Calendar.getInstance().getTimeInMillis() / 1000;

            var sign = token.concat("params{\"page\":1}ts").concat(String.valueOf(timeInMillis))
                .concat("user_id").concat(userId);

            String signMd5 = EncryptUtils.encrypt32(sign);

            params.put("user_id", userId);
            params.put("params", "{\"page\":" + String.valueOf(pageNumber) + "}");
            params.put("ts", timeInMillis);
            params.put("sign", signMd5);

            return webClient.post().uri(url).contentType(MediaType.APPLICATION_JSON)  // JSON数据类型
                .body(BodyInserters.fromValue(params))  // JSON字符串数据
                .retrieve() // 获取响应体
                .bodyToMono(JsonNode.class);
        });
        return DataUtils.changePayTime(mono);
    }

    /**
     * 获取全部爱发电赞助用户
     *
     * @return 全部赞助用户
     */
    @Override
    public Mono<JsonNode> listAllSponsor() {
        List<SponsorEntity.SponsorJsonData> sources = new ArrayList<>();
        var firstSponsorList = getSponsorList(1);
        firstSponsorList.doOnSuccess(result -> {
            SponsorEntity sponsorEntity =
                JsonUtils.jsonToObject(result.asText(), SponsorEntity.class);
            var data = sponsorEntity.getData();
            int totalPage = data.getTotal_page();
            List<SponsorEntity.SponsorJsonData> dataList = data.getList();
            sources.addAll(dataList);
            for (int i = 2; i <= totalPage; i++) {
                var sponsorList = getSponsorList(i);
                sponsorList.doOnSuccess(jsonNode -> {
                    SponsorEntity sponsor =
                        JsonUtils.jsonToObject(jsonNode.asText(), SponsorEntity.class);
                    sources.addAll(sponsor.getData().getList());
                }).subscribe();
            }
            data.setList(sources);
        });
        return DataUtils.changePayTime(firstSponsorList);
    }

    /**
     * 获取全部赞助方案
     *
     * @return 全部赞助方案JSON
     */
    @Override
    public Mono<JsonNode> listSponsorshipProgram() {
        return null;
    }
}
