package com.checkbuy.project.domain.usuario.service;

import com.checkbuy.project.domain.usuario.model.Usuario;
import com.checkbuy.project.domain.usuario.repository.UsuarioRepository;
import com.checkbuy.project.exception.EmailJaCadastrado;
import com.checkbuy.project.web.dto.usuario.UsuarioCadastroDTO;
import com.checkbuy.project.web.dto.usuario.UsuarioDTO;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioDTO cadastrarUsuario(@Valid UsuarioCadastroDTO dto){
        var email = usuarioRepository.existsByEmail(dto.email());

        if(email){
            throw new EmailJaCadastrado(dto.email());
        }

        var usuario = new Usuario(null, dto.email(), dto.nome(), passwordEncoder.encode(dto.senha()));
        usuarioRepository.save(usuario);
        return new UsuarioDTO(usuario);
    }
}
