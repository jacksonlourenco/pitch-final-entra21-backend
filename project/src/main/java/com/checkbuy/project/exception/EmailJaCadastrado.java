package com.checkbuy.project.exception;

public class EmailJaCadastrado extends RuntimeException {
    public EmailJaCadastrado(String email) {
        super("E-mail: " + email + " existente!");
    }
}
