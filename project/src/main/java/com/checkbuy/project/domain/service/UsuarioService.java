package com.checkbuy.project.domain.service;

import com.checkbuy.project.domain.dto.UsuarioCadastroDTO;
import com.checkbuy.project.domain.dto.UsuarioDTO;
import com.checkbuy.project.domain.exception.EmailJaCadastrado;
import com.checkbuy.project.domain.model.Usuario;
import com.checkbuy.project.domain.repository.UsuarioRepository;
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
