package com.example.roomservice.service.rooms;

import com.example.roomservice.model.rooms.Room;
import com.example.roomservice.model.rooms.RoomCreateArg;

import java.util.Date;
import java.util.List;

public interface RoomService {

    void create(RoomCreateArg arg);

    void update(RoomCreateArg arg, int roomId);

    void delete(Integer id);

    List<Room> findAll();

    Room findAt(Integer id);

    List<Room> findFreeRooms(Date start, Date end);
}
