package com.checkbuy.project.domain.controller;

import com.checkbuy.project.service.supermercado.cooper.scraping.ScrapingCooperService;
import com.checkbuy.project.service.supermercado.giassi.scraping.ScrapingGiassiService;
import com.checkbuy.project.service.supermercado.komprao.dto.ProdutoKompraoDTO;
import com.checkbuy.project.service.supermercado.komprao.scraping.ScrapingKompraoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scraping/komprao")
public class ScrapingKompraoController {

    private final ScrapingKompraoService scrapingKompraoService;
    private final ScrapingGiassiService scrapingGiassiService;

    public ScrapingKompraoController(ScrapingKompraoService scrapingKompraoService, ScrapingGiassiService scrapingGiassiService) {
        this.scrapingKompraoService = scrapingKompraoService;
        this.scrapingGiassiService = scrapingGiassiService;
    }

    @GetMapping("/{produto}")
    public void buscarPorNome(@PathVariable String produto){
        //scrapingKompraoService.scrapingPorTermo(produto);
    }

    @GetMapping("/page/{page}/categoria/{categoria}")
    public void scrapingKomprao(@PathVariable int page, @PathVariable String categoria){
        scrapingKompraoService.kochScraping(categoria, page);
    }



}
