package site.xindu.afdian.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import run.halo.app.infra.utils.JsonUtils;
import run.halo.app.theme.finders.Finder;
import site.xindu.afdian.entity.SponsorEntity;
import site.xindu.afdian.service.AfdianFinderService;
import site.xindu.afdian.service.SponsorService;
import java.util.ArrayList;
import java.util.List;

@Finder("afdianFinder")
public class AfdianFinderServiceImpl implements AfdianFinderService {

    @Autowired
    private SponsorService sponsorService;

    /**
     * 获取全部赞助者信息
     *
     * @return {@link SponsorEntity}
     */
    @Override
    public Mono<JsonNode> listSponsor(int pageNumber) {
        return sponsorService.getSponsorList(pageNumber);
    }

    /**
     * 获取全部赞助者信息
     *
     * @return {@link SponsorEntity}
     */
    @Override
    public Mono<JsonNode> listAllSponsor() {
        return sponsorService.listAllSponsor();
    }
}
