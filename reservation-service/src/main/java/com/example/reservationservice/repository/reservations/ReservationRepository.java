package com.example.reservationservice.repository.reservations;

import com.example.reservationservice.model.reservations.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByBeginDate(Date beginDate);

    @Query(value = "select * from reservation " +
            "where guest_id in (SELECT user_id FROM users " +
            "where first_name = :firstName AND second_name = :secondName)", nativeQuery = true)
    List<Reservation> findByName(String firstName, String secondName);

    @Query(value = "SELECT * FROM reservation WHERE guest_id = :guestId", nativeQuery = true)
    List<Reservation> findByGuestId(Integer guestId);
}
