package com.checkbuy.project.web.controller.produtoreferencia;

import com.checkbuy.project.domain.produtoreferencia.model.ProdutoReferencia;
import com.checkbuy.project.domain.produtoreferencia.service.ProdutoReferenciaService;
import com.checkbuy.project.infra.util.UriUtils;
import com.checkbuy.project.web.dto.produtoreferencia.ProdutoReferenciaDTO;
import com.checkbuy.project.web.dto.produtoreferencia.ProdutoReferenciaSimilaridadeDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
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
    public ResponseEntity<Void> excluir(@PathVariable Integer id){
        produtoReferenciaService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<ProdutoReferencia>> listar(Pageable pageable){
        var lista = produtoReferenciaService.listar(pageable);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/sugerir/{alias}")
    public List<ProdutoReferenciaSimilaridadeDTO> sugerir(@PathVariable String alias){
        return produtoReferenciaService.sugerir(alias);
    }

}
