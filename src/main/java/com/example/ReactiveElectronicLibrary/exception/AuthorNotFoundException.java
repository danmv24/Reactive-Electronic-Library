package com.example.ReactiveElectronicLibrary.exception;

import org.springframework.http.HttpStatus;

public class AuthorNotFoundException extends RuntimeException {
    private String errorMessage;

    private HttpStatus errorCode;

    public AuthorNotFoundException(HttpStatus errorCode, String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
