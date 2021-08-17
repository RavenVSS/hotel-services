package com.example.reservationservice.controller.reservations;

import com.example.reservationservice.command.reservations.GetReservationsCurrentUserCommand;
import com.example.reservationservice.controller.reservations.dto.in.ReservationCreateDto;
import com.example.reservationservice.controller.reservations.dto.out.ReservationDto;
import com.example.reservationservice.model.reservations.Reservation;
import com.example.reservationservice.model.reservations.ReservationCreateArg;
import com.example.reservationservice.service.reservations.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    private final GetReservationsCurrentUserCommand getReservationsCurrentUserCommand;

    @PostMapping("create")
    @PreAuthorize("hasRole('WORKER') || hasRole('USER')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addNewReservation (@RequestBody ReservationCreateDto reservationCreateDto) {
        ReservationCreateArg reservationCreateArg = reservationMapper.fromDto(reservationCreateDto);
        reservationService.create(reservationCreateArg);
    }

    @PostMapping("{id}/update")
    @PreAuthorize("hasRole('WORKER') || hasRole('USER')")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateReservation (@RequestBody ReservationCreateDto reservationCreateDto,
                                   @PathVariable("id") Integer id) {
        reservationService.update(reservationMapper.fromDto(reservationCreateDto), id);
    }

    @PostMapping("{id}/delete")
    @PreAuthorize("hasRole('WORKER')")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteReservation(@PathVariable("id") Integer id) {
        reservationService.delete(id);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('WORKER')")
    public ReservationDto getAtReservation(@PathVariable("id") Integer id) {
        Reservation reservation = reservationService.findAt(id);
        ReservationDto reservationDto = reservationMapper.toDto(reservation);
        return reservationDto;
    }

    @GetMapping("list")
    @PreAuthorize("hasRole('WORKER')")
    public List<ReservationDto> getAllReservations() {
        return reservationMapper.toList(reservationService.findAll());
    }

    @GetMapping("search/name")
    @PreAuthorize("hasRole('WORKER')")
    public List<ReservationDto> getReservationsByName(@RequestParam("firstName") String firstName,
                                                      @RequestParam("secondName") String secondName) {
        return reservationMapper.toList(reservationService.findByName(firstName, secondName));
    }

    @GetMapping("search/date")
    @PreAuthorize("hasRole('WORKER')")
    public List<ReservationDto> getReservationsByDate(@RequestParam("beginDate")
                                                      @DateTimeFormat(pattern="yyyy-MM-dd") Date beginDate) {
        return reservationMapper.toList(reservationService.findByBeginDate(beginDate));
    }

    @GetMapping("current")
    @PreAuthorize("hasRole('USER')")
    public List<ReservationDto> getReservationsCurrentUser() {
        return reservationMapper.toList(getReservationsCurrentUserCommand.execute(null));
    }
}
