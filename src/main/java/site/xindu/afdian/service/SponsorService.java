package site.xindu.afdian.service;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import site.xindu.afdian.utils.EncryptUtils;
import run.halo.app.plugin.ReactiveSettingFetcher;

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
    public Mono<JsonNode> getSponsorList() {
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
            params.put("params", "{\"page\":1}");
            params.put("ts", timeInMillis);
            params.put("sign", signMd5);

            var body =
                webClient.post().uri(url).contentType(MediaType.APPLICATION_JSON)  // JSON数据类型
                    .body(BodyInserters.fromValue(params))  // JSON字符串数据
                    .retrieve() // 获取响应体
                    .bodyToMono(JsonNode.class);// 响应数据类型转换
            log.info(body.toString());
            return body;
        });

    }
}
