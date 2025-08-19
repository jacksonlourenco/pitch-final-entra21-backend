package com.checkbuy.project.exception;

public class UnidadeNaoEncontrada extends RuntimeException {
    public UnidadeNaoEncontrada(Integer id) {
        super("Unidade ID: " + id + " não foi encontrado!");
    }
}
