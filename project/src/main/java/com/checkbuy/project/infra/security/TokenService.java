package com.checkbuy.project.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.checkbuy.project.domain.usuario.model.Usuario;
import com.checkbuy.project.exception.ErroGeracaoToken;
import com.checkbuy.project.exception.TokenInvalidoOuExperiado;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    private static final String ISSUER = "API - checkbuy";

    /**
     * Gera um token JWT (JSON Web Token) para autenticação de um usuário.
     * <p>
     * O token contém o {@code username} do usuário como "subject", é emitido por {@link #ISSUER}
     * e possui uma data de expiração definida pelo método {@link #dataExpiracao()}.
     * </p>
     *
     * @param userDetails objeto que implementa {@link org.springframework.security.core.userdetails.UserDetails},
     *                    contendo informações do usuário que serão incluídas no token.
     * @return uma {@link String} representando o token JWT assinado.
     * @throws ErroGeracaoToken se ocorrer qualquer erro durante a criação do token JWT.
     *
     * <p><b>Exemplo de uso:</b></p>
     * <pre>
     * {@code
     * UserDetails user = userDetailsService.loadUserByUsername("usuario");
     * String token = gerarToken(user);
     * }
     * </pre>
     */
    public String gerarToken(UserDetails userDetails) {

        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(userDetails.getUsername())
                    .withExpiresAt(dataExpiracao())
                    .sign(getAlgorithm());
        } catch (JWTCreationException ex) {
            throw new ErroGeracaoToken(ex);
        }

    }

    public String getSubject(String tokenJWT) {
        try {
            return JWT.require(getAlgorithm())
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new TokenInvalidoOuExperiado();
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now()
                .plusHours(2)
                .atZone(ZoneId.of("America/Sao_Paulo"))
                .toInstant();
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

}
