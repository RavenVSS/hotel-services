package com.example.reservationservice.exceptions;

public class NoAccessException extends RuntimeException {

    public NoAccessException(String message) {
        super(message);
    }
}
