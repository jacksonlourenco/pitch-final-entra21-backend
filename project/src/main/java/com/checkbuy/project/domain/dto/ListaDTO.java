package com.checkbuy.project.domain.dto;

import com.checkbuy.project.domain.model.Lista;

import java.time.LocalDate;

public record ListaDTO(
        Integer id,
        String nome,
        LocalDate dataCriacao,
        Integer idUsuario
){
    public ListaDTO(Lista lista) {
        this(lista.getId(), lista.getNome(), lista.getDataCriacao(), lista.getUsuario().getId());
    }
}
