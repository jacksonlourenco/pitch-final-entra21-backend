package com.checkbuy.project.domain.controller;
import com.checkbuy.project.domain.dto.ProdutoScrapingChangeDTO;
import com.checkbuy.project.domain.model.ProdutoScraping;
import com.checkbuy.project.domain.service.ProdutoScrapingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/produtos/scraping")
public class ProdutoScrapingController {

    private final ProdutoScrapingService produtoScrapingService;

    public ProdutoScrapingController(ProdutoScrapingService produtoScrapingService) {
        this.produtoScrapingService = produtoScrapingService;
    }

    @GetMapping("/{produtoReferenciaId}")
    public Page<ProdutoScraping> obterScrapingPelaReferencia(@PathVariable Integer produtoReferenciaId, Pageable pageable){
        return produtoScrapingService.obterScrapingPelaReferencia(produtoReferenciaId, pageable);
    }

    @PutMapping
    public ResponseEntity<Void> alterarProdutoReferencia(@RequestBody ProdutoScrapingChangeDTO dto){
        produtoScrapingService.alterarProdutoReferencia(dto);
        return ResponseEntity.noContent().build();
    }

}
