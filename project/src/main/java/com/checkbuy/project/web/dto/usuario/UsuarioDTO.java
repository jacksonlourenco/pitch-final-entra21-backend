package com.checkbuy.project.web.dto.usuario;

import com.checkbuy.project.domain.usuario.model.Usuario;

public record UsuarioDTO(
    Integer id,
    String nome,
    String email
){
    public UsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
