package site.xindu.afdian.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import site.xindu.afdian.service.SponsorService;
import run.halo.app.plugin.ApiVersion;

@ApiVersion("v1alpha1")
@RequestMapping("/afdian")
@RestController
@Slf4j
public class AfdianController {


    private final SponsorService sponsorService;

    public AfdianController(SponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }


    @GetMapping("/getSponsorList")
    public Mono<JsonNode> getFirstSponsorList(){
        return sponsorService.getSponsorList("1");
    }

    @GetMapping(value = "/getSponsorList/{pageNumber}")
    public Mono<JsonNode> getFirstSponsorList(@PathVariable String pageNumber){
        return sponsorService.getSponsorList(pageNumber);
    }
}
