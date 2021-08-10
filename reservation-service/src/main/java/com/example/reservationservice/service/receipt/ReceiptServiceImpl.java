package com.example.reservationservice.service.receipt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    @Override
    public Integer getReceipt(Integer guestId, Integer roomId) {
        Random random = new Random();
        Integer max = 9999;
        Integer min = 1000;
        Integer diff = max - min;
        Integer receipt = random.nextInt( diff+ 1);
        receipt += min;
        String str = receipt.toString();
        str += guestId.toString() + roomId.toString();
        receipt = Integer.parseInt(str);
        return receipt;
    }
}
