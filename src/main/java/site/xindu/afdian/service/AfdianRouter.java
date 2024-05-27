package site.xindu.afdian.service;

import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import run.halo.app.infra.utils.JsonUtils;
import run.halo.app.plugin.ReactiveSettingFetcher;
import run.halo.app.theme.TemplateNameResolver;
import site.xindu.afdian.entity.SponsorEntity;
import site.xindu.afdian.finder.AfdianFinder;

@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class AfdianRouter {

    private final TemplateNameResolver templateNameResolver;

    private final AfdianFinder afdianFinder;

    private final ReactiveSettingFetcher settingFetcher;

    @Bean
    RouterFunction<ServerResponse> momentRouterFunction() {
        return RouterFunctions.route().GET("/afdian", this::renderPage).build();
    }

    Mono<ServerResponse> renderPage(ServerRequest request) {
        // 准备需要提供给模板的默认数据
        var model = new HashMap<String, Object>();
        Mono<String> sponsorUrl = this.settingFetcher.get("basic").map(setting ->
                setting.get("sponsorUrl").asText("https://afdian.net/a/carolcoral")
            )
            .defaultIfEmpty("https://afdian.net/a/carolcoral");
        model.put("sponsorUrl", sponsorUrl);
        log.info(model.toString());
        return templateNameResolver.resolveTemplateNameOrDefault(request.exchange(), "afdian")
            .flatMap(templateName -> ServerResponse.ok().render(templateName, model));
    }

}
