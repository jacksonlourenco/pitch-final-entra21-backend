package com.checkbuy.project.web.controller.produtoscraping;
import com.checkbuy.project.domain.produtoscraping.model.ProdutoScraping;
import com.checkbuy.project.domain.produtoscraping.service.ProdutoScrapingService;
import com.checkbuy.project.web.dto.produtoscraping.ContagemNotIndexPorUnidadeDTO;
import com.checkbuy.project.web.dto.produtoscraping.ProdutoScrapingChangeDTO;
import com.checkbuy.project.web.dto.produtoscraping.ProdutoScrapingOfertaRecentesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public ResponseEntity<List<ContagemNotIndexPorUnidadeDTO>> obterContagemPorUnidadeNotIndex(){
        var contagemPorUnidade =  produtoScrapingService.obterContagemPorUnidadeNotIndex();
        return ResponseEntity.ok().body(contagemPorUnidade);
    }


    @GetMapping("/melhores-ofertas/{produtoReferenciaId}")
    public ResponseEntity<List<ProdutoScrapingOfertaRecentesDTO>> ofertasMaisRecentes(@PathVariable Integer produtoReferenciaId){
        var melhoresOfertas = produtoScrapingService.ofertasMaisRecentes(produtoReferenciaId);
        return ResponseEntity.ok().body(melhoresOfertas);
    }

}
