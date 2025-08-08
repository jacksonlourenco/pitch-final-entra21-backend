package com.checkbuy.project.domain.controller;

import com.checkbuy.project.domain.dto.FixProdutoNotIndexDTO;
import com.checkbuy.project.domain.dto.ProdutoNotIndexDTO;
import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.service.ProdutoScrapingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos/referencias")
@CrossOrigin
public class ProdutosReferenciaController {

    private final ProdutoScrapingService produtoScrapingService;

    public ProdutosReferenciaController(ProdutoScrapingService produtoScrapingService) {
        this.produtoScrapingService = produtoScrapingService;
    }

    @PostMapping("/criar")
    private ResponseEntity<ProdutoReferencia> criarReferencia(@RequestBody ProdutoReferencia dto){
        produtoScrapingService.criarReferencia(dto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/atualizar")
    public void fixProdutoNotIndex(@Valid @RequestBody FixProdutoNotIndexDTO dto){
        produtoScrapingService.fixProdutoNotIndex(dto);
    }

    @GetMapping("/listar/notindex")
    public List<ProdutoNotIndexDTO> listarProdutoNotIndex(){
        return produtoScrapingService.listarProdutoNotIndex();
    }

    @GetMapping("/listar")
    public List<ProdutoReferencia> listarProdutos(){
        return produtoScrapingService.listarProdutos();
    }



}
