package com.example.reservationservice.command.reservations;

import com.example.reservationservice.command.core.Command;
import com.example.reservationservice.feignservice.UserFeignService;
import com.example.reservationservice.model.reservations.Reservation;
import com.example.reservationservice.service.reservations.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetReservationsCurrentUserCommand implements Command<Void, List<Reservation>> {

    private final ReservationService reservationService;
    private final UserFeignService userFeignService;

    @Override
    public List<Reservation> execute(Void unused) {
        return reservationService.findByGuestId(userFeignService.getCurrentUser().getUserId());
    }
}
