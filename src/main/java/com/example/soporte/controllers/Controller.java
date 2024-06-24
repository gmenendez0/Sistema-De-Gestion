package com.example.soporte.controllers;

import com.example.soporte.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public abstract class Controller{
    /**
     * Creates a response entity with HTTP status 200 (OK).
     *
     * @param object the body of the response
     * @param <T> the type of the response body
     * @return a ResponseEntity with HTTP status 200 (OK)
     */
    protected <T> ResponseEntity<T> okResponse(T object) {
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    /**
     * Creates a response entity with HTTP status 201 (Created).
     *
     * @param object the body of the response
     * @param <T> the type of the response body
     * @return a ResponseEntity with HTTP status 201 (Created)
     */
    protected <T> ResponseEntity<T> createdResponse(T object) {
        return new ResponseEntity<>(object, HttpStatus.CREATED);
    }

    /**
     * Creates a response entity with HTTP status 204 (No Content).
     *
     * @param <T> the type of the response body
     * @return a ResponseEntity with HTTP status 204 (No Content)
     */
    protected <T> ResponseEntity<T> noContentResponse() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Creates a response entity with HTTP status 404 (Not Found).
     *
     * @param object the body of the response
     * @param <T> the type of the response body
     * @return a ResponseEntity with HTTP status 404 (Not Found)
     */
    private <T> ResponseEntity<T> notFoundResponse(T object) {
        return new ResponseEntity<>(object, HttpStatus.NOT_FOUND);
    }

    /**
     * Creates a response entity with HTTP status 400 (Bad Request).
     *
     * @param object the body of the response
     * @param <T> the type of the response body
     * @return a ResponseEntity with HTTP status 400 (Bad Request)
     */
    private <T> ResponseEntity<T> badRequestResponse(T object) {
        return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
    }

    /**
     * Creates a response entity with HTTP status 500 (Internal Server Error).
     *
     * @param object the body of the response
     * @param <T> the type of the response body
     * @return a ResponseEntity with HTTP status 500 (Internal Server Error)
     */
    private <T> ResponseEntity<T> internalServerErrorResponse(T object) {
        return new ResponseEntity<>(object, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles exceptions thrown by controller methods.
     *
     * @param err the exception thrown
     * @return a ResponseEntity with appropriate HTTP status and error message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleError(Exception err) {
        System.out.println(err.getMessage());

        return switch(err){
            case MethodArgumentNotValidException _, InvalidArgumentsException _, InvalidRequestFormatException _ -> badRequestResponse(err.getMessage());
            case InterfaceException _, RepositoryException _ -> internalServerErrorResponse(err.getMessage());
            case ResourceNotFoundException _ -> notFoundResponse(err.getMessage());

            default -> internalServerErrorResponse("Internal server error.");
        };
    }

    /**
     * Validates that the provided resource is not null.
     *
     * @param resource the resource to validate
     * @param <T> the type of the resource
     * @throws ResourceNotFoundException if the resource is null
     */
    protected <T> void validateResource(T resource) {
        if (resource == null) throw new ResourceNotFoundException("Resource not found.");
    }
}
