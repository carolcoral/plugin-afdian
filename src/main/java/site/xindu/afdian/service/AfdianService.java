package site.xindu.afdian.service;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;

/**
 * 爱发电APP相关服务
 */
public interface AfdianService {

    /**
     * 分页获取爱发电赞助用户
     * @param pageNumber 页码
     * @return 赞助用户集合
     */
    Mono<JsonNode> getSponsorList(int pageNumber);

    /**
     * 获取全部爱发电赞助用户
     * @return 全部赞助用户
     */
    Mono<JsonNode> listAllSponsor();


    /**
     * 获取全部赞助方案
     * @return 全部赞助方案JSON
     */
    Mono<JsonNode> listSponsorshipProgram();



}
