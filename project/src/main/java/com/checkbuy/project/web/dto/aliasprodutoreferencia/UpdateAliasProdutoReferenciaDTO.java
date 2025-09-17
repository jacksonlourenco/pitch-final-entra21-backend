package com.checkbuy.project.web.dto.aliasprodutoreferencia;

import jakarta.validation.constraints.NotNull;

public record UpdateAliasProdutoReferenciaDTO(

        @NotNull
        String nomeProduto,

        @NotNull
        Integer produtoReferenciaId) {
}