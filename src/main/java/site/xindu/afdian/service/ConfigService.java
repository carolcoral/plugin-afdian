package site.xindu.afdian.service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import run.halo.app.infra.utils.JsonUtils;
import run.halo.app.plugin.ReactiveSettingFetcher;
import site.xindu.afdian.config.BaseSettingConfig;

@Service
@Slf4j
public class ConfigService {

    private final ReactiveSettingFetcher settingFetcher;

    public ConfigService(ReactiveSettingFetcher settingFetcher) {
        this.settingFetcher = settingFetcher;
    }

    private static final String BASIC = "basic";

    public Mono<JsonNode> getConfig() {
        return this.settingFetcher.get(BASIC).map(res -> {
            var fields = res.fields();
            while (fields.hasNext()) {
                var next = fields.next();
                var key = next.getKey();
                var value = next.getValue();
                try {
                    if ("sponsorUrl".equalsIgnoreCase(key)) {
                        next.setValue(value);
                    } else if ("sponsorNumber".equalsIgnoreCase(key)) {
                        next.setValue(value);
                    } else {
                        next.setValue(new ObjectMapper().readTree(""));
                    }
                } catch (JsonProcessingException e) {
                    log.error("转换Config 数据出现异常!", e);
                }
            }
            return res;
        });
    }

}
