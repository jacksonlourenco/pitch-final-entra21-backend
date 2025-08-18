package com.checkbuy.project.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioCadastroDTO(
        @Email
        String email,

        @NotBlank
        String nome,

        @NotBlank
        String senha
){}
