package com.checkbuy.project.web.controller.lista;

import com.checkbuy.project.domain.lista.service.ListaProdutoService;
import com.checkbuy.project.domain.lista.service.ListaService;
import com.checkbuy.project.web.dto.lista.ListaManipulacaoProdutoDTO;
import com.checkbuy.project.web.dto.listaresultado.ListaResultadoDTO;
import com.checkbuy.project.domain.usuario.model.Usuario;
import com.checkbuy.project.infra.util.UriUtils;
import com.checkbuy.project.web.dto.lista.ListaCriarDTO;
import com.checkbuy.project.web.dto.lista.ListaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/listas")
public class ListaController {

    private final ListaService listaService;
    private final ListaProdutoService listaProdutoService;

    public ListaController(ListaService listaService,
                           ListaProdutoService listaProdutoService) {
        this.listaService = listaService;
        this.listaProdutoService = listaProdutoService;
    }

    @GetMapping
    public ResponseEntity<List<ListaDTO>> minhasListas(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(listaService.listarMinhasListas(usuario));
    }

    @PostMapping
    public ResponseEntity<ListaDTO> criarLista(@RequestBody ListaCriarDTO dto,
                                               @AuthenticationPrincipal Usuario usuario) {

        ListaDTO lista = listaService.criarLista(dto, usuario);

        URI location = UriUtils.buildLocation(lista.id());
        return ResponseEntity.created(location).body(lista);
    }

    @GetMapping("/{listaId}")
    public ResponseEntity<List<ListaResultadoDTO>> obterLista(@PathVariable Integer listaId,
                                                              @AuthenticationPrincipal Usuario usuario){
        var itensLista = listaService.obterLista(listaId, usuario);

        return ResponseEntity.ok().body(itensLista);
    }

    @DeleteMapping("/{listaId}")
    public ResponseEntity<Void> deletarLista(@PathVariable Integer listaId,
                                             @AuthenticationPrincipal Usuario usuario) {
        listaService.deletarLista(listaId, usuario);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{listaId}/produtos")
    public ResponseEntity<Void> inserirProdutoReferencia(
            @PathVariable Integer listaId,
            @RequestBody ListaManipulacaoProdutoDTO dto,
            @AuthenticationPrincipal Usuario usuario) {

        listaProdutoService.inserirProduto(listaId, dto, usuario);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{listaId}/produtos")
    public ResponseEntity<Void> alterarProdutoReferencia(
            @PathVariable Integer listaId,
            @RequestBody ListaManipulacaoProdutoDTO dto,
            @AuthenticationPrincipal Usuario usuario) {

        listaProdutoService.alterarProduto(listaId, dto, usuario);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{listaId}/produtos/{produtoReferenciaId}")
    public ResponseEntity<Void> excluirProdutosReferencia(@PathVariable Integer listaId,
                                                          @PathVariable Integer produtoReferenciaId,
                                                          @AuthenticationPrincipal Usuario usuario){
        listaProdutoService.excluirProduto(listaId, produtoReferenciaId, usuario);

        return ResponseEntity.noContent().build();
    }

}