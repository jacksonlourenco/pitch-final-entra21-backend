package com.checkbuy.project.web.dto.lista;

import com.checkbuy.project.domain.lista.model.Lista;

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
