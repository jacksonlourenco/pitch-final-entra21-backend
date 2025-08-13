package com.checkbuy.project.domain.exception;

public class ProdutoReferenciaNaoEncontrado extends RuntimeException {
    public ProdutoReferenciaNaoEncontrado(Integer id) {
        super("Produto Referência ID: " + id + " não foi encontrado!");
    }
}
