package com.checkbuy.project.exception;

public class ProibidoInserirQuantidadeZeroEmLista extends RuntimeException {
    public ProibidoInserirQuantidadeZeroEmLista(Integer produtoReferenciaId) {
        super("Produto ID: "+ produtoReferenciaId + " não pode ter quantidade menor que zero:");
    }
}
