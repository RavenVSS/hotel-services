package com.example.reservationservice.exceptions;

public class LoginIsNotFreeException extends RuntimeException {

    public LoginIsNotFreeException(String message) {
        super(message);
    }
}
