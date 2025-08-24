package com.checkbuy.project.web.controller.usuario;

import com.checkbuy.project.domain.usuario.model.Usuario;
import com.checkbuy.project.domain.usuario.service.UsuarioService;
import com.checkbuy.project.infra.util.EmailService;
import com.checkbuy.project.web.dto.usuario.UsuarioCadastroDTO;
import com.checkbuy.project.web.dto.usuario.UsuarioDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final EmailService emailService;

    public UsuarioController(UsuarioService usuarioService,
                             EmailService emailService) {
        this.usuarioService = usuarioService;
        this.emailService = emailService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody @Valid UsuarioCadastroDTO dto){
        var usuario = usuarioService.cadastrarUsuario(dto);
        emailService.enviarEmailDeConfirmacaoHtml(usuario.email(), usuario.nome());

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping("/user")
    public ResponseEntity<UsuarioDTO> infoUsuario(@AuthenticationPrincipal Usuario usuario){
        var usuarioInfo = usuarioService.infoUsuario(usuario);

        return ResponseEntity.ok().body(usuarioInfo);
    }

}
