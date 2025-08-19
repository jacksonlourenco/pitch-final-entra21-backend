package com.checkbuy.project.web.dto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacaoDTO(
        @Email
        String email,

        @NotBlank
        String senha
){}
