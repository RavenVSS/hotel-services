package com.example.reservationservice.controller.paymethods.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayMethodDto {
    private Integer id;
    private String methodName;
}
