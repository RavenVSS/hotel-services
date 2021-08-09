package com.example.reservationservice.model.reservations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationCreateArg {
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
