package com.example.app.exceptions;

/**
 * Custom exception class for handling cases where an officer is already inside.
 */
public class OfficerAlreadyInsideException extends RuntimeException {
    public OfficerAlreadyInsideException(String message) {
        super(message);
    }
}
