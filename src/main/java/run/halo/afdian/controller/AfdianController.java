package run.halo.afdian.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import run.halo.afdian.service.SponsorService;
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
    public Mono<JsonNode> getSponsorList(){
        return sponsorService.getSponsorList();
    }
}
