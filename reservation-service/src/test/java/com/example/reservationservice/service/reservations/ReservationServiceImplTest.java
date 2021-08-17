package com.example.reservationservice.service.reservations;

import com.example.reservationservice.feignservice.UserFeignService;
import com.example.reservationservice.model.reservations.ActualStatus;
import com.example.reservationservice.model.reservations.PayStatus;
import com.example.reservationservice.model.reservations.Reservation;
import com.example.reservationservice.model.reservations.ReservationCreateArg;
import com.example.reservationservice.model.users.User;
import com.example.reservationservice.model.users.UserTypes;
import com.example.reservationservice.repository.reservations.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private UserFeignService userFeignService;

    private ReservationServiceImpl reservationService;

    private Reservation expected;
    private ReservationCreateArg reservationCreateArg;

    @BeforeEach
    void setUp() {
        expected = Reservation.builder()
                .actualStatus(ActualStatus.ACTUAL)
                .beginDate(new Date(1234567L))
                .endDate(new Date(1234567L))
                .comment("Test")
                .guestId(1)
                .id(1)
                .money(1000)
                .paymentMethodId(1)
                .payStatus(PayStatus.PAID)
                .receipt(0000)
                .roomId(1)
                .workerId(2)
                .build();

        reservationCreateArg = ReservationCreateArg.builder()
                .actualStatus(ActualStatus.ACTUAL)
                .beginDate(new Date(1234567L))
                .endDate(new Date(1234567L))
                .comment("Test")
                .guestId(1)
                .money(1000)
                .paymentMethodId(1)
                .payStatus(PayStatus.PAID)
                .receipt(0000)
                .roomId(1)
                .workerId(2)
                .build();

        reservationService = new ReservationServiceImpl(reservationRepository, userFeignService);
    }

    @Test
    void create() {
        User user = User.builder()
                .userId(1)
                .email("qwe@mail.ru")
                .firstName("qwe")
                .login("qwe")
                .middleName("qwe")
                .phone("89991111234")
                .regDate(new Date())
                .secondName("qwe")
                .type(UserTypes.ROLE_WORKER)
                .build();
        Mockito.when(userFeignService.getCurrentUser())
                .thenReturn(user);
        Mockito.when(reservationRepository.save(any(Reservation.class)))
                .thenReturn(expected);
        reservationService.create(reservationCreateArg);

        Mockito.verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void delete() {
        Mockito.when(reservationRepository.findById(expected.getId()))
                .thenReturn(Optional.of(expected));
        reservationService.delete(expected.getId());
        Mockito.verify(reservationRepository).deleteById(any());
    }

    @Test
    void findAt() {
        Mockito.when(reservationRepository.findById(expected.getId()))
                .thenReturn(Optional.of(expected));
        assertThat(reservationService.findAt(expected.getId())).isEqualTo(expected);
    }

    @Test
    void update() {
        Mockito.when(reservationRepository.findById(expected.getId()))
                .thenReturn(Optional.of(expected));
        ReservationCreateArg reservationCreateArgUpdate = ReservationCreateArg.builder()
                .actualStatus(ActualStatus.ACTUAL)
                .beginDate(new Date(1234567L))
                .endDate(new Date(1234567L))
                .comment("Update")
                .guestId(1)
                .money(1000)
                .paymentMethodId(1)
                .payStatus(PayStatus.PAID)
                .receipt(0000)
                .roomId(1)
                .workerId(2)
                .build();

        Reservation expectedUpdate = Reservation.builder()
                .actualStatus(ActualStatus.ACTUAL)
                .beginDate(new Date(1234567L))
                .endDate(new Date(1234567L))
                .comment("Update")
                .guestId(1)
                .id(1)
                .money(1000)
                .paymentMethodId(1)
                .payStatus(PayStatus.PAID)
                .receipt(0000)
                .roomId(1)
                .workerId(2)
                .build();

        reservationService.update(reservationCreateArgUpdate, 1);

        assertThat(reservationService.findAt(1)).isEqualTo(expectedUpdate);
    }

    @Test
    void findAll() {
        Reservation expectedTwo = Reservation.builder()
                .actualStatus(ActualStatus.ACTUAL)
                .beginDate(new Date(1234567L))
                .endDate(new Date(1234568L))
                .comment("Test")
                .guestId(1)
                .id(1)
                .money(1000)
                .paymentMethodId(1)
                .payStatus(PayStatus.PAID)
                .receipt(0000)
                .roomId(1)
                .workerId(2)
                .build();
        List<Reservation> expectedList = new ArrayList<>();
        expectedList.add(expected);
        expectedList.add(expectedTwo);
        Mockito.when(reservationRepository.findAll())
                .thenReturn(expectedList);

        assertThat(reservationService.findAll()).isEqualTo(expectedList);
    }

    @Test
    void findByBeginDate() {
        List<Reservation> expectedList = new ArrayList<>();
        expectedList.add(expected);
        Mockito.when(reservationRepository.findByBeginDate(new Date(1234569L)))
                .thenReturn(expectedList);

        assertThat(reservationService.findByBeginDate(new Date(1234569L))).isEqualTo(expectedList);
    }

    @Test
    void findByName() {
        List<Reservation> expectedList = new ArrayList<>();
        expectedList.add(expected);

        Mockito.when(reservationRepository.findByName("Иван", "Иванов"))
                .thenReturn(expectedList);

        assertThat(reservationService.findByName("Иван", "Иванов"))
                .isEqualTo(expectedList);
    }

    @Test
    void findByGuestId() {
        List<Reservation> expectedList = new ArrayList<>();
        expectedList.add(expected);

        Mockito.when(reservationRepository.findByGuestId(1))
                .thenReturn(expectedList);

        assertThat(reservationService.findByGuestId(1))
                .isEqualTo(expectedList);
    }
}