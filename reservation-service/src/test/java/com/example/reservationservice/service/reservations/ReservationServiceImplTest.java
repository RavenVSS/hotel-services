package com.example.reservationservice.service.reservations;

import com.example.reservationservice.feignservice.UserFeignService;
import com.example.reservationservice.model.reservations.ActualStatus;
import com.example.reservationservice.model.reservations.PayStatus;
import com.example.reservationservice.model.reservations.Reservation;
import com.example.reservationservice.model.reservations.ReservationCreateArg;
import com.example.reservationservice.model.users.User;
import com.example.reservationservice.repository.reservations.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private UserFeignService userFeignService;
    @InjectMocks
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
    }

    @Test
    @DisplayName("Создание записи бронирования")
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

        expected.setId(null);
        expected.setWorkerId(1);

        // Act
        reservationService.create(reservationCreateArg);

        // Assert
        Reservation actual = argument.getValue();

        verify(reservationRepository).save(expected);

        assertThat(actual).extracting(Reservation::getPaymentMethodId,
                                        Reservation::getActualStatus,
                                        Reservation::getGuestId,
                                        Reservation::getMoney,
                                        Reservation::getReceipt,
                                        Reservation::getRoomId,
                                        Reservation::getWorkerId,
                                        Reservation::getPayStatus,
                                        Reservation::getBeginDate,
                                        Reservation::getEndDate,
                                        Reservation::getComment)
                         .contains(1, ActualStatus.ACTUAL,
                                        1000, 1000, 1000, 1, 1,
                                        PayStatus.PAID,
                                        new Date(1234567L),
                                        new Date(1234567L),
                                        "Test");
    }

    @Test
    @DisplayName("Удаление записи бронирования")
    void delete() {
        // Arrange
        when(reservationRepository.findById(expected.getId()))
                .thenReturn(Optional.of(expected));

        // Act
        reservationService.delete(expected.getId());

        // Assert
        verify(reservationRepository).deleteById(expected.getId());
    }

    @Test
    @DisplayName("Получение записи бронирования по ID")
    void findAt() {
        // Arrange
        when(reservationRepository.findById(expected.getId()))
                .thenReturn(Optional.of(expected));

        // Act
        Reservation actual = reservationService.findAt(expected.getId());

        // Assert
        assertThat(actual).isEqualTo(expected);
        verify(reservationRepository).findById(expected.getId());
    }

    @Test
    @DisplayName("Обновление записи бронирования")
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
        assertThat(actual).extracting(Reservation::getPaymentMethodId,
                                        Reservation::getActualStatus,
                                        Reservation::getGuestId,
                                        Reservation::getMoney,
                                        Reservation::getReceipt,
                                        Reservation::getRoomId,
                                        Reservation::getWorkerId,
                                        Reservation::getPayStatus,
                                        Reservation::getBeginDate,
                                        Reservation::getEndDate,
                                        Reservation::getComment,
                                        Reservation::getId)
                            .contains(1, ActualStatus.ACTUAL,
                                    1000, 1000, 1000, 1, 1,
                                    PayStatus.PAID,
                                    new Date(1234567L),
                                    new Date(1234567L),
                                    "Test", 1);
        verify(reservationRepository).save(expected);
    }

    @Test
    @DisplayName("Получение всех записей бронирования")
    void findAll() {
        // Arrange
        List<Reservation> expectedList = Arrays.asList(mock(Reservation.class), mock(Reservation.class));
        when(reservationRepository.findAll())
                .thenReturn(expectedList);

        // Act
        List<Reservation> actual = reservationService.findAll();

        // Assert
        assertThat(actual).isEqualTo(expectedList);
        verify(reservationRepository).findAll();
    }

    @Test
    @DisplayName("Получение всех записей бронирования по дате")
    void findByBeginDate() {
        // Arrange
        List<Reservation> expectedList = Arrays.asList(expected, expected);
        when(reservationRepository.findByBeginDate(new Date(1234569L)))
                .thenReturn(expectedList);

        // Act
        List<Reservation> actual = reservationService.findByBeginDate(new Date(1234569L));

        // Assert
        assertThat(actual).isEqualTo(expectedList);
        verify(reservationRepository).findByBeginDate(new Date(1234569L));
    }

    @Test
    @DisplayName("Получение всех записей бронирования по имени и фамилии")
    void findByName() {
        // Arrange
        List<Reservation> expectedList = Arrays.asList(expected, expected);

        when(reservationRepository.findByName("Иван", "Иванов"))
                .thenReturn(expectedList);

        // Act
        List<Reservation> actual = reservationService.findByName("Иван", "Иванов");

        // Assert
        assertThat(actual).isEqualTo(expectedList);
        verify(reservationRepository).findByName("Иван", "Иванов");
    }

    @Test
    @DisplayName("Получение всех записей бронирования текущего пользователя")
    void findByGuestId() {
        // Arrange
        List<Reservation> expectedList = Arrays.asList(expected, expected);

        when(reservationRepository.findByGuestId(1))
                .thenReturn(expectedList);

        // Act
        List<Reservation> actual = reservationService.findByGuestId(1);

        // Assert
        assertThat(actual).isEqualTo(expectedList);
        verify(reservationRepository).findByGuestId(1);
    }
}