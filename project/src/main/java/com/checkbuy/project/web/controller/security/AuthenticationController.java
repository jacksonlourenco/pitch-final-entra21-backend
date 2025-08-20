package com.checkbuy.project.web.controller.security;

import com.checkbuy.project.infra.security.AutenticacaoAppService;
import com.checkbuy.project.infra.security.DadosTokenJWT;
import com.checkbuy.project.web.dto.security.DadosAutenticacaoDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private final AutenticacaoAppService autenticacaoAppService;

    public AuthenticationController(AutenticacaoAppService autenticacaoAppService) {
        this.autenticacaoAppService = autenticacaoAppService;
    }

    @PostMapping
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacaoDTO dados){

        return ResponseEntity.ok(autenticacaoAppService.autenticar(dados));
    }
}



