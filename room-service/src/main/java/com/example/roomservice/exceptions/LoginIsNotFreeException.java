package com.example.roomservice.exceptions;

public class LoginIsNotFreeException extends RuntimeException {

    public LoginIsNotFreeException(String message) {
        super(message);
    }
}
