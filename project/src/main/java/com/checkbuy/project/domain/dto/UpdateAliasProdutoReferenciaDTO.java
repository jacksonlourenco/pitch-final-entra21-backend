package com.checkbuy.project.domain.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateAliasProdutoReferenciaDTO(

        @NotNull
        String nomeProduto,

        @NotNull
        Integer produtoReferenciaId) {
}