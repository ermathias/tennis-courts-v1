package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleRepository;
import com.tenniscourts.tenniscourts.TennisCourt;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ReservationService.class)
public class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    GuestRepository guestRepository;

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    ReservationMapper reservationMapper;

    @Test(expected = EntityNotFoundException.class)
    public void bookReservationThrowsEntityNotFoundExceptionWhenGuestIsNotFound() {
        CreateReservationRequestDTO createReservationRequestDTO = CreateReservationRequestDTO.builder().guestId(1L).build();

        when(guestRepository.findById(1L)).thenReturn(Optional.empty());

        reservationService.bookReservation(createReservationRequestDTO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void bookReservationThrowsIllegalArgumentExceptionWhenTheDTOSchedulesAndDBSchedulesDontMatch() {
        List<Schedule> scheduleList = Arrays.asList(new Schedule(), new Schedule());
        CreateReservationRequestDTO createReservationRequestDTO =
                CreateReservationRequestDTO.builder().guestId(1L).scheduleIds(Arrays.asList(1L, 2L, 3L)).build();

        when(scheduleRepository.findAllById(createReservationRequestDTO.getScheduleIds())).thenReturn(scheduleList);
        when(guestRepository.findById(1L)).thenReturn(Optional.of(new Guest()));


        reservationService.bookReservation(createReservationRequestDTO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void bookReservationThrowsIllegalArgumentExceptionWhenTheDBSchedulesHaveTheSameTennisCourts() {
        TennisCourt tennisCourt = new TennisCourt();
        List<Schedule> scheduleList = Arrays.asList(Schedule.builder().tennisCourt(tennisCourt).build(), Schedule.builder().tennisCourt(tennisCourt).build());
        CreateReservationRequestDTO createReservationRequestDTO =
                CreateReservationRequestDTO.builder().guestId(1L).scheduleIds(Arrays.asList(1L, 2L)).build();

        when(scheduleRepository.findAllById(createReservationRequestDTO.getScheduleIds())).thenReturn(scheduleList);
        when(guestRepository.findById(1L)).thenReturn(Optional.of(new Guest()));


        reservationService.bookReservation(createReservationRequestDTO);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findReservationThrowsEntityNotFoundExceptionWhenReservationIsNotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        reservationService.findReservation(1L);
    }

    @Test(expected = EntityNotFoundException.class)
    public void cancelReservationThrowsEntityNotFoundExceptionWhenReservationIsNotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        reservationService.cancelReservation(1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cancelReservationThrowsIllegalArgumentExceptionWhenReservationStatusIsNotReadyToPlay() {
        Reservation reservation = Reservation.builder().reservationStatus(ReservationStatus.CANCELLED).build();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        reservationService.cancelReservation(1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cancelReservationThrowsIllegalArgumentExceptionWhenStartDateTimeIsInThePast() {
        Reservation reservation = Reservation.builder()
                .reservationStatus(ReservationStatus.READY_TO_PLAY)
                .schedule(Schedule.builder().startDateTime(LocalDateTime.now().minusHours(1)).build())
                .build();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        reservationService.cancelReservation(1L);
    }

    @Test
    public void getRefundValueFullRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusDays(2);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal(10));
    }

    @Test
    public void getRefundValueHoursLessThan24() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusHours(19);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(
                reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal("7.50"));
    }

    @Test
    public void getRefundValueHoursLessThan12() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusHours(11);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()),
                new BigDecimal("5.00"));
    }

    @Test
    public void getRefundValueHoursLessThan2() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusHours(1);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()),
                new BigDecimal("2.50"));
    }
}