package com.checkbuy.project.exception;

public class ProdutoReferenciaNaoEncontrado extends RuntimeException {
    public ProdutoReferenciaNaoEncontrado(Integer id) {
        super("Produto Referência ID: " + id + " não foi encontrado!");
    }
}
