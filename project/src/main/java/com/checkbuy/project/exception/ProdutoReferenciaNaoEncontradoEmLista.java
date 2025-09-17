package com.checkbuy.project.exception;

public class ProdutoReferenciaNaoEncontradoEmLista extends RuntimeException {
    public ProdutoReferenciaNaoEncontradoEmLista(Integer produtoReferenciaId, Integer listaId) {
        super("Produto Referencia ID: " + produtoReferenciaId + " não foi encontrado na Lista ID: " + listaId);
    }
}
