package com.example.roomservice.controller;

import com.example.roomservice.controller.dto.in.RoomCreateDto;
import com.example.roomservice.controller.dto.out.RoomDto;
import com.example.roomservice.model.rooms.Room;
import com.example.roomservice.model.rooms.RoomCreateArg;
import com.example.roomservice.service.rooms.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "rooms", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @PostMapping("create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addNewRoom(@RequestBody RoomCreateDto roomCreateDto) {
        RoomCreateArg roomCreateArg = roomMapper.fromDto(roomCreateDto);
        roomService.create(roomCreateArg);
    }

    @GetMapping("{id}")
    public RoomDto getAtRoom(@PathVariable Integer id) {
        Room room = roomService.findAt(id);
        RoomDto roomDto = roomMapper.toDto(room);
        return roomDto;
    }

    @PostMapping("{id}/update")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateRoom(
            @PathVariable("id") Integer roomId,
            @RequestBody RoomCreateDto roomCreateDto) {
        RoomCreateArg roomCreateArg = roomMapper.fromDto(roomCreateDto);
        roomService.update(roomCreateArg, roomId);
    }

    @PostMapping("{id}/delete")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteRoom(@PathVariable("id") Integer id) {
        roomService.delete(id);
    }

    @GetMapping("list")
    public List<RoomDto> getAllRooms() {
        List<Room> rooms = roomService.findAll();
        return roomMapper.toList(rooms);
    }

    @GetMapping("free")
    public List<RoomDto> getFreeRooms(@RequestParam("startDate")
                                      @DateTimeFormat(pattern="yyyy-MM-dd") Date start,
                                      @RequestParam("endDate")
                                      @DateTimeFormat(pattern="yyyy-MM-dd") Date end) {
        List<Room> rooms = roomService.findFreeRooms(start, end);
        return roomMapper.toList(rooms);
    }
}
