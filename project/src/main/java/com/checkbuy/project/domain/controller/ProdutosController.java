package com.checkbuy.project.domain.controller;

import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.service.ProdutoScrapingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
@CrossOrigin
public class ProdutosController {

    private final ProdutoScrapingService produtoScrapingService;

    public ProdutosController(ProdutoScrapingService produtoScrapingService) {
        this.produtoScrapingService = produtoScrapingService;
    }

    @PutMapping("/referencia/alterar/{aliasScraping}")
    public void alterar(@PathVariable String aliasScraping, @RequestBody ProdutoReferencia dto){
        produtoScrapingService.alterarReferencia(aliasScraping, dto);
    }
}
