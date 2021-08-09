package com.example.roomservice.model.rooms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "picture_name")
    private String pictureName;

    @Column(name = "storey")
    private int storey;

    @Column(name = "bed_count")
    private int bedCount;

    @Column(name = "price")
    private int price;

    @Column(name = "tv_status")
    @Enumerated(EnumType.STRING)
    private AvailableStatus tvStatus;

    @Column(name = "balcony_status")
    @Enumerated(EnumType.STRING)
    private AvailableStatus balconyStatus;

    @Column(name = "fridge_status")
    @Enumerated(EnumType.STRING)
    private AvailableStatus fridgeStatus;

    @Column(name = "available_status")
    @Enumerated(EnumType.STRING)
    private AvailableStatus availableStatus;
}
