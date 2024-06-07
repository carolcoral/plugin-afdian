package site.xindu.afdian.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ApiVersion;
import site.xindu.afdian.service.AfdianConfigService;
import site.xindu.afdian.service.AfdianService;

@ApiVersion("v1alpha1")
@RequestMapping("/afdian")
@RestController
@Slf4j
public class AfdianController {


    private final AfdianService afdianService;

    private final AfdianConfigService configService;

    public AfdianController(AfdianService afdianService, AfdianConfigService configService) {
        this.afdianService = afdianService;
        this.configService = configService;
    }


    @GetMapping("/getSponsorList")
    public Mono<JsonNode> listAllSponsor() {
        return afdianService.listAllSponsor();
    }

    @GetMapping("/getSponsorList/{pageNumber}")
    public Mono<JsonNode> listSponsor(@PathVariable("pageNumber") int pageNumber) {
        return afdianService.getSponsorList(pageNumber);
    }

    @GetMapping("/config")
    public Mono<JsonNode> getConfig() {
        return configService.getConfig();
    }

    @GetMapping("/listAllSponsorship")
    public Mono<JsonNode> listAllSponsorship() {
        return afdianService.listAllSponsorship();
    }

    @GetMapping("/listPlansAndSales")
    public Mono<JsonNode> listPlansAndSales() {
        return afdianService.listPlansAndSales();
    }

    @GetMapping("/listAlbum")
    public Mono<JsonNode> listAlbum() {
        return afdianService.listAlbum();
    }

}
