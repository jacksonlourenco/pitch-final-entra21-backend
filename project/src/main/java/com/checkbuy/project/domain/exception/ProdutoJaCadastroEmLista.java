package com.checkbuy.project.domain.exception;

public class ProdutoJaCadastroEmLista extends RuntimeException {
    public ProdutoJaCadastroEmLista(Integer listaId, Integer produtoReferenciaId) {
        super("Produto ID: "+ produtoReferenciaId + " ja existente na Lista ID: " + listaId );
    }
}
