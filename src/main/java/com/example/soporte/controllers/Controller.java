package com.example.soporte.controllers;

import com.example.soporte.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public abstract class Controller{
    protected <T> ResponseEntity<T> okResponse(T object) {
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    protected <T> ResponseEntity<T> createdResponse(T object) {
        return new ResponseEntity<>(object, HttpStatus.CREATED);
    }

    protected <T> ResponseEntity<T> noContentResponse() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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

    //TODO Revisar que esta devolviendo en cada caso.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleError(Exception err) {
        System.out.println(err.getMessage());

        return switch(err){
            case MethodArgumentNotValidException _, InvalidArgumentsException _, InvalidRequestFormatException _ -> badRequestResponse(err.getMessage());
            case InterfaceException _, RepositoryException _ -> internalServerErrorResponse(err.getMessage());
            case ResourceNotFoundException _ -> notFoundResponse(err.getMessage());

            default -> internalServerErrorResponse("Internal server error.");
        };


        /*return switch(err.getClass().getName()){
            case "ResourceNotFoundException" -> notFoundResponse(err);
            case "InvalidArgumentsException", "InvalidRequestFormatException", "MethodArgumentNotValidException" -> badRequestResponse(err);
            case "InterfaceException", "RepositoryException" -> internalServerErrorResponse(err);

            default -> internalServerErrorResponse(new RuntimeException("Internal server errorr."));
        };*/
    }

    protected <T> void validateResource(T resource) {
        if (resource == null) throw new ResourceNotFoundException("Resource not found.");
    }
}
