package site.xindu.afdian.service;

import tools.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;

/**
 * 配置信息服务
 */
public interface AfdianConfigService {

    /**
     * 获取插件配置信息
     * @return 插件配置信息（敏感信息已处理）
     */
    Mono<JsonNode> getConfig();

}
