package com.example.reservationservice.controller.paymethods;

import com.example.reservationservice.controller.paymethods.dto.out.PayMethodDto;
import com.example.reservationservice.service.paymethods.PayMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("paymethods")
@RequiredArgsConstructor
public class PayMethodController {

    private final PayMethodService payMethodService;
    private final PayMethodMapper payMethodMapper;

    @GetMapping("list")
    public List<PayMethodDto> getAllPayMethods() {
        return payMethodMapper.toList(payMethodService.findAll());
    }
}
