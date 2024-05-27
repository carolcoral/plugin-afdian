package site.xindu.afdian.finder.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import run.halo.app.infra.utils.JsonUtils;
import run.halo.app.theme.finders.Finder;
import site.xindu.afdian.entity.SponsorEntity;
import site.xindu.afdian.finder.AfdianFinder;
import site.xindu.afdian.service.SponsorService;
import site.xindu.afdian.utils.DataUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Finder("afdianFinder")
public class AfdianFinderImpl implements AfdianFinder {

    @Autowired
    private SponsorService sponsorService;

    /**
     * 获取全部赞助者信息
     *
     * @return {@link SponsorEntity}
     */
    @Override
    public Mono<JsonNode> listSponsor(int pageNumber) {
        var mono = sponsorService.getSponsorList(pageNumber);
        return DataUtils.changePayTime(mono);
    }

    /**
     * 获取全部赞助者信息
     *
     * @return {@link SponsorEntity}
     */
    @Override
    public Mono<JsonNode> listAllSponsor() {
        var mono = sponsorService.listAllSponsor();
        return DataUtils.changePayTime(mono);
    }

    /**
     * 格式化时间 yyyy-MM-dd
     *
     * @param timestamp 时间戳
     * @return time String
     */
    @Override
    public String parseTime(Long timestamp) {
        var length = timestamp.toString().length();
        if (length == 10){
            timestamp = timestamp * 1000;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date(timestamp));
    }
}
