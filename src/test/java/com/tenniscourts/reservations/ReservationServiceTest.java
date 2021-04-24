package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.FixtureGuest;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.FixtureSchedules;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleRepository;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void bookReservation(){
        var guest = FixtureGuest.fixtureGuest();
        var schedule = FixtureSchedules.fixtureScheduleWithEndTime();
        var createReservationRequestDTO = new CreateReservationRequestDTO(1L, 1L);

        Mockito.when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));
        Mockito.when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        Reservation reservation = new Reservation(guest, schedule,  new BigDecimal("100.00").add(new BigDecimal("10.00")) , ReservationStatus.READY_TO_PLAY, new BigDecimal("0.00") );
        reservationService.bookReservation(createReservationRequestDTO);
        Mockito.verify(reservationRepository).saveAndFlush(reservation);
    }

    @Test(expected = EntityNotFoundException.class)
    public void bookReservationEntityNotFoundException(){
        CreateReservationRequestDTO temp = new CreateReservationRequestDTO(1L, 1L);
        reservationService.bookReservation(temp);
    }

    @Test
    public void findReservation(){
        var reservation = FixtureReservations.fixtureReservation();
        var reservationDTO = FixtureReservations.fixtureReservationDTO();

        Mockito.when(reservationRepository.findById(1L)).thenReturn(java.util.Optional.of(reservation));
        Mockito.when(reservationMapper.map(reservation)).thenReturn(reservationDTO);

        var response = reservationService.findReservation(1L);

        Assert.assertEquals(reservationDTO, response);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findReservationEntityNotFoundException() {
        reservationService.findReservation(1L);
    }

    @Test(expected = EntityNotFoundException.class)
    public void cancelReservationEntityNotFoundException(){
        Reservation reservation = new Reservation();
        reservation.setId(2323L);
        reservationService.cancelReservation(2323L);
    }

    @Test
    public void cancelReservationUpdateReservation(){

        var reservation = FixtureReservations.fixtureReservationCancellation();
        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);

        reservationService.cancelReservation(1L);

        Mockito.verify(reservationRepository).save(reservation);
    }

    @Test
    public void validateCancellationReservationStatusDifferentReadyToPlay(){
        var reservation = FixtureReservations.fixtureReservationWithStatusCancelled();

        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        try {
            reservationService.cancelReservation(1L);
            fail("Should have thrown an exception");
        } catch (IllegalArgumentException e){
            assertEquals("Cannot cancel/reschedule because it's not in ready to play status.", e.getMessage());
        }

    }

    @Test
    public void validateCancellationReservationOnlyFutureDates(){
        var reservation = FixtureReservations.fixtureReservationWithExpiredSchedule();

        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        try {
            reservationService.cancelReservation(1L);
            fail("Should have thrown an exception");
        } catch (IllegalArgumentException e){
            assertEquals("Can cancel/reschedule only future dates.", e.getMessage());
        }
    }

    @Test
    public void rescheduleReservation(){

        var guest = FixtureGuest.fixtureGuest();
        var firstSchedule = FixtureSchedules.fixtureScheduleReservation(1L);
        var secondSchedule = FixtureSchedules.fixtureScheduleReservation(2L);
        var reservation = FixtureReservations.fixtureReservationWithSchedules(firstSchedule);

        var createReservationRequestDTO = new CreateReservationRequestDTO(guest.getId(), 2L);

        var reservationDTO = new ReservationDTO();
        reservationDTO.setGuestId(guest.getId());
        reservationDTO.setScheduledId(2L);

        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);
        Mockito.when(guestRepository.findById(guest.getId())).thenReturn(Optional.of(guest));
        Mockito.when(scheduleRepository.findById(secondSchedule.getId())).thenReturn(Optional.of(secondSchedule));
        Mockito.when(reservationService.bookReservation(createReservationRequestDTO)).thenReturn(reservationDTO);

        var response = reservationService.rescheduleReservation(reservation.getId(), secondSchedule.getId());

        assertEquals(reservationDTO, response);
    }

    @Test
    public void rescheduleReservationToTheSameSlot(){
        var reservation =FixtureReservations.fixtureReservationWithSchedulesAndValue();

        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);

        try{
            reservationService.rescheduleReservation(reservation.getId(), reservation.getSchedule().getId());
        }catch (IllegalArgumentException e){
            assertEquals("Cannot reschedule to the same slot.", e.getMessage());
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void getAllMyReservationsEntityNotFoundException(){
        Mockito.when(guestRepository.findById(1L)).thenReturn(Optional.empty());

        reservationService.getAllMyReservations(1L);
    }

    @Test
    public void getAllMyReservations(){
        var guest = FixtureGuest.fixtureGuest();
        var reservation = FixtureReservations.fixtureReservation();
        var reservationDTO = FixtureReservations.fixtureReservationDTO();
        var reservationList = FixtureReservations.fixtureReservationList();
        var reservationDTOList = FixtureReservations.fixtureReservationDTOList();

        Mockito.when(guestRepository.findById(guest.getId())).thenReturn(Optional.of(guest));
        Mockito.when(reservationRepository.findByGuestIdAndScheduleStartDateTimeLessThan(guest.getId(), LocalDateTime.now())).thenReturn(reservationList);
        Mockito.when(reservationMapper.map(reservation)).thenReturn(reservationDTO);

        var response = reservationService.getAllMyReservations(guest.getId());

        assertEquals(reservationDTOList, response);
    }

    @Test
    public void getRefundValueFullRefund() {
        Schedule schedule = new Schedule();
        LocalDateTime startDateTime = LocalDateTime.now().plusDays(2);
        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal("10"));
    }
    @Test
    public void getRefundValue75percentRefund() {
        Schedule schedule = new Schedule();
        LocalDateTime startDateTime = LocalDateTime.now().plusHours(13);
        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal("7.5"));
    }

    @Test
    public void getRefundValue50percentRefund() {
        Schedule schedule = new Schedule();
        LocalDateTime startDateTime = LocalDateTime.now().plusHours(8);
        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal("5.0"));
    }

    @Test
    public void getRefundValue25percentRefund() {
        Schedule schedule = new Schedule();
        LocalDateTime startDateTime = LocalDateTime.now().plusHours(1);
        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal("2.5"));
    }

    @Test
    public void getRefundValueNoRefund() {
        Schedule schedule = new Schedule();
        LocalDateTime startDateTime = LocalDateTime.now().minusHours(1);
        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), BigDecimal.ZERO);
    }
}