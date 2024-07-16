package site.xindu.afdian.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ReactiveSettingFetcher;
import run.halo.app.theme.finders.Finder;
import site.xindu.afdian.entity.SponsorEntity;
import site.xindu.afdian.service.AfdianFinder;
import site.xindu.afdian.service.AfdianService;
import site.xindu.afdian.utils.DataUtils;

@RequiredArgsConstructor
@Finder("afdianFinder")
public class AfdianFinderImpl implements AfdianFinder {

    @Autowired
    private AfdianService afdianService;

    private final ReactiveSettingFetcher settingFetcher;

    private static final String THEME_SETTING = "themeSetting";

    /**
     * 获取全部赞助者信息
     *
     * @return {@link SponsorEntity}
     */
    @Override
    public Mono<JsonNode> listSponsor(int pageNumber) {
        var mono = afdianService.getSponsorList(pageNumber);
        return DataUtils.changePayTime(mono);
    }

    /**
     * 获取全部赞助者信息
     *
     * @return {@link SponsorEntity}
     */
    @Override
    public Mono<JsonNode> listAllSponsor() {
        var mono = afdianService.listAllSponsor();
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
        if (length == 10) {
            timestamp = timestamp * 1000;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date(timestamp));
    }

    /**
     * 获取设置的标记金额
     *
     * @return 标记金额
     */
    @Override
    public Mono<Double> getRewardNumber() {
        return this.settingFetcher.get(THEME_SETTING).map(setting ->
                setting.get("sponsorNumber").asDouble(66.0)
            )
            .defaultIfEmpty(66.0);
    }

    /**
     * 获取全部赞助方案、商品、VIP服务
     *
     * @return 全部赞助方案、商品、VIP服务
     */
    @Override
    public Mono<JsonNode> listAllSponsorship() {
        return afdianService.listAllSponsorship();
    }

    /**
     * 获取全部赞助方案、商品列表
     *
     * @return 全部赞助方案、商品列表
     */
    @Override
    public Mono<JsonNode> listPlansAndSales() {
        return afdianService.listPlansAndSales();
    }

    /**
     * 获取全部作品集
     *
     * @return 全部作品集
     */
    @Override
    public Mono<JsonNode> listAlbum() {
        return afdianService.listAlbum();
    }
}
