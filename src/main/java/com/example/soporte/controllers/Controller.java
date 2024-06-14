package com.example.soporte.controllers;

import com.example.soporte.exceptions.InvalidArgumentsException;
import com.example.soporte.exceptions.InvalidRequestFormatException;
import com.example.soporte.exceptions.PersistenceException;
import com.example.soporte.exceptions.RepositoryAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Controller{
    protected <T> ResponseEntity<T> okResponse(T object) {
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    protected <T> ResponseEntity<T> createdResponse(T object) {
        return new ResponseEntity<>(object, HttpStatus.CREATED);
    }

    private <T> ResponseEntity<T> notFoundResponse(T object) {
        return new ResponseEntity<>(object, HttpStatus.NOT_FOUND);
    }

    private <T> ResponseEntity<T> badRequestResponse(T object) {
        return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
    }

    private <T> ResponseEntity<T> internalServerErrorResponse(T object) {
        return new ResponseEntity<>(object, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ResponseEntity<Object> handleError(Exception err) {
        if (err instanceof InvalidArgumentsException) return badRequestResponse(err);
        if (err instanceof PersistenceException) return internalServerErrorResponse(err);
        if (err instanceof RepositoryAccessException) return internalServerErrorResponse(err);
        if (err instanceof InvalidRequestFormatException) return badRequestResponse(err);

        return internalServerErrorResponse(new RuntimeException("Internal server error."));
    }
}
