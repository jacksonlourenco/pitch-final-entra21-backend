package com.checkbuy.project.domain.controller;

import com.checkbuy.project.domain.dto.UpdateAliasProdutoReferenciaDTO;
import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.model.alias.AliasProdutoReferencia;
import com.checkbuy.project.domain.service.ProdutoReferenciaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos/referencia")
@CrossOrigin
public class ProdutoReferenciaController {

    private final ProdutoReferenciaService produtoReferenciaService;

    public ProdutoReferenciaController(ProdutoReferenciaService produtoReferenciaService) {
        this.produtoReferenciaService = produtoReferenciaService;
    }

    @GetMapping
    public List<ProdutoReferencia> listarProdutoReferencia(){
        return produtoReferenciaService.listarProdutoReferencia();
    }

    @PostMapping("/criar")
    public ResponseEntity<ProdutoReferencia> create(@RequestBody ProdutoReferencia dto){
        produtoReferenciaService.create(dto);
        return ResponseEntity.ok().body(dto);
    }
}