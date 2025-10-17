package com.ocoelhogabriel.microauth.application.dto;

import java.time.LocalDateTime;

public record TokenDetails(
        String username,
        String token,
        LocalDateTime issuedAt,
        LocalDateTime expiresInSeconds) {
}