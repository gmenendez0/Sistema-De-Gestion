package com.example.soporte.exceptions;

public class InvalidRequestFormatException extends RuntimeException {
    public InvalidRequestFormatException(String message) {
        super(message);
    }
}
