package com.example.reservationservice.service.paymethods;

import com.example.reservationservice.model.paymethods.PayMethod;

import java.util.List;

public interface PayMethodService {

    List<PayMethod> findAll();
}
