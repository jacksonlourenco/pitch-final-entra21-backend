package com.checkbuy.project.domain.controller;

import com.checkbuy.project.service.supermercado.cooper.scraping.ScrapingCooperService;
import com.checkbuy.project.service.supermercado.komprao.scraping.ScrapingKompraoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/scraping")
@CrossOrigin
public class ScrapingController {

    private final ScrapingCooperService scrapingCooperService;
    private final ScrapingKompraoService scrapingKompraoService;

    public ScrapingController(ScrapingCooperService scrapingCooperService,
                              ScrapingKompraoService scrapingKompraoService) {
        this.scrapingCooperService = scrapingCooperService;
        this.scrapingKompraoService = scrapingKompraoService;
    }

    @GetMapping("/produto/{termo}")
    public ResponseEntity<String> buscarTermo(@PathVariable String termo){
        CompletableFuture<Void> future1 = scrapingCooperService.cooperScrapingTermo(termo);
        CompletableFuture<Void> future2 = scrapingKompraoService.kochScrapingTermo(termo);

        CompletableFuture.allOf(future1, future2).join(); // aguarda as duas finalizarem

        return ResponseEntity.ok("Scraping concluído");
    }
}
