package com.checkbuy.project.domain.exception;

public class ListaNaoEncontada extends RuntimeException {
    public ListaNaoEncontada() {
        super("Lista não encontrada!");
    }
}
