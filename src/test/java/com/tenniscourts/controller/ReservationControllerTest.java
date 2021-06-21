package com.tenniscourts.controller;

import com.tenniscourts.Fixtures;
import com.tenniscourts.model.Guest;
import com.tenniscourts.model.Schedule;
import com.tenniscourts.repository.GuestRepository;
import com.tenniscourts.repository.ScheduleRepository;
import com.tenniscourts.service.ReservationService;
import com.tenniscourts.storage.CreateReservationRequestDTO;
import com.tenniscourts.storage.ReservationDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@RunWith(MockitoJUnitRunner.Silent.class)
@ContextConfiguration(classes = ReservationService.class)
public class ReservationControllerTest {

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ReservationService reservationService;

    @Test
    public void bookReservationTest() {
        Schedule schedule = Fixtures.buildSchedule();
        schedule.setStartDateTime(LocalDateTime.now().plusDays(1));
        given(scheduleRepository.findById(1L)).willReturn(java.util.Optional.of(schedule));
        Guest guest = Guest.builder().name("Marie Curie").build();
        guest.setId(1L);
        given(guestRepository.findById(1L)).willReturn(Optional.of(guest));
        given(reservationService.bookReservation(Fixtures.buildCreateReservationRequestDTO()))
                .willReturn(Fixtures.buildReservationDTO());

        CreateReservationRequestDTO createReservationRequestDTO = CreateReservationRequestDTO.builder()
                .guestId(1L)
                .scheduleId(1L)
                .build();

        ReservationDTO reservationDTO = reservationService.bookReservation(createReservationRequestDTO);

        Assert.assertEquals(reservationDTO.getSchedule().getId(), createReservationRequestDTO.getScheduleId());
    }
}
