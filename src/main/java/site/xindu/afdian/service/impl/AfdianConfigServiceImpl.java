package site.xindu.afdian.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ReactiveSettingFetcher;
import site.xindu.afdian.service.AfdianConfigService;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.ObjectNode;

@Service
@Slf4j
public class AfdianConfigServiceImpl implements AfdianConfigService {

    private final ReactiveSettingFetcher settingFetcher;

    public AfdianConfigServiceImpl(ReactiveSettingFetcher settingFetcher) {
        this.settingFetcher = settingFetcher;
    }

    private static final String THEME_SETTING = "themeSetting";

    /**
     * 获取插件配置信息
     *
     * @return 插件配置信息（敏感信息已处理）
     */
    @Override
    public Mono<JsonNode> getConfig() {
        return this.settingFetcher.getSettingValue(THEME_SETTING).map(res -> {
            var properties = ((ObjectNode) res).properties();
            for (var next : properties) {
                var key = next.getKey();
                var value = next.getValue();
                if ("userId".equalsIgnoreCase(key) || "token".equalsIgnoreCase(key)) {
                    next.setValue(JsonNodeFactory.instance.nullNode());
                } else {
                    next.setValue(value);
                }
            }
            return res;
        });
    }
}
