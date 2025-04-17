package com.example.app.exceptions;

public class OfficerLimitExceededException extends RuntimeException {
    public OfficerLimitExceededException(String message) {
        super(message);
    }
}
