package com.example.userservice.exceptions;

public class NoAccessException extends RuntimeException {

    public NoAccessException(String message) {
        super(message);
    }
}
