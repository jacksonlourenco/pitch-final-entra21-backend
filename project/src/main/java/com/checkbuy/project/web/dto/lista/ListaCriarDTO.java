package com.checkbuy.project.web.dto.lista;

import jakarta.validation.constraints.NotBlank;

public record ListaCriarDTO(
        @NotBlank
        String nome
){}
