package com.checkbuy.project.exception;

public class ProibidoInserirProdutoNotIndexEmLista extends RuntimeException {
    public ProibidoInserirProdutoNotIndexEmLista() {
        super("Produto Not Index, Não pode ser inserido em lista.");
    }
}
