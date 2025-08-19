package com.checkbuy.project.domain.scraping;

import com.checkbuy.project.domain.scraping.supemercados.bistek.ScrapingBistekService;
import com.checkbuy.project.domain.scraping.supemercados.cooper.ScrapingCooperService;
import com.checkbuy.project.domain.scraping.supemercados.koch.ScrapingKompraoService;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ScrapingService {

    private final ScrapingCooperService cooper;
    private final ScrapingKompraoService komprao;
    private final ScrapingBistekService bistek;

    public ScrapingService(ScrapingCooperService cooper, ScrapingKompraoService komprao, ScrapingBistekService bistek) {
        this.cooper = cooper;
        this.komprao = komprao;
        this.bistek = bistek;
    }

    public void scrapingPorTermo(String termo){
        CompletableFuture<Void> cooperScraping = cooper.cooperScrapingTermo(termo);
        CompletableFuture<Void> kompraoScraping = komprao.kochScrapingTermo(termo);
        CompletableFuture<Void> bistekScraping = bistek.biteskScrapingTermo(termo);

        CompletableFuture.allOf(cooperScraping, kompraoScraping, bistekScraping).join();
        //CompletableFuture.allOf(bistekScraping).join();
    }

}
