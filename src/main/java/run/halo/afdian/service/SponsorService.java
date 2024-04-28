package run.halo.afdian.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import run.halo.afdian.utils.EncryptUtils;
import run.halo.app.plugin.ReactiveSettingFetcher;
import java.util.Calendar;

@Service
@Slf4j
public class SponsorService {

    private final ReactiveSettingFetcher settingFetcher;

    public SponsorService(ReactiveSettingFetcher settingFetcher) {
        this.settingFetcher = settingFetcher;
    }

    /**
     * 获取第一页的爱发电赞助用户
     *
     */
    public Mono<JsonNode> getSponsorList() {
        return this.settingFetcher.get("basic").flatMap(base ->{
            String token = base.get("token").asText();
            String userId = base.get("userId").asText();
            String url = "https://afdian.net/api/open/query-sponsor";
            JSONObject params = new JSONObject();
            var timeInMillis = Calendar.getInstance().getTimeInMillis() / 1000;

            var sign = token.concat("params{\"page\":1}ts")
                .concat(String.valueOf(timeInMillis)).concat("user_id").concat(userId);

            String signMd5 = EncryptUtils.encrypt32(sign);

            params.set("user_id", userId);
            params.set("params", "{\"page\":1}");
            params.set("ts", timeInMillis);
            params.set("sign", signMd5);

            var execute = HttpRequest.post(url).body(params.toString()).execute();
            var body = execute.body();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = null;
            try {
                jsonNode = objectMapper.readTree(body);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            log.info(jsonNode.toString());
            return Mono.just(jsonNode);
        });

    }
}
