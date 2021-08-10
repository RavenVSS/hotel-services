package com.example.reservationservice.controller.paymethods;

import com.example.reservationservice.controller.paymethods.dto.out.PayMethodDto;
import com.example.reservationservice.model.paymethods.PayMethod;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PayMethodMapper {

    List<PayMethodDto> toList(List<PayMethod> payMethodList);
}
