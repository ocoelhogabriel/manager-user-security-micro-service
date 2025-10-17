package com.ocoelhogabriel.microauth.application.service;

import com.ocoelhogabriel.microauth.domain.entity.User;
import com.ocoelhogabriel.microauth.domain.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Busca o usuário no banco de dados através do nosso repositório
        User userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // 2. Coleta as 'authorities' (roles) do usuário
        Collection<? extends GrantedAuthority> authorities = userEntity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        // 3. Retorna um objeto User do Spring Security.
        // Este objeto contém o username, a SENHA CRIPTOGRAFADA e as roles.
        // O Spring Security usará isso internamente para a comparação.
        return new User(
                userEntity.getUsername(),
                userEntity.getPasswordHash(),
                authorities
        );
    }
}