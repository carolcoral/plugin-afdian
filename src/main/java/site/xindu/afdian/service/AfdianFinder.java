package site.xindu.afdian.service;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;
import site.xindu.afdian.entity.SponsorEntity;

/**
 * 为主题提供爱发电Finder接口
 */
public interface AfdianFinder {

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

    /**
     * 格式化时间 yyyy-MM-dd
     * @param timestamp 时间戳
     * @return time String
     */
    String parseTime(Long timestamp);

    /**
     * 获取设置的标记金额
     * @return 标记金额
     */
    Mono<Double> getRewardNumber();

    /**
     * 获取全部赞助方案、商品、VIP服务
     * @return 全部赞助方案、商品、VIP服务
     */
    Mono<JsonNode> listAllSponsorship();


    /**
     * 获取全部赞助方案、商品列表
     * @return 全部赞助方案、商品列表
     */
    Mono<JsonNode> listPlansAndSales();

    /**
     * 获取全部作品集
     * @return 全部作品集
     */
    Mono<JsonNode> listAlbum();

}
