package com.example.project.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Model.Produto;
import com.example.project.Service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> getAllProdutos() {
        return produtoService.getAllProdutos();
    }

    @GetMapping("/{id}")
    public Optional<Produto> getProdutoById(@PathVariable Integer id) {
        return produtoService.getProdutoById(id);
    }

    @PostMapping
    public Produto createProduto(Produto produto) {
        return produtoService.createProduto(produto);
    }

    @PutMapping("/{id}")
    public Produto updateProduto(@PathVariable Integer id, Produto produto) {
        return produtoService.updateProduto(id, produto);
    }

    @DeleteMapping("/{id}")
    public String deleteProduto(@PathVariable Integer id) {
        produtoService.deleteProduto(id);
        return "Produto com ID " + id + " foi deletado com sucesso.";
    }
}