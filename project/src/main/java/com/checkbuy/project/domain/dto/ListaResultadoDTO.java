package com.checkbuy.project.domain.dto;

import com.checkbuy.project.domain.model.ListaResultado;

public record ListaResultadoDTO(
    Integer listaId,
    Integer produtoId,
    Integer quantidade,
    Integer usuarioId
){
    public ListaResultadoDTO(ListaResultado p) {
        this(p.getId().getListaId(),p.getId().getProdutoReferenciaId(), p.getQuantidade(), p.getLista().getUsuario().getId());
    }
}
