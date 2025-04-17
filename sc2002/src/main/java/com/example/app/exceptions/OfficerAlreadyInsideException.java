package com.example.app.exceptions;

public class OfficerAlreadyInsideException extends RuntimeException {
    public OfficerAlreadyInsideException(String message) {
        super(message);
    }
}
