package com.example.reservationservice.controller.reservations.dto.out;

import com.example.reservationservice.model.reservations.ActualStatus;
import com.example.reservationservice.model.reservations.PayStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Integer reservationId;
    private Integer guestId;
    private Integer workerId;
    private Integer roomId;
    private Date beginDate;
    private Date endDate;
    private ActualStatus actualStatus;
    private Integer paymentMethodId;
    private PayStatus payStatus;
    private Integer receipt;
    private Integer money;
    private String comment;
}
