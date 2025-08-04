package com.example.project.domain.dto;

import jakarta.validation.constraints.NotNull;

public record FixProdutoNotIndexDTO(

        @NotNull
        String estabelecimento,

        @NotNull
        String nomeProduto,

        @NotNull
        Integer produtoReferenciaId) {
}