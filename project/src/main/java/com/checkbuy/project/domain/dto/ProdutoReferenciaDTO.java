package com.checkbuy.project.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoReferenciaDTO(

    @NotBlank
    String nome,

    @NotBlank
    String urlImg,

    @NotBlank
    String marca,

    @NotBlank
    String descricao,

    @NotBlank
    String unidadeMedida,

    @NotNull
    Double valorMedida,

    @NotBlank
    String codigoBarra
) {}