package com.checkbuy.project.domain.controller;

import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.model.ProdutoScraping;
import com.checkbuy.project.domain.service.ProdutoReferenciaService;
import com.checkbuy.project.domain.service.ProdutoScrapingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin
public class ProdutosController {

    private final ProdutoScrapingService produtoScrapingService;
    private final ProdutoReferenciaService produtoReferenciaService;

    public ProdutosController(ProdutoScrapingService produtoScrapingService,
                              ProdutoReferenciaService produtoReferenciaService) {
        this.produtoScrapingService = produtoScrapingService;
        this.produtoReferenciaService = produtoReferenciaService;
    }

    @GetMapping("/scraping")
    public List<ProdutoScraping> listarProdutosScraping(){
        return produtoScrapingService.listarProdutosScraping();
    }

    @GetMapping("/scraping/{produtoReferenciaId}")
    public List<ProdutoScraping> listarProdutosScraping(@PathVariable int produtoReferenciaId){
        return produtoScrapingService.listarProdutosScraping(produtoReferenciaId);
    }

    @GetMapping("/referencia")
    public List<ProdutoReferencia> listarProdutoReferencia(){
        return produtoReferenciaService.listarProdutoReferencia();
    }
}
