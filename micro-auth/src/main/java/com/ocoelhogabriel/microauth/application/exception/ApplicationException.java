package com.ocoelhogabriel.microauth.application.exception;

import java.io.Serial;

/**
 * Base exception class for all application-level exceptions.
 */
public class ApplicationException extends RuntimeException {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * Creates a new ApplicationException with the specified message.
     *
     * @param message the exception message
     */
    public ApplicationException(String message) {
        super(message);
    }
    
    /**
     * Creates a new ApplicationException with the specified message and cause.
     *
     * @param message the exception message
     * @param cause the cause of the exception
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
