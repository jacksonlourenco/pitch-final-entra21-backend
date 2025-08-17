package com.checkbuy.project.domain.exception;

public class ProdutoReferenciaNotIndexImutavel extends RuntimeException {
    public ProdutoReferenciaNotIndexImutavel(Integer id) {
        super("Produto Referência ID: " + id + " não é póssivel fazer alteração. (USO DO SISTEMA)");
    }
}
