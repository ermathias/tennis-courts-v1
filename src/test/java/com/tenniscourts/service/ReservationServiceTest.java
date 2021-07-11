package com.tenniscourts.service;

import com.tenniscourts.dto.CreateReservationRequestDTO;
import com.tenniscourts.dto.ReservationDTO;
import com.tenniscourts.dto.ScheduleDTO;
import com.tenniscourts.mapper.ReservationMapper;
import com.tenniscourts.model.Guest;
import com.tenniscourts.model.Reservation;
import com.tenniscourts.model.ReservationStatus;
import com.tenniscourts.model.Schedule;
import com.tenniscourts.repository.ReservationRepository;
import com.tenniscourts.service.ReservationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ReservationService.class)
public class ReservationServiceTest {


    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationMapper reservationMapper;

    @Mock
    private GuestService guestService;

    @Mock
    private ScheduleService scheduleService;


    @Before
    public void setup(){
        reservationService = new ReservationService(
                reservationRepository,reservationMapper,guestService, scheduleService, 10);
    }

    @Test
    public void getRefundValueFullRefundTest() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusDays(2);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal(10));
    }

    @Test
    public void bookReservationTest() {
        CreateReservationRequestDTO createReservationRequestDTO = new CreateReservationRequestDTO();
        createReservationRequestDTO.setGuestId(1L);
        createReservationRequestDTO.setScheduleId(1L);

        bookReservationMock();

        ReservationDTO reservationDTO = reservationService.bookReservation(createReservationRequestDTO);

        Assert.assertEquals(reservationDTO.getGuestId(), Long.getLong("1L"));


    }


    @Test
    public void cancelReservationTest() {

        cancelReservationMock();

        ReservationDTO reservationDTO = reservationService.cancelReservation(1L);

        Assert.assertEquals(reservationDTO.getValue(), new BigDecimal(10));


    }

    private void bookReservationMock(){
        Reservation reservation = new Reservation();
        Guest guest = new Guest();
        guest.setName("John");
        reservation.setGuest(guest);
        reservation.setValue(new BigDecimal(10));
        Schedule schedule = new Schedule();
        schedule.setStartDateTime(LocalDateTime.now().plusDays(1));
        schedule.setEndDateTime(LocalDateTime.now().plusDays(2));
        reservation.setSchedule(schedule);
        ReservationDTO reservationDTOMock = new ReservationDTO();
        reservationDTOMock.setValue(new BigDecimal(10));
        when(reservationMapper.map(Mockito.any(CreateReservationRequestDTO.class))).thenReturn(reservation);
        when(reservationMapper.map(Mockito.any(Reservation.class))).thenReturn(reservationDTOMock);
        when(reservationRepository.save(Mockito.any(Reservation.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }

    private void cancelReservationMock(){
        ReservationDTO reservationDTOMock = new ReservationDTO();
        reservationDTOMock.setValue(new BigDecimal(10));
        Optional<Reservation> reservation = Optional.of(new Reservation());
        reservation.get().setId(1L);
        Schedule schedule = new Schedule();
        schedule.setStartDateTime(LocalDateTime.now().plusDays(2));
        schedule.setId(1L);
        reservation.get().setSchedule(schedule);
        reservation.get().setValue(new BigDecimal("10"));
        Guest guest = new Guest();
        guest.setName("John");
        guest.setId(1L);
        reservation.get().setGuest(guest);
        when(reservationRepository.findById(any())).thenReturn(reservation);
        when(reservationRepository.save(any())).thenReturn(reservation.get());
        when(reservationMapper.map(Mockito.any(Reservation.class))).thenReturn(reservationDTOMock);
    }


    @Test
    public void rescheduleReservationTest() {

        cancelReservationMock();

        List<ScheduleDTO> freeSlots = new ArrayList<>();

        ScheduleDTO freeSlot = new ScheduleDTO();
        freeSlot.setId(1L);
        freeSlot.setStartDateTime(LocalDateTime.now().plusDays(2));
        freeSlot.setEndDateTime(LocalDateTime.now().plusDays(2).plusHours(1));

        freeSlots.add(freeSlot);


        when(scheduleService.findFreeSlots(any())).thenReturn(freeSlots);

        bookReservationMock();

        ReservationDTO reservationDTO = reservationService.rescheduleReservation(1L, 1L);

        Assert.assertEquals(reservationDTO.getValue(), new BigDecimal(10));

    }

}