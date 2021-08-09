package com.example.roomservice.repository.rooms;

import com.example.roomservice.model.rooms.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query(value = "select * from rooms " +
            "where room_id not in (SELECT room_id FROM reservation " +
            "where begin_date BETWEEN cast(:start as date)  AND cast(:end as date) " +
            "OR cast(:start as date) BETWEEN begin_date AND end_date)", nativeQuery = true)
    List<Room> findFreeRooms(@Param("start") Date start, @Param("end") Date end);
}
