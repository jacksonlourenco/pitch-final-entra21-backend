package com.checkbuy.project.exception;

import com.checkbuy.project.web.dto.security.DadosAutenticacaoDTO;

public class CredenciaisInvalidas extends RuntimeException {
    public CredenciaisInvalidas() {
        super("Credenciais inválidas");
    }
}
