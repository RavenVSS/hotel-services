package com.example.roomservice.model.rooms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreateArg {
    private String pictureName;
    private int storey;
    private int bedCount;
    private int price;
    private AvailableStatus tvStatus;
    private AvailableStatus balconyStatus;
    private AvailableStatus fridgeStatus;
    private AvailableStatus availableStatus;
}
