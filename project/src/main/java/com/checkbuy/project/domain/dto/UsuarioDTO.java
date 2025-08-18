package com.checkbuy.project.domain.dto;

import com.checkbuy.project.domain.model.Usuario;

public record UsuarioDTO(
    Integer id,
    String nome,
    String email
){
    public UsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
