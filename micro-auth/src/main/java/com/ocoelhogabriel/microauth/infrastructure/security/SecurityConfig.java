package com.ocoelhogabriel.microauth.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Centralized configuration class for Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita CSRF para APIs REST
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API stateless, sem sessão
                .authorizeHttpRequests(authorize -> authorize
                        // Libera o endpoint de login para acesso público
                        .requestMatchers("/api/auth/v1/login").permitAll()
                        // Libera os endpoints do Swagger
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // Todas as outras requisições precisam ser autenticadas
                        .anyRequest().authenticated()
                )
                .build();
    }

    /**
     * Expõe o AuthenticationManager do Spring como um Bean.
     * Este é o componente que a nossa AuthenticationServiceImpl usará para orquestrar a autenticação.
     * Ele automaticamente já sabe que deve usar o UserDetailsServiceImpl e o PasswordEncoder.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Define o algoritmo de criptografia de senhas.
     * Ao autenticar, o AuthenticationManager usará este bean para comparar a senha
     * enviada pelo usuário com a senha criptografada vinda do UserDetailsServiceImpl.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}