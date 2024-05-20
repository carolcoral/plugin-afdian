package site.xindu.afdian.service;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import run.halo.app.infra.utils.JsonUtils;
import run.halo.app.plugin.ReactiveSettingFetcher;
import site.xindu.afdian.entity.SponsorEntity;
import site.xindu.afdian.utils.EncryptUtils;

@Service
@Slf4j
public class SponsorService {

    private final ReactiveSettingFetcher settingFetcher;

    public SponsorService(ReactiveSettingFetcher settingFetcher) {
        this.settingFetcher = settingFetcher;
    }

    // 创建webClient
    private WebClient webClient = WebClient.builder().baseUrl("https://afdian.net").build();

    /**
     * 获取第一页的爱发电赞助用户
     */
    public Mono<JsonNode> getSponsorList(int pageNumber) {
        return this.settingFetcher.get("basic").flatMap(base -> {
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
    }

    /**
     * 获取全部赞助者信息
     *
     * @return {@link SponsorEntity}
     */
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
        return firstSponsorList;
    }
}
