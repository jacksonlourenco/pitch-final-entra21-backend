package com.checkbuy.project.web.controller.lista;

import com.checkbuy.project.domain.lista.service.ListaService;
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

    public ListaController(ListaService listaService) {
        this.listaService = listaService;
    }

    @GetMapping("/{listaId}")
    public ResponseEntity<List<ListaResultadoDTO>> obterLista(@PathVariable Integer listaId, @AuthenticationPrincipal Usuario usuario){
        var itensLista = listaService.obterLista(listaId, usuario);

        return ResponseEntity.ok().body(itensLista);
    }

    @PostMapping
    public ResponseEntity<ListaDTO> criarLista(@RequestBody ListaCriarDTO dto, @AuthenticationPrincipal Usuario usuario) {

        ListaDTO lista = listaService.criarLista(dto, usuario);

        URI location = UriUtils.buildLocation(usuario.getId());
        return ResponseEntity.created(location).body(lista);
    }

    @GetMapping
    public ResponseEntity<List<ListaDTO>> minhasListas(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(listaService.listarMinhasListas(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLista(@PathVariable Integer id, @AuthenticationPrincipal Usuario usuario) {
        listaService.deletarLista(id, usuario);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/inserir/{listaId}")
    public ResponseEntity<Void> inserirProdutoReferencia(
            @PathVariable Integer listaId,
            @RequestParam Integer produtoReferenciaId,
            @RequestParam Integer quantidade,
            @AuthenticationPrincipal Usuario usuario) {

        listaService.inserirProduto(listaId, produtoReferenciaId, quantidade, usuario);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/excluir/{listaId}/{produtoReferenciaId}")
    public ResponseEntity<Void> excluirProdutosReferencia(@PathVariable Integer listaId, @PathVariable Integer produtoReferenciaId, @AuthenticationPrincipal Usuario usuario){
        listaService.excluirProduto(listaId, produtoReferenciaId, usuario);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/alterar/{listaId}")
    public ResponseEntity<Void> alterarProdutoReferencia(
            @PathVariable Integer listaId,
            @RequestParam Integer produtoReferenciaId,
            @RequestParam Integer quantidade,
            @AuthenticationPrincipal Usuario usuario) {

        listaService.alterarProduto(listaId, produtoReferenciaId, quantidade, usuario);

        return ResponseEntity.noContent().build();
    }


}