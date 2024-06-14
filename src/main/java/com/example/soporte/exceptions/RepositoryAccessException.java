package com.example.soporte.exceptions;

public class RepositoryAccessException extends RuntimeException {
    public RepositoryAccessException(String message) {
        super(message);
    }
}
