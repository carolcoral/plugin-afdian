package site.xindu.afdian.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ApiVersion;
import site.xindu.afdian.service.SponsorService;

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
    public Mono<JsonNode> listAllSponsor(){
        return sponsorService.listAllSponsor();
    }

    @GetMapping("/getSponsorList/{pageNumber}")
    public Mono<JsonNode> listSponsor(@PathVariable("pageNumber") int pageNumber){
        return sponsorService.getSponsorList(pageNumber);
    }

}
