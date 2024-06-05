package site.xindu.afdian.service;

import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ReactiveSettingFetcher;

@Service
@Slf4j
public class ConfigService {

    private final ReactiveSettingFetcher settingFetcher;

    public ConfigService(ReactiveSettingFetcher settingFetcher) {
        this.settingFetcher = settingFetcher;
    }

    private static final String BASIC = "basic";

    public HashMap<String, Object> getConfig() {
        var model = new HashMap<String, Object>();
        Mono<String> sponsorUrl =
            this.settingFetcher.get(BASIC).map(setting ->
                setting.get("sponsorUrl").asText()
            ).defaultIfEmpty("https://afdian.net/a/carolcoral");
        model.put("sponsorUrl", sponsorUrl);
        Mono<Double> sponsorNumber =
            this.settingFetcher.get(BASIC).map(setting ->
                setting.get("sponsorNumber").asDouble()
            ).defaultIfEmpty(66.00);
        model.put("sponsorNumber", sponsorNumber);
        return model;
    }

}
