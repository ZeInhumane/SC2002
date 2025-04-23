package com.example.app.exceptions;

/**
 * Custom exception class for handling cases where the officer limit is exceeded.
 */
public class OfficerLimitExceededException extends RuntimeException {
    public OfficerLimitExceededException(String message) {
        super(message);
    }
}
