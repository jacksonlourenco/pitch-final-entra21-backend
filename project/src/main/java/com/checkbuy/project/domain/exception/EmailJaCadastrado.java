package com.checkbuy.project.domain.exception;

public class EmailJaCadastrado extends RuntimeException {
    public EmailJaCadastrado(String email) {
        super("E-mail: " + email + " existente!");
    }
}
