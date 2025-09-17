package com.checkbuy.project.exception;

public class AliasProdutoReferenciaNaoEncontrado extends RuntimeException {
    public AliasProdutoReferenciaNaoEncontrado(String alias) {
        super("Alias do Produto: " + alias + " não foi encontrado!");
    }
}
