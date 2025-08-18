package com.checkbuy.project.domain.controller;

import com.checkbuy.project.domain.model.Lista;
import com.checkbuy.project.domain.model.Usuario;
import com.checkbuy.project.domain.service.ListaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listas")
public class ListaController {

    private final ListaService listaService;

    public ListaController(ListaService listaService) {
        this.listaService = listaService;
    }


    @PostMapping
    public ResponseEntity<Lista> criarLista(@RequestBody String nome, @AuthenticationPrincipal Usuario usuario) {
        Lista lista = listaService.criarLista(nome, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(lista);
    }

    @GetMapping
    public ResponseEntity<List<Lista>> minhasListas(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(listaService.listarMinhasListas(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLista(@PathVariable Integer id, @AuthenticationPrincipal Usuario usuario) {
        listaService.deletarLista(id, usuario);
        return ResponseEntity.noContent().build();
    }

}