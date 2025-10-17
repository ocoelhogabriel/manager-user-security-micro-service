package com.ocoelhogabriel.microauth.infrastructure.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ocoelhogabriel.microauth.application.dto.TokenDetails;
import com.ocoelhogabriel.microauth.infrastructure.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class JwtManager {

    private static final String TOKEN_ISSUER = "user-security-api";
    private static final String ROLES_CLAIM = "roles";
    private static final String USER_ID_CLAIM = "userId";

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.expiration.time.minutes}")
    private long expirationTimeInMinutes;

    public JwtManager(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public TokenDetails generateTokenDetails(String username, Collection<? extends GrantedAuthority> authorities) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            long expiresInSeconds = expirationInMinutes * 60;
            Instant issuedAtInstant = Instant.now();
            Instant expiresAtInstant = issuedAtInstant.plusSeconds(expiresInSeconds);

            // Coleta as "authorities" (roles) como uma lista de strings
            String roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

            String token = JWT.create().withIssuer("micro-auth") // Emissor do token
                    .withSubject(username) // Assunto (usuário)
                    .withClaim("roles", roles) // Adiciona as roles como uma claim
                    .withIssuedAt(issuedAtInstant) // Data de emissão
                    .withExpiresAt(expiresAtInstant) // Data de expiração
                    .sign(algorithm); // Assina com o nosso segredo

            LocalDateTime issuedAt = LocalDateTime.ofInstant(issuedAtInstant, ZoneOffset.UTC);

            return new TokenDetails(token, issuedAt, expiresInSeconds);

        } catch (JWTCreationException exception) {
            // Lidar com o erro de criação do token
            throw new RuntimeException("Error while generating JWT token", exception);
        }
    }

    /**
     * Valida um token JWT e retorna o nome do usuário (subject).
     */
    public String validateTokenAndGetSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("micro-auth").build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            // Se o token for inválido (expirado, assinatura incorreta, etc.), lança uma exceção
            throw new JWTVerificationException("Invalid or expired JWT token");
        }
    }
}
