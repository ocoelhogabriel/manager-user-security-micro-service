package com.ocoelhogabriel.microauth.domain.service;

import com.ocoelhogabriel.microauth.interfaces.dto.AuthenticationRequest;
import com.ocoelhogabriel.microauth.interfaces.dto.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse authenticate(String username, String password);

}
