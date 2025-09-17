package com.checkbuy.project.exception;

public class ListaNaoEncontada extends RuntimeException {
    public ListaNaoEncontada(Integer listaId, Integer id) {
        super("Lista ID: " + listaId + " (inexistente) ou (não pertence) a Usuario ID: " + id);
    }
}
