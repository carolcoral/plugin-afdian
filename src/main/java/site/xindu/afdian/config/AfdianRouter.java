package site.xindu.afdian.config;

import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ReactiveSettingFetcher;
import run.halo.app.theme.TemplateNameResolver;

@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class AfdianRouter {

    private final TemplateNameResolver templateNameResolver;

    private final ReactiveSettingFetcher settingFetcher;

    private static final String THEME_SETTING = "themeSetting";
    private static final String BASIC = "basic";
    private static final String SHOP = "shop";

    @Bean
    RouterFunction<ServerResponse> momentRouterFunction() {
        return RouterFunctions.route().GET("/afdian", this::renderPage).build();
    }

    Mono<ServerResponse> renderPage(ServerRequest request) {
        // 准备需要提供给模板的默认数据
        var model = new HashMap<String, Object>();
        defaultModel(model);
        return this.settingFetcher.get(THEME_SETTING).flatMap(setting -> {
            String themeStyle = setting.get("themeStyle").asText();
            if (StringUtils.isEmpty(themeStyle)) {
                themeStyle = "afdian";
            }
            extracted(model, themeStyle);
            return templateNameResolver.resolveTemplateNameOrDefault(request.exchange(), themeStyle)
                .flatMap(templateName -> ServerResponse.ok().render(templateName, model));
        });

    }

    private void defaultModel(HashMap<String, Object> model) {
        Mono<String> sponsorUrl =
            this.settingFetcher.get(THEME_SETTING).map(setting ->
                setting.get("sponsorUrl").asText()
            ).defaultIfEmpty("https://afdian.com/a/carolcoral");
        model.put("sponsorUrl", sponsorUrl);
        Mono<Double> sponsorNumber =
            this.settingFetcher.get(THEME_SETTING).map(setting ->
                setting.get("sponsorNumber").asDouble()
            ).defaultIfEmpty(66.00);
        model.put("sponsorNumber", sponsorNumber);
        Mono<String> rewardTopTitle = this.settingFetcher.get(THEME_SETTING).map(setting ->
            setting.get("rewardTopTitle").asText()
        ).defaultIfEmpty("感谢我的赞赏者们");
        model.put("rewardTopTitle", rewardTopTitle);
        Mono<String> baseUrl =
            this.settingFetcher.get(BASIC).map(setting ->
                setting.get("baseUrl").asText()
            ).defaultIfEmpty("https://afdian.com");
        model.put("baseUrl", baseUrl);
    }

    private void extracted(HashMap<String, Object> model, String theme) {
        // 不同样式存在不同的默认值
        switch (theme) {
            case "afdian2":
                Mono<String> rewardTopImgUrl =
                    this.settingFetcher.get(THEME_SETTING).map(setting ->
                        setting.get("rewardTopImgUrl").asText()
                    ).defaultIfEmpty(
                        "https://youimg1.c-ctrip.com/target/100m1b000001bj6if96CC.jpg");
                model.put("rewardTopImgUrl", rewardTopImgUrl);
                Mono<Boolean> enableRewardRoll =
                    this.settingFetcher.get(THEME_SETTING).map(setting ->
                        setting.get("enableRewardRoll").asBoolean()
                    ).defaultIfEmpty(false);
                model.put("enableRewardRoll", enableRewardRoll);
                break;
            case "afdian3":
                Mono<String> theme3ImgUrl =
                    this.settingFetcher.get(THEME_SETTING).map(setting ->
                        setting.get("theme3ImgUrl").asText()
                    ).defaultIfEmpty(
                        "https://youimg1.c-ctrip.com/target/100m1b000001bj6if96CC.jpg");
                model.put("theme3ImgUrl", theme3ImgUrl);
                Mono<Boolean> enableTheme3Img =
                    this.settingFetcher.get(THEME_SETTING).map(setting ->
                        setting.get("enableTheme3Img").asBoolean()
                    ).defaultIfEmpty(false);
                model.put("enableTheme3Img", enableTheme3Img);
                break;
            default:
                break;
        }
    }

    // 爱发电商品展示

    @Bean
    RouterFunction<ServerResponse> momentRouterFunction2() {
        return RouterFunctions.route().GET("/afdian/shop", this::renderPage2).build();
    }

    Mono<ServerResponse> renderPage2(ServerRequest request) {
        // 准备需要提供给模板的默认数据
        var model = new HashMap<String, Object>();
        defaultModel(model);
        return this.settingFetcher.get(SHOP).flatMap(setting -> {
            String themeStyle = "shop1";
            extractedShop(model);
            return templateNameResolver.resolveTemplateNameOrDefault(request.exchange(), themeStyle)
                .flatMap(templateName -> ServerResponse.ok().render(templateName, model));
        });
    }

    private void extractedShop(HashMap<String, Object> model) {
        Mono<String> basicTitle =
            this.settingFetcher.get(SHOP).map(setting ->
                setting.get("basic_title").asText()
            ).defaultIfEmpty("爱发电商品展示");
        model.put("afdian_shop_basic_title", basicTitle);
        Mono<String> secondTitle =
            this.settingFetcher.get(SHOP).map(setting ->
                setting.get("second_title").asText()
            ).defaultIfEmpty("services");
        model.put("afdian_shop_second_title", secondTitle);
    }

}
