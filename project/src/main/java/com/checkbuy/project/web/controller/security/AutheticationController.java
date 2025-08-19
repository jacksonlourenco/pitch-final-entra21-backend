package com.checkbuy.project.web.controller.security;

import com.checkbuy.project.domain.usuario.model.Usuario;
import com.checkbuy.project.infra.security.DadosTokenJWT;
import com.checkbuy.project.infra.security.TokenService;
import com.checkbuy.project.web.dto.security.DadosAutenticacaoDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class AutheticationController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutheticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacaoDTO dados){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(),dados.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var authenticationCast = (Usuario) authentication.getPrincipal();
        var tokenJWT = tokenService.gerarToken(authenticationCast);

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}



