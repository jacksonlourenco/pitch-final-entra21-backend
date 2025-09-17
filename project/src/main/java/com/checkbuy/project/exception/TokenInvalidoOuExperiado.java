package com.checkbuy.project.exception;

public class TokenInvalidoOuExperiado extends RuntimeException {
    public TokenInvalidoOuExperiado() {
        super("Token JWT inválido ou expirado!");
    }
}
