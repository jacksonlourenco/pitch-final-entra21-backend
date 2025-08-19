package com.checkbuy.project.web.dto.produtoscraping;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoScrapingChangeDTO(

        @NotBlank
        String alias,

        @NotNull
        Integer produtoReferenciaID
){}
