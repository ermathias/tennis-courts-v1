package com.tenniscourts.reservations;

import com.tenniscourts.bean.ReservationStatus;
import com.tenniscourts.dto.CreateReservationRequestDTO;
import com.tenniscourts.dto.ReservationDTO;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.model.guests.Guest;
import com.tenniscourts.model.reservation.Reservation;
import com.tenniscourts.model.reservation.ReservationMapper;
import com.tenniscourts.model.schedule.Schedule;
import com.tenniscourts.model.tennis.TennisCourt;
import com.tenniscourts.repository.ReservationRepository;
import com.tenniscourts.service.ReservationService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ReservationService.class)
public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ReservationMapper reservationMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRefundValue() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusDays(2);
        schedule.setStartDateTime(startDateTime);
        Assert.assertEquals(BigDecimal.valueOf(10), reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10)).build()));

        startDateTime = LocalDateTime.now().plusHours(1).plusMinutes(30);
        schedule.setStartDateTime(startDateTime);
        Assert.assertEquals(BigDecimal.valueOf(7.5).setScale(2), reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10)).build()));

        startDateTime = LocalDateTime.now().plusHours(9).plusMinutes(30);
        schedule.setStartDateTime(startDateTime);
        Assert.assertEquals(BigDecimal.valueOf(5).setScale(1), reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10)).build()));

        startDateTime = LocalDateTime.now().plusHours(11).plusMinutes(30);
        schedule.setStartDateTime(startDateTime);
        Assert.assertEquals(BigDecimal.valueOf(2.5).setScale(2), reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10)).build()));
    }

    @Test
    public void testBookReservation() {
        CreateReservationRequestDTO createReservationRequestDTO = getCreateReservationRequestDTO();

        Guest guest = getGuest();

        TennisCourt tennisCourt = new TennisCourt("Roland Garos");
        Schedule schedule = getSchedule(tennisCourt);

        Reservation reservation = getReservation(guest, schedule);
        reservation.setId(Long.valueOf(1));

        when(reservationMapper.map(eq(createReservationRequestDTO))).thenReturn(reservation);

        when(reservationRepository.save(eq(reservation))).thenReturn(reservation);
        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));


        ReservationDTO reservationDTO = reservationService.bookReservation(createReservationRequestDTO);

        ReservationDTO foundReservation = reservationService.findReservation(reservation.getId());

        Assert.assertEquals(reservationDTO.getId(), foundReservation.getId());
    }

    @Test
    public void testRescheduleReservationException() {

        Reservation reservation = getReservation();

        when(reservationRepository.save(eq(reservation))).thenReturn(reservation);
        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.rescheduleReservation(Long.valueOf(1), Long.valueOf(1));
        });

        String expectedMessage = "Cannot reschedule to the same slot.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void testRescheduleReservation() {

        Reservation reservation = getReservation();

        when(reservationMapper.map(any(CreateReservationRequestDTO.class))).thenReturn(reservation).thenReturn(reservation);

        when(reservationRepository.save(eq(reservation))).thenReturn(reservation).thenReturn(reservation);
        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation)).thenReturn(Optional.of(reservation));


        ReservationDTO rescheduledReservation = reservationService.rescheduleReservation(Long.valueOf(1), Long.valueOf(2));
        Assert.assertEquals(Long.valueOf(1), rescheduledReservation.getPreviousReservation().getId());

    }

    @Test
    public void testBookReservations() {

        Reservation reservation = getReservation();
        when(reservationMapper.map(any(CreateReservationRequestDTO.class))).thenReturn(reservation).thenReturn(reservation);

        when(reservationRepository.save(eq(reservation))).thenReturn(reservation).thenReturn(reservation);
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation, reservation));

        reservationService.bookReservations(Arrays.asList(getCreateReservationRequestDTO(), getCreateReservationRequestDTO()));

        List<ReservationDTO> reservationList = reservationService.findAll();

        Assert.assertEquals(2, reservationList.size());
    }

    @Test
    public void testFindReservationException() {
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            reservationService.findReservation(Long.valueOf(1));
        });

        String expectedMessage = "Reservation not found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    private Reservation getReservation() {
        Guest guest = getGuest();

        TennisCourt tennisCourt = new TennisCourt("Roland Garos");
        Schedule schedule = getSchedule(tennisCourt);
        schedule.setId(Long.valueOf(1));

        Reservation reservation = getReservation(guest, schedule);
        reservation.setId(Long.valueOf(1));
        return reservation;
    }

    private Reservation getReservation(Guest guest, Schedule schedule) {
        return Reservation.builder()
                .guest(guest)
                .reservationStatus(ReservationStatus.READY_TO_PLAY)
                .schedule(schedule)
                .refundValue(BigDecimal.valueOf(5))
                .value(BigDecimal.valueOf(10))
                .build();
    }

    private CreateReservationRequestDTO getCreateReservationRequestDTO() {
        return CreateReservationRequestDTO.builder()
                .guestId(Long.valueOf(1))
                .scheduleId(Long.valueOf(1))
                .build();
    }

    private Guest getGuest() {
        Guest guest = Guest.builder()
                .name("Roger")
                .build();
        return guest;
    }

    private Schedule getSchedule(TennisCourt tennisCourt) {
        Schedule schedule = Schedule.builder()
                .tennisCourt(tennisCourt)
                .startDateTime(LocalDateTime.now().plusHours(2))
                .endDateTime(LocalDateTime.now().plusHours(1))
                .build();
        return schedule;
    }

}