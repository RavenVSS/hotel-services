package com.example.authservice.exceptions;

public class LoginIsNotFreeException extends RuntimeException {

    public LoginIsNotFreeException(String message) {
        super(message);
    }
}
