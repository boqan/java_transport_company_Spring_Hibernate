package com.batanasov.javaTransportCompanyProjectSPRING.exceptions;

/**
 * Custom exception class for handling cases where an entity is not found.
 * This exception is typically thrown when a lookup for a specific entity in the database yields no results.
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
