package com.example.app.exceptions;

/**
 * Custom exception class for handling data parsing errors.
 */
public class DataParsingException extends RuntimeException {
    public DataParsingException(String message) {
        super(message);
    }
}
