package com.tenniscourts.service;

import com.tenniscourts.dto.CreateReservationRequestDTO;
import com.tenniscourts.dto.ReservationDTO;
import com.tenniscourts.mapper.ReservationMapper;
import com.tenniscourts.model.Guest;
import com.tenniscourts.model.Reservation;
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
                reservationRepository,reservationMapper,guestService, scheduleService, 10, 50);
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

        Reservation reservation = new Reservation();
        Guest guest = new Guest();
        guest.setName("John");
        reservation.setGuest(guest);
        reservation.setValue(new BigDecimal(45));
        Schedule schedule = new Schedule();
        schedule.setStartDateTime(LocalDateTime.now().plusDays(1));
        schedule.setEndDateTime(LocalDateTime.now().plusDays(2));
        reservation.setSchedule(schedule);

        ReservationDTO reservationDTOMock = new ReservationDTO();
        reservationDTOMock.setValue(new BigDecimal(45));

        when(reservationMapper.map(Mockito.any(CreateReservationRequestDTO.class))).thenReturn(reservation);
        when(reservationMapper.map(Mockito.any(Reservation.class))).thenReturn(reservationDTOMock);

        when(reservationRepository.save(Mockito.any(Reservation.class)))
                .thenAnswer(i -> i.getArguments()[0]);


        ReservationDTO reservationDTO = reservationService.bookReservation(createReservationRequestDTO);

        Assert.assertEquals(reservationDTO.getGuestId(), Long.getLong("1L"));


    }


    @Test
    public void cancelReservationTest() {

        ReservationDTO reservationDTOMock = new ReservationDTO();
        reservationDTOMock.setValue(new BigDecimal(45));

        Optional<Reservation> reservation = Optional.of(new Reservation());
        reservation.get().setId(1L);
        Schedule schedule = new Schedule();
        schedule.setStartDateTime(LocalDateTime.now().plusDays(2));
        schedule.setId(1L);
        reservation.get().setSchedule(schedule);
        reservation.get().setValue(new BigDecimal("45"));
        when(reservationRepository.findById(any())).thenReturn(reservation);

        when(reservationRepository.save(any())).thenReturn(reservation.get());

        when(reservationMapper.map(Mockito.any(Reservation.class))).thenReturn(reservationDTOMock);

        ReservationDTO reservationDTO = reservationService.cancelReservation(1L);

        Assert.assertEquals(reservationDTO.getValue(), new BigDecimal(45));


    }

}