package site.xindu.afdian.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ReactiveSettingFetcher;
import site.xindu.afdian.service.AfdianConfigService;

@Service
@Slf4j
public class AfdianConfigServiceImpl implements AfdianConfigService {

    private final ReactiveSettingFetcher settingFetcher;

    public AfdianConfigServiceImpl(ReactiveSettingFetcher settingFetcher) {
        this.settingFetcher = settingFetcher;
    }

    private static final String BASIC = "basic";

    /**
     * 获取插件配置信息
     *
     * @return 插件配置信息（敏感信息已处理）
     */
    @Override
    public Mono<JsonNode> getConfig() {
        return this.settingFetcher.get(BASIC).map(res -> {
            var fields = res.fields();
            while (fields.hasNext()) {
                var next = fields.next();
                var key = next.getKey();
                var value = next.getValue();
                try {
                    if ("userId".equalsIgnoreCase(key)) {
                        next.setValue(new ObjectMapper().readTree(""));
                    } else if ("token".equalsIgnoreCase(key)) {
                        next.setValue(new ObjectMapper().readTree(""));
                    } else {
                        next.setValue(value);
                    }
                } catch (JsonProcessingException e) {
                    log.error("转换Config 数据出现异常!", e);
                }
            }
            return res;
        });
    }
}
