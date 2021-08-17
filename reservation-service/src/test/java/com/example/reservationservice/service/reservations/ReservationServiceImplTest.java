package com.example.reservationservice.service.reservations;

import com.example.reservationservice.feignservice.UserFeignService;
import com.example.reservationservice.model.reservations.ActualStatus;
import com.example.reservationservice.model.reservations.PayStatus;
import com.example.reservationservice.model.reservations.Reservation;
import com.example.reservationservice.model.reservations.ReservationCreateArg;
import com.example.reservationservice.model.users.User;
import com.example.reservationservice.repository.reservations.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
                .receipt(1000)
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
                .receipt(1000)
                .roomId(1)
                .workerId(2)
                .build();

        reservationService = new ReservationServiceImpl(reservationRepository, userFeignService);
    }

    @Test
    void create() {
        // Arrange
        User user = Mockito.mock(User.class);
        when(user.getUserId())
                .thenReturn(1);

        ArgumentCaptor<Reservation> argument = ArgumentCaptor.forClass(Reservation.class);
        when(userFeignService.getCurrentUser())
                .thenReturn(user);
        when(reservationRepository.save(argument.capture()))
                .thenReturn(expected);

        // Act
        reservationService.create(reservationCreateArg);

        // Assert
        Reservation actual = argument.getValue();
        assertEquals(Integer.valueOf(1), actual.getPaymentMethodId());
        assertEquals(ActualStatus.ACTUAL, actual.getActualStatus());
        assertEquals(Integer.valueOf(1), actual.getGuestId());
        assertEquals(Integer.valueOf(1000), actual.getMoney());
        assertEquals(Integer.valueOf(1000), actual.getReceipt());
        assertEquals(Integer.valueOf(1), actual.getRoomId());
        assertEquals(Integer.valueOf(1), actual.getWorkerId());
        assertEquals(PayStatus.PAID, actual.getPayStatus());
        assertEquals(new Date(1234567L), actual.getBeginDate());
        assertEquals(new Date(1234567L), actual.getEndDate());
        assertEquals("Test", actual.getComment());
    }

    @Test
    void delete() {
        // Arrange
        when(reservationRepository.findById(expected.getId()))
                .thenReturn(Optional.of(expected));

        // Act
        reservationService.delete(expected.getId());

        // Assert
        verify(reservationRepository).deleteById(any());
    }

    @Test
    void findAt() {
        // Arrange
        when(reservationRepository.findById(expected.getId()))
                .thenReturn(Optional.of(expected));

        // Act & Assert
        assertThat(reservationService.findAt(expected.getId())).isEqualTo(expected);
    }

    @Test
    void update() {
        // Arrange
        ArgumentCaptor<Reservation> argument = ArgumentCaptor.forClass(Reservation.class);

        when(reservationRepository.findById(1))
                .thenReturn(Optional.of(expected));
        when(reservationRepository.save(argument.capture()))
                .thenReturn(expected);

        // Act
        reservationService.update(reservationCreateArg, 1);

        // Assert
        Reservation actual = argument.getValue();
        assertEquals(Integer.valueOf(1), actual.getPaymentMethodId());
        assertEquals(ActualStatus.ACTUAL, actual.getActualStatus());
        assertEquals(Integer.valueOf(1), actual.getGuestId());
        assertEquals(Integer.valueOf(1000), actual.getMoney());
        assertEquals(Integer.valueOf(1000), actual.getReceipt());
        assertEquals(Integer.valueOf(1), actual.getRoomId());
        assertEquals(Integer.valueOf(2), actual.getWorkerId());
        assertEquals(PayStatus.PAID, actual.getPayStatus());
        assertEquals(new Date(1234567L), actual.getBeginDate());
        assertEquals(new Date(1234567L), actual.getEndDate());
        assertEquals("Test", actual.getComment());
    }

    @Test
    void findAll() {
        // Arrange
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
        when(reservationRepository.findAll())
                .thenReturn(expectedList);

        // Act & Assert
        assertThat(reservationService.findAll()).isEqualTo(expectedList);
    }

    @Test
    void findByBeginDate() {
        // Arrange
        List<Reservation> expectedList = new ArrayList<>();
        expectedList.add(expected);
        when(reservationRepository.findByBeginDate(new Date(1234569L)))
                .thenReturn(expectedList);

        // Act & Assert
        assertThat(reservationService.findByBeginDate(new Date(1234569L))).isEqualTo(expectedList);
    }

    @Test
    void findByName() {
        // Arrange
        List<Reservation> expectedList = new ArrayList<>();
        expectedList.add(expected);

        when(reservationRepository.findByName("Иван", "Иванов"))
                .thenReturn(expectedList);

        // Act & Assert
        assertThat(reservationService.findByName("Иван", "Иванов"))
                .isEqualTo(expectedList);
    }

    @Test
    void findByGuestId() {
        // Arrange
        List<Reservation> expectedList = new ArrayList<>();
        expectedList.add(expected);

        when(reservationRepository.findByGuestId(1))
                .thenReturn(expectedList);

        // Act & Assert
        assertThat(reservationService.findByGuestId(1))
                .isEqualTo(expectedList);
    }
}