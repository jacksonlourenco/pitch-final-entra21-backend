package com.checkbuy.project.exception;

import com.auth0.jwt.exceptions.JWTCreationException;

public class ErroGeracaoToken extends RuntimeException {
    public ErroGeracaoToken(JWTCreationException ex) {
        super("Erro ao gerar o token ", ex);
    }
}
