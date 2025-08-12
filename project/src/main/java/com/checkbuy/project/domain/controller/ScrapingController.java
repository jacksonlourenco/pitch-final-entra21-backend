package com.checkbuy.project.domain.controller;

import com.checkbuy.project.service.supermercado.bistek.ScrapingBistekService;
import com.checkbuy.project.service.supermercado.cooper.scraping.ScrapingCooperService;
import com.checkbuy.project.service.supermercado.komprao.ScrapingKompraoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/scraping")
@CrossOrigin
public class ScrapingController {

    private final ScrapingCooperService scrapingCooperService;
    private final ScrapingKompraoService scrapingKompraoService;
    private final ScrapingBistekService scrapingBistekService;

    public ScrapingController(ScrapingCooperService scrapingCooperService,
                              ScrapingKompraoService scrapingKompraoService, ScrapingBistekService scrapingBistekService) {
        this.scrapingCooperService = scrapingCooperService;
        this.scrapingKompraoService = scrapingKompraoService;
        this.scrapingBistekService = scrapingBistekService;
    }

    @GetMapping("/pesquisar/{termo}")
    public ResponseEntity<String> buscarTermo(@PathVariable String termo){
        CompletableFuture<Void> cooper = scrapingCooperService.cooperScrapingTermo(termo);
        CompletableFuture<Void> komprao = scrapingKompraoService.kochScrapingTermo(termo);
        CompletableFuture<Void> bistek = scrapingBistekService.biteskScrapingTermo(termo);

        CompletableFuture.allOf(cooper, komprao, bistek).join(); // aguarda as duas finalizarem

        return ResponseEntity.ok("Scraping concluído");
    }
}
