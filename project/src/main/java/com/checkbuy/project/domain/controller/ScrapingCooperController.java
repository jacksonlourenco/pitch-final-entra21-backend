package com.checkbuy.project.domain.controller;

import com.checkbuy.project.domain.service.ProdutoScrapingService;
import com.checkbuy.project.service.supermercado.cooper.scraping.ScrapingCooperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scraping/cooper")
public class ScrapingCooperController {

    private final ScrapingCooperService scrapingCooperService;

    public ScrapingCooperController(ScrapingCooperService scrapingCooperService) {
        this.scrapingCooperService = scrapingCooperService;
    }

    @GetMapping("/{produto}")
    public void buscarPorNome(@PathVariable String produto){
        scrapingCooperService.scrapingPorTermo(produto);
    }

    @GetMapping("page={page}&item={item}&categoria={categoria}")
    public ResponseEntity<Object> scrapingCooper(@PathVariable int page, @PathVariable int item, @PathVariable int categoria){
        scrapingCooperService.cooperScraping(page, item, categoria);
        return ResponseEntity.noContent().build();
    }
}
