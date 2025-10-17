package com.ocoelhogabriel.microauth.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * Data Transfer Object for authentication requests.
 */
@Getter
public class AuthenticationRequest {
    
    @NotBlank(message = "Username is required")
    private String username;
    
    @NotBlank(message = "Password is required")
    private String password;
    
    public AuthenticationRequest() {
    }
    
    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
