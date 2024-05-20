package site.xindu.afdian.service;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;
import site.xindu.afdian.entity.SponsorEntity;

/**
 * 为主题提供爱发电Finder接口
 */
public interface AfdianFinderService {

    /**
     * 分页获取赞助者信息
     * @return {@link SponsorEntity}
     */
    Mono<JsonNode> listSponsor(int pageNumber);

    /**
     * 获取全部赞助者信息
     * @return {@link SponsorEntity}
     */
    Mono<JsonNode> listAllSponsor();

}
