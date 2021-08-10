package com.example.reservationservice.repository.paymethods;

import com.example.reservationservice.model.paymethods.PayMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayMethodRepository extends JpaRepository<PayMethod, Integer> {

}
