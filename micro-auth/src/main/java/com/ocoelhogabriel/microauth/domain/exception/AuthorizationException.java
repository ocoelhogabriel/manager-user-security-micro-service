package com.ocoelhogabriel.microauth.domain.exception;

import java.io.Serial;

/**
 * Exception thrown when authorization fails.
 */
public class AuthorizationException extends DomainException {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * Creates a new AuthorizationException with the specified message.
     *
     * @param message the exception message
     */
    public AuthorizationException(String message) {
        super(message);
    }
    
    /**
     * Creates a new AuthorizationException with the specified message and cause.
     *
     * @param message the exception message
     * @param cause the cause of the exception
     */
    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
