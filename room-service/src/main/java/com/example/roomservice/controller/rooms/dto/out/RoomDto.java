package com.example.roomservice.controller.rooms.dto.out;

import com.example.roomservice.model.rooms.AvailableStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Integer roomId;
    private String pictureName;
    private int storey;
    private int bedCount;
    private int price;
    private AvailableStatus tvStatus;
    private AvailableStatus balconyStatus;
    private AvailableStatus fridgeStatus;
    private AvailableStatus availableStatus;
}
