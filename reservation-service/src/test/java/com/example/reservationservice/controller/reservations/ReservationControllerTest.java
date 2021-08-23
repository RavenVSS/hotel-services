package com.example.reservationservice.controller.reservations;

import com.example.reservationservice.controller.reservations.dto.in.ReservationCreateDto;
import com.example.reservationservice.controller.reservations.dto.out.ReservationDto;
import com.example.reservationservice.feignservice.UserFeignService;
import com.example.reservationservice.model.reservations.ActualStatus;
import com.example.reservationservice.model.reservations.PayStatus;
import com.example.reservationservice.model.reservations.Reservation;
import com.example.reservationservice.model.users.User;
import com.example.reservationservice.repository.reservations.ReservationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username="admin",roles={"USER","WORKER"})
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ReservationMapper reservationMapper;
    @MockBean
    private ReservationRepository reservationRepository;
    @MockBean
    private UserFeignService userFeignService;

    private Reservation expected;
    private ReservationCreateDto createArg;
    private DateFormat dateFormat;
    private Date expectedDate;


    @BeforeEach
    void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        objectMapper.setDateFormat(dateFormat);
        expectedDate = dateFormat.parse("2000-01-01");

        expected = Reservation.builder()
                .id(1)
                .actualStatus(ActualStatus.ACTUAL)
                .beginDate(expectedDate)
                .endDate(expectedDate)
                .comment("Test")
                .guestId(1)
                .money(1000)
                .workerId(1)
                .paymentMethodId(1)
                .payStatus(PayStatus.PAID)
                .receipt(1000)
                .roomId(1)
                .build();

        createArg = ReservationCreateDto.builder()
                .actualStatus(ActualStatus.ACTUAL)
                .beginDate(expectedDate)
                .endDate(expectedDate)
                .comment("Test")
                .guestId(1)
                .money(1000)
                .paymentMethodId(1)
                .payStatus(PayStatus.PAID)
                .receipt(1000)
                .roomId(1)
                .build();
    }

    @Test
    @DisplayName("Создание записи бронирования")
    void addNewReservation() throws Exception {
        // Arrange
        User user = mock(User.class);
        when(user.getUserId())
                .thenReturn(1);
        when(userFeignService.getCurrentUser())
                .thenReturn(user);

        ArgumentCaptor<Reservation> argument = ArgumentCaptor.forClass(Reservation.class);

        when(reservationRepository.save(argument.capture()))
                .thenReturn(expected);
        expected.setId(null);

        // Act
        mockMvc.perform(post("/reservations/create")
                         .contentType("application/json")
                         .content(objectMapper.writeValueAsString(createArg)))
                 .andExpect(status().isCreated())
                 .andReturn();

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
                                    expectedDate,
                                    expectedDate,
                                    "Test");
    }

    @Test
    @DisplayName("Обновление записи бронирования")
    void updateReservation() throws Exception {
        // Arrange
        ArgumentCaptor<Reservation> argument = ArgumentCaptor.forClass(Reservation.class);

        when(reservationRepository.findById(1))
                .thenReturn(Optional.of(expected));
        when(reservationRepository.save(argument.capture()))
                .thenReturn(expected);

        // Act
        mockMvc.perform(post("/reservations/1/update")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(createArg)))
                    .andExpect(status().isOk())
                    .andReturn();

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
                                    expectedDate,
                                    expectedDate,
                                    "Test", 1);

        verify(reservationRepository).save(expected);
    }

    @Test
    @DisplayName("Удаление записи бронирования")
    void deleteReservation() throws Exception {
        // Arrange
        when(reservationRepository.findById(expected.getId()))
                .thenReturn(Optional.of(expected));

        doNothing().when(reservationRepository).deleteById(expected.getId());

        // Act
        reservationRepository.deleteById(expected.getId());

        // Assert
        verify(reservationRepository).deleteById(expected.getId());
    }

    @Test
    @DisplayName("Получение записи бронирования по ID")
    void getAtReservation() throws Exception {
        // Arrange
        when(reservationRepository.findById(expected.getId()))
                .thenReturn(Optional.of(expected));

        ReservationDto expectedDto = reservationMapper.toDto(expected);

        // Act
        MvcResult mvcResult = mockMvc.perform(get("/reservations/1"))
                                        .andExpect(status().isOk())
                                        .andReturn();

        // Assert
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(expectedDto));

        verify(reservationRepository).findById(expected.getId());
    }

    @Test
    @DisplayName("Получение всех записей бронирования")
    void getAllReservations() throws Exception {
        // Arrange
        List<Reservation> expectedList = Arrays.asList(mock(Reservation.class), mock(Reservation.class));

        List<ReservationDto> expectedListDto = reservationMapper.toList(expectedList);

        when(reservationRepository.findAll())
                .thenReturn(expectedList);

        // Act
        MvcResult mvcResult = mockMvc.perform(get("/reservations/list"))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(expectedListDto));

        verify(reservationRepository).findAll();
    }

    @Test
    @DisplayName("Получение всех записей бронирования по имени и фамилии")
    void getReservationsByName() throws Exception {
        // Arrange
        List<Reservation> expectedList = Arrays.asList(expected, expected);
        List<ReservationDto> expectedListDto = reservationMapper.toList(expectedList);

        when(reservationRepository.findByName("Иван", "Иванов"))
                .thenReturn(expectedList);

        // Act
        MvcResult mvcResult = mockMvc.perform(get("/reservations/search/name?firstName=Иван&secondName=Иванов"))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(expectedListDto));

        verify(reservationRepository).findByName("Иван", "Иванов");
    }

    @Test
    @DisplayName("Получение всех записей бронирования по дате")
    void getReservationsByDate() throws Exception {
        // Arrange
        List<Reservation> expectedList = Arrays.asList(expected, expected);
        List<ReservationDto> expectedListDto = reservationMapper.toList(expectedList);

        when(reservationRepository.findByBeginDate(expectedDate))
                .thenReturn(expectedList);

        // Act
        MvcResult mvcResult = mockMvc.perform(get("/reservations/search/date?beginDate=" + dateFormat.format(expectedDate)))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(expectedListDto));

        verify(reservationRepository).findByBeginDate(expectedDate);
    }

    @Test
    @DisplayName("Получение всех записей бронирования текущего пользователя")
    void getReservationsCurrentUser() throws Exception {
        // Arrange
        List<Reservation> expectedList = Arrays.asList(expected, expected);
        List<ReservationDto> expectedListDto = reservationMapper.toList(expectedList);

        when(reservationRepository.findByGuestId(1))
                .thenReturn(expectedList);

        User user = mock(User.class);
        when(user.getUserId())
                .thenReturn(1);
        when(userFeignService.getCurrentUser())
                .thenReturn(user);

        // Act
        MvcResult mvcResult = mockMvc.perform(get("/reservations/current"))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(expectedListDto));

        verify(reservationRepository).findByGuestId(1);
    }
}