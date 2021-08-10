package com.example.reservationservice.service.paymethods;

import com.example.reservationservice.exceptions.EntityNotFoundException;
import com.example.reservationservice.model.paymethods.PayMethod;
import com.example.reservationservice.repository.paymethods.PayMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PayMethodServiceImpl implements PayMethodService {

    private final PayMethodRepository payMethodRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PayMethod> findAll() {
        List<PayMethod> payMethodList = payMethodRepository.findAll();
        if(payMethodList.isEmpty()) throw new EntityNotFoundException("Pay Method not found");
        return payMethodList;
    }
}
