package com.example.roomservice.exceptions;

public class NoAccessException extends RuntimeException {

    public NoAccessException(String message) {
        super(message);
    }
}
