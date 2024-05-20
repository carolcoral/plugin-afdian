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
    public Mono<JsonNode> listSponsor(String pageNumber) {
        return sponsorService.getSponsorList(pageNumber);
    }

    /**
     * 获取全部赞助者信息
     *
     * @return {@link SponsorEntity}
     */
    @Override
    public Mono<JsonNode> listAllSponsor() {
        List<SponsorEntity.SponsorJsonData> sources = new ArrayList<>();
        var firstSponsorList = sponsorService.getFirstSponsorList();
        firstSponsorList.doOnSuccess(result -> {
            SponsorEntity sponsorEntity =
                JsonUtils.jsonToObject(result.asText(), SponsorEntity.class);
            var data = sponsorEntity.getData();
            int totalPage = data.getTotal_page();
            List<SponsorEntity.SponsorJsonData> dataList = data.getList();
            sources.addAll(dataList);
            for (int i = 2; i <= totalPage; i++) {
                var sponsorList = sponsorService.getSponsorList(String.valueOf(i));
                sponsorList.doOnSuccess(jsonNode -> {
                    SponsorEntity sponsor =
                        JsonUtils.jsonToObject(jsonNode.asText(), SponsorEntity.class);
                    sources.addAll(sponsor.getData().getList());
                });
            }
            data.setList(sources);
        });
        return firstSponsorList;
    }
}
