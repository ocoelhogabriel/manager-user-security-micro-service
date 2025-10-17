package com.ocoelhogabriel.microauth.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for authentication responses.
 */
@Setter
@Getter
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private LocalDateTime issuedAt;
    private long expiresInSeconds;
    private String userId;
    private UserRoleDto userRole;

}
