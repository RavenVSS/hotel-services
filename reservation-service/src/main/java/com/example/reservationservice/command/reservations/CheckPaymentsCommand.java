package com.example.reservationservice.command.reservations;

import com.example.reservationservice.command.core.Command;
import com.example.reservationservice.service.reservations.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckPaymentsCommand implements Command<Void, Void> {

    private final ReservationService reservationService;

    @Override
    public Void execute(Void unused) {
        reservationService.checkPayments();
        return null;
    }
}
