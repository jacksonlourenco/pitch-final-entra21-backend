package com.checkbuy.project.domain.controller;

import com.checkbuy.project.domain.dto.ProdutoReferenciaDTO;
import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.service.ProdutoReferenciaService;
import com.checkbuy.project.util.UriUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@CrossOrigin
@RequestMapping("/produtos/referencias")
public class ProdutoReferenciaController {

    private final ProdutoReferenciaService produtoReferenciaService;

    public ProdutoReferenciaController(ProdutoReferenciaService produtoReferenciaService) {
        this.produtoReferenciaService = produtoReferenciaService;
    }

    @PostMapping
    public ResponseEntity<ProdutoReferencia> criar(@RequestBody  @Valid ProdutoReferenciaDTO dto){

        ProdutoReferencia produtoReferencia = produtoReferenciaService.criar(dto);

        URI location = UriUtils.buildLocation(produtoReferencia.getId());

        return ResponseEntity.created(location).body(produtoReferencia);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoReferencia> buscarPorId(@PathVariable Integer id){

        ProdutoReferencia produtoReferencia = produtoReferenciaService.buscarPorId(id);

        return ResponseEntity.ok(produtoReferencia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoReferencia> alterar(@PathVariable Integer id, @RequestBody @Valid ProdutoReferenciaDTO dto){
        ProdutoReferencia produtoReferencia = produtoReferenciaService.alterar(id, dto);

        return ResponseEntity.ok(produtoReferencia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Integer id){
        produtoReferenciaService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<ProdutoReferencia>> listar(Pageable pageable){
        var lista = produtoReferenciaService.listar(pageable);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/sugerir/{alias}")
    public void sugerir(@PathVariable String alias){

    }

}
