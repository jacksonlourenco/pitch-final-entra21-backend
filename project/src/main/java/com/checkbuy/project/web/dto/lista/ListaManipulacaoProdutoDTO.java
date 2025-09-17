package com.checkbuy.project.web.dto.lista;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ListaManipulacaoProdutoDTO(
        @NotNull
        Integer produtoReferenciaId,

        @Min(value = 1, message = "A quantidade mínima permitida é 1.")
        Integer quantidade
){}
