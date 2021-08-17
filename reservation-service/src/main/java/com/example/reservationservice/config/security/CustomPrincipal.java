package com.example.reservationservice.config.security;

public class CustomPrincipal {

    private Integer userId;

    public CustomPrincipal() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
