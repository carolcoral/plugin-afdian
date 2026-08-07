package site.xindu.afdian.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ReactiveSettingFetcher;
import run.halo.app.theme.finders.Finder;
import site.xindu.afdian.entity.SaleListEntity;
import site.xindu.afdian.entity.SponsorEntity;
import site.xindu.afdian.service.AfdianFinder;
import site.xindu.afdian.service.AfdianService;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

@RequiredArgsConstructor
@Finder("afdianFinder")
public class AfdianFinderImpl implements AfdianFinder {

    @Autowired
    private AfdianService afdianService;

    private final ReactiveSettingFetcher settingFetcher;

    private static final JsonMapper JSON_MAPPER = JsonMapper.builder().build();

    private static final String THEME_SETTING = "themeSetting";

    /**
     * 获取全部赞助者信息
     *
     * @return {@link SponsorEntity}
     */
    @Override
    public Mono<SponsorEntity> listSponsor(int pageNumber) {
        return afdianService.getSponsorList(pageNumber).map(this::toSponsorEntity);
    }

    /**
     * 获取全部赞助者信息
     *
     * @return {@link SponsorEntity}
     */
    @Override
    public Mono<SponsorEntity> listAllSponsor() {
        return afdianService.listAllSponsor().map(this::toSponsorEntity);
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
        return this.settingFetcher.getSettingValue(THEME_SETTING).map(setting ->
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
    public Mono<Map<String, Object>> listAllSponsorship() {
        return afdianService.listAllSponsorship().map(this::toMap);
    }

    /**
     * 获取全部赞助方案、商品列表
     *
     * @return 全部赞助方案、商品列表
     */
    @Override
    public Mono<SaleListEntity> listPlansAndSales() {
        return afdianService.listPlansAndSales().map(node -> {
            try {
                return JSON_MAPPER.treeToValue(node, SaleListEntity.class);
            } catch (JacksonException e) {
                throw new RuntimeException("解析赞助方案数据出现异常!", e);
            }
        });
    }

    /**
     * 获取全部作品集
     *
     * @return 全部作品集
     */
    @Override
    public Mono<Map<String, Object>> listAlbum() {
        return afdianService.listAlbum().map(this::toMap);
    }

    private SponsorEntity toSponsorEntity(JsonNode node) {
        try {
            return JSON_MAPPER.treeToValue(node, SponsorEntity.class);
        } catch (JacksonException e) {
            throw new RuntimeException("解析赞助者数据出现异常!", e);
        }
    }

    private Map<String, Object> toMap(JsonNode node) {
        try {
            return JSON_MAPPER.treeToValue(node,
                new tools.jackson.core.type.TypeReference<Map<String, Object>>() {
                });
        } catch (JacksonException e) {
            throw new RuntimeException("解析数据出现异常!", e);
        }
    }
}
