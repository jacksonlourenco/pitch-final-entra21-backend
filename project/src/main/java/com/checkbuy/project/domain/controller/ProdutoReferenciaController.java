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
@RequestMapping("/alias-produtos-referencias")
@CrossOrigin
public class ProdutoReferenciaController {

    private final ProdutoReferenciaService produtoReferenciaService;

    public ProdutoReferenciaController(ProdutoReferenciaService produtoReferenciaService) {
        this.produtoReferenciaService = produtoReferenciaService;
    }

    @PostMapping("/criar")
    private ResponseEntity<ProdutoReferencia> create(@RequestBody ProdutoReferencia dto){
        produtoReferenciaService.create(dto);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/listar")
    public List<ProdutoReferencia> listarProdutos(){
        return produtoReferenciaService.listarProdutos();
    }

    @GetMapping("/listar-notindex")
    public List<AliasProdutoReferencia> listarNotIndex(){
        return produtoReferenciaService.listarNotIndex();
    }

    @PutMapping("/atualizar")
    public ResponseEntity<UpdateAliasProdutoReferenciaDTO> fixProdutoNotIndex(@Valid @RequestBody UpdateAliasProdutoReferenciaDTO dto){
        produtoReferenciaService.alterar(dto);
        return ResponseEntity.ok().body(dto);
    }


}
