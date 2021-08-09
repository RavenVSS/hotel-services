package com.example.roomservice.service.rooms;

import com.example.roomservice.exceptions.EntityNotFoundException;
import com.example.roomservice.model.rooms.Room;
import com.example.roomservice.model.rooms.RoomCreateArg;
import com.example.roomservice.repository.rooms.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public void create(RoomCreateArg arg) {
        roomRepository.save(Room.builder()
            .pictureName(arg.getPictureName())
            .storey(arg.getStorey())
            .bedCount(arg.getBedCount())
            .price(arg.getPrice())
            .tvStatus(arg.getTvStatus())
            .balconyStatus(arg.getBalconyStatus())
            .fridgeStatus(arg.getFridgeStatus())
            .availableStatus(arg.getAvailableStatus())
            .build());
    }

    @Override
    @Transactional
    public void update(RoomCreateArg arg, int roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        room.setPictureName(arg.getPictureName());
        room.setStorey(arg.getStorey());
        room.setBedCount(arg.getBedCount());
        room.setPrice(arg.getPrice());
        room.setTvStatus(arg.getTvStatus());
        room.setBalconyStatus(arg.getBalconyStatus());
        room.setFridgeStatus(arg.getFridgeStatus());
        room.setAvailableStatus(arg.getAvailableStatus());

        roomRepository.save(room);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        roomRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Room findAt(Integer id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
        return room;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> findFreeRooms(Date start, Date end) {
        return roomRepository.findFreeRooms(start, end);
    }

}
