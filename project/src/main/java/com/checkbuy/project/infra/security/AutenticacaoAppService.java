package com.checkbuy.project.infra.security;

import com.checkbuy.project.exception.CredenciaisInvalidas;
import com.checkbuy.project.web.dto.security.DadosAutenticacaoDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoAppService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticacaoAppService(AuthenticationManager authenticationManager,
                                  TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    /**
     * Autentica um usuário e gera um token JWT.
     * @param dados DTO com email e senha
     * @return DadosTokenJWT com token gerado
     * @throws CredenciaisInvalidas se email ou senha forem incorretos
     */
    public DadosTokenJWT autenticar(DadosAutenticacaoDTO dados){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(),dados.senha());
        Authentication authentication = null;

        try{
            authentication = authenticationManager.authenticate(authenticationToken);
        }catch (AuthenticationException e){
            throw new CredenciaisInvalidas();
        }


        var userDetails = (UserDetails) authentication.getPrincipal();
        var tokenJWT = tokenService.gerarToken(userDetails);

        return new DadosTokenJWT(tokenJWT);
    }

}
