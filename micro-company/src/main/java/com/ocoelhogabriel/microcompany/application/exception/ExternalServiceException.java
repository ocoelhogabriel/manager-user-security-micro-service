package com.ocoelhogabriel.microcompany.application.exception;

import java.io.Serial;

/**
 * Exception thrown when there's an external service failure.
 */
public class ExternalServiceException extends ApplicationException {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * Creates a new ExternalServiceException with the specified message.
     *
     * @param message the exception message
     */
    public ExternalServiceException(String message) {
        super(message);
    }
    
    /**
     * Creates a new ExternalServiceException with the specified message and cause.
     *
     * @param message the exception message
     * @param cause the cause of the exception
     */
    public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Creates a new ExternalServiceException for a specific service.
     *
     * @param serviceName the name of the external service
     * @param errorCause the cause of the exception
     */
    public ExternalServiceException(String serviceName, String errorCause, Throwable cause) {
        super(String.format("Error communicating with external service: %s - %s", serviceName, errorCause), cause);
    }
}
