package com.example.reservationservice.service.reservations;

import com.example.reservationservice.exceptions.EntityNotFoundException;
import com.example.reservationservice.feignservice.UserFeignService;
import com.example.reservationservice.model.reservations.ActualStatus;
import com.example.reservationservice.model.reservations.PayStatus;
import com.example.reservationservice.model.reservations.Reservation;
import com.example.reservationservice.model.reservations.ReservationCreateArg;
import com.example.reservationservice.repository.reservations.ReservationRepository;
import com.example.reservationservice.service.receipt.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserFeignService userFeignService;

    private final Map<Integer, Date> startPaymentsReservation = new HashMap<>();

    @Override
    @Transactional
    public void create(ReservationCreateArg reservationCreateArg) {
        Reservation reservation = reservationRepository.save(Reservation.builder()
            .roomId(reservationCreateArg.getRoomId())
            .actualStatus(ActualStatus.ACTUAL)
            .receipt(reservationCreateArg.getReceipt())
            .payStatus(reservationCreateArg.getPayStatus())
            .beginDate(reservationCreateArg.getBeginDate())
            .endDate(reservationCreateArg.getEndDate())
            .guestId(reservationCreateArg.getGuestId())
            .workerId(userFeignService.getCurrentUser().getUserId())
            .money(reservationCreateArg.getMoney())
            .comment(reservationCreateArg.getComment())
            .paymentMethodId(reservationCreateArg.getPaymentMethodId())
            .build());

        if (reservation.getPaymentMethodId() == 2)
            startPaymentsReservation.put(reservation.getId(), new Date());
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
        reservationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(ReservationCreateArg reservationCreateArg, Integer id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        reservation.setRoomId(reservationCreateArg.getRoomId());
        reservation.setActualStatus(reservationCreateArg.getActualStatus());
        reservation.setPayStatus(reservationCreateArg.getPayStatus());
        reservation.setBeginDate(reservationCreateArg.getBeginDate());
        reservation.setEndDate(reservationCreateArg.getEndDate());
        reservation.setGuestId(reservationCreateArg.getGuestId());
        reservation.setWorkerId(reservationCreateArg.getWorkerId());
        reservation.setMoney(reservationCreateArg.getMoney());
        reservation.setReceipt(reservationCreateArg.getReceipt());
        reservation.setComment(reservationCreateArg.getComment());
        reservation.setPaymentMethodId(reservationCreateArg.getPaymentMethodId());

        reservationRepository.save(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        List<Reservation> reservationList = reservationRepository.findAll();
        if (reservationList.isEmpty()) throw new EntityNotFoundException("Reservation not found");
        return reservationList;
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation findAt(Integer id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findByBeginDate(Date beginDate) {
        List<Reservation> reservationList = reservationRepository.findByBeginDate(beginDate);
        if (reservationList.isEmpty()) throw new EntityNotFoundException("Reservation not found");
        return reservationList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findByName(String firstName, String secondName) {
        List<Reservation> reservationList = reservationRepository.findByName(firstName, secondName);
        if (reservationList.isEmpty()) throw new EntityNotFoundException("Reservation not found");
        return reservationList;
    }

    @Override
    public List<Reservation> findByGuestId(Integer guestId) {
        List<Reservation> reservationList = reservationRepository.findByGuestId(guestId);
        if (reservationList.isEmpty()) throw new EntityNotFoundException("Reservation not found");
        return reservationList;
    }

    @Override
    public void checkPayments() {
        if (!startPaymentsReservation.isEmpty()) {
            List<Integer> removeList = new ArrayList<>();
            startPaymentsReservation.forEach((reservationId, date) -> {
                if ((new Date().getTime() - date.getTime())/60000 >= 15) {

                    Reservation reservation = reservationRepository.findById(reservationId)
                            .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

                    if (reservation.getPayStatus() == PayStatus.NOT_PAID) {
                        reservation.setActualStatus(ActualStatus.REJECTED);
                        reservationRepository.save(reservation);
                    }
                    removeList.add(reservationId);
                }
            });

            for (Integer reservationId: removeList) {
                startPaymentsReservation.remove(reservationId);
            }
        }
    }
}
