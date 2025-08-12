package com.checkbuy.project.domain.controller;

import com.checkbuy.project.domain.model.ProdutoScraping;
import com.checkbuy.project.domain.service.ProdutoScrapingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos/scraping")
@CrossOrigin

public class ProdutoScrapingController {

    private final ProdutoScrapingService produtoScrapingService;

    public ProdutoScrapingController(ProdutoScrapingService produtoScrapingService) {
        this.produtoScrapingService = produtoScrapingService;
    }

    @GetMapping("/scraping")
    public List<ProdutoScraping> listarProdutosScraping(){
        return produtoScrapingService.listarProdutosScraping();
    }

    @GetMapping("/scraping/{produtoReferenciaId}")
    public List<ProdutoScraping> listarProdutosScraping(@PathVariable int produtoReferenciaId){
        return produtoScrapingService.listarProdutosScraping(produtoReferenciaId);
    }

}
