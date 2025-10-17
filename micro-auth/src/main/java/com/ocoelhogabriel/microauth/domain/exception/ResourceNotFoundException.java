package com.ocoelhogabriel.microauth.domain.exception;

import com.ocoelhogabriel.microcompany.domain.exception.DomainException;

import java.io.Serial;

/**
 * Exception thrown when a resource is not found.
 */
public class ResourceNotFoundException extends DomainException {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * Creates a new ResourceNotFoundException with the specified message.
     *
     * @param message the exception message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Creates a new ResourceNotFoundException for a resource with the specified ID.
     *
     * @param resourceType the type of resource
     * @param id the ID of the resource
     */
    public ResourceNotFoundException(String resourceType, Object id) {
        super(String.format("%s with id %s not found", resourceType, id));
    }
}
