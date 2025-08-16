package com.checkbuy.project.domain.controller;
import com.checkbuy.project.domain.dto.ContagemNotIndexPorUnidadeDTO;
import com.checkbuy.project.domain.dto.ProdutoScrapingChangeDTO;
import com.checkbuy.project.domain.model.ProdutoScraping;
import com.checkbuy.project.domain.service.ProdutoScrapingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/produtos/scraping")
public class ProdutoScrapingController {

    private final ProdutoScrapingService produtoScrapingService;

    public ProdutoScrapingController(ProdutoScrapingService produtoScrapingService) {
        this.produtoScrapingService = produtoScrapingService;
    }

    @GetMapping("/{produtoReferenciaId}")
    public Page<ProdutoScraping> obterScrapingPelaReferencia(@PathVariable Integer produtoReferenciaId,
                                                             Pageable pageable,
                                                             @RequestParam(required = false) String nome,
                                                             @RequestParam(required = false) Integer unidade_id){

        return produtoScrapingService.obterScrapingPelaReferencia(produtoReferenciaId, pageable, nome, unidade_id);
    }

    @PutMapping
    public ResponseEntity<Void> alterarProdutoReferencia(@RequestBody ProdutoScrapingChangeDTO dto){
        produtoScrapingService.alterarProdutoReferencia(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/notindex/contagem-por-unidade")
    public List<ContagemNotIndexPorUnidadeDTO> obterContagemPorUnidadeNotIndex(){
        return produtoScrapingService.obterContagemPorUnidadeNotIndex();
    }

}
