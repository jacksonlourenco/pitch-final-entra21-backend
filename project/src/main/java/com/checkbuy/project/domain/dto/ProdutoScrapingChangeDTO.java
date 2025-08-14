package com.checkbuy.project.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoScrapingChangeDTO(

        @NotBlank
        String alias,

        @NotNull
        Integer produtoReferenciaID
){}
