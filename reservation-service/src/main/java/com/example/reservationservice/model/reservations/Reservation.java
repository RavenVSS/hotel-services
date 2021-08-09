package com.example.reservationservice.model.reservations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "guest_id")
    private Integer guestId;

    @Column(name = "worker_id")
    private Integer workerId;

    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "begin_date")
    private Date beginDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "actual_status")
    @Enumerated(EnumType.STRING)
    private ActualStatus actualStatus;

    @Column(name = "payment_method_id")
    private Integer paymentMethodId;

    @Column(name = "success_pay_status")
    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    @Column(name = "receipt")
    private Integer receipt;

    @Column(name = "money")
    private Integer money;

    @Column(name = "comment")
    private String comment;
}
