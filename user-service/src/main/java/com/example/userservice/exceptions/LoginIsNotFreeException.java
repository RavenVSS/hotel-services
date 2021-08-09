package com.example.userservice.exceptions;

public class LoginIsNotFreeException extends RuntimeException {

    public LoginIsNotFreeException(String message) {
        super(message);
    }
}
