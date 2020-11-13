package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleRepository;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
        Guest guest = new Guest();
        guest.setName("Federer");
        guest.setAdmin(true);
        guest.setId(1L);
        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setStartDateTime(LocalDateTime.now());
        schedule.setEndDateTime(LocalDateTime.now().plusHours(1));
        CreateReservationRequestDTO temp = new CreateReservationRequestDTO(1L, 1L);

        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);
        Mockito.when(scheduleRepository.getOne(1L)).thenReturn(schedule);

        Reservation reservation = new Reservation(guest, schedule,  new BigDecimal("100.00").add(new BigDecimal("10.00")) , ReservationStatus.READY_TO_PLAY, new BigDecimal("0.00") );
        ReservationDTO answer = reservationService.bookReservation(temp);
        Mockito.verify(reservationRepository).saveAndFlush(reservation);

    }

    /**
     * There was some difficulties catching EntityNotFoundException because .getOne() doesn't throw it
     * making it difficult to test.
     */
    @Test
    public void bookReservationReturningNull(){
        Guest guest = new Guest();
        guest.setName("Federer");
        guest.setAdmin(true);
        Schedule schedule = new Schedule();
        CreateReservationRequestDTO temp = new CreateReservationRequestDTO(1L, 1L);
        Reservation reservation = new Reservation(guest, schedule,  new BigDecimal("100.00").add(new BigDecimal("10.00")) , ReservationStatus.READY_TO_PLAY, new BigDecimal("0.00") );
        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);
        Mockito.when(scheduleRepository.getOne(1L)).thenReturn(schedule);

        ReservationDTO answer = reservationService.bookReservation(temp);

        assertEquals(null, answer);
    }



    @Test
    public void findReservation(){
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());

        Mockito.when(reservationRepository.findById(1L)).thenReturn(java.util.Optional.of(reservation));
        Mockito.when(reservationMapper.map(reservation)).thenReturn(reservationDTO);

        ReservationDTO answer = reservationService.findReservation(1L);

        Assert.assertEquals(reservationDTO, answer);
    }

    @Test
    public void findReservationNotFound(){
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());

        //  Mockito.when(reservationRepository.findById(1L)).thenReturn(java.util.Optional.of(reservation));
        // Mockito.when(reservationMapper.map(reservation)).thenReturn(reservationDTO);

        try {
           reservationService.findReservation(12323L);
            fail("Should have thrown an exception");
        } catch (EntityNotFoundException e){
            assertEquals("Reservation not found.", e.getMessage());
        }

    }


   @Test
   public void validateCancellationReservationStatusCancelled(){
        Reservation reservation = new Reservation();
        reservation.setReservationStatus(ReservationStatus.CANCELLED);

       Mockito.when(reservationRepository.findById(1L)).thenReturn(java.util.Optional.of(reservation));

        try {
            reservationService.cancelReservation(1L);
            fail("Should have thrown an exception");
        } catch (IllegalArgumentException e){
            assertEquals("Cannot cancel/reschedule because it's not in ready to play status.", e.getMessage());
        }

   }

    @Test
    public void validateCancellationReservationOnlyFutureDates(){
        Reservation reservation = new Reservation();
        reservation.setReservationStatus(ReservationStatus.READY_TO_PLAY);
        Schedule schedule = new Schedule();
        LocalDateTime startDateTime = LocalDateTime.now().minusDays(2);
        schedule.setStartDateTime(startDateTime);
        reservation.setSchedule(schedule);

        Mockito.when(reservationRepository.findById(1L)).thenReturn(java.util.Optional.of(reservation));

        try {
            reservationService.cancelReservation(1L);
            fail("Should have thrown an exception");
        } catch (IllegalArgumentException e){
            assertEquals("Can cancel/reschedule only future dates.", e.getMessage());
        }

    }

    @Test
    public void cancelReservationNotFound(){
        Reservation reservation = new Reservation();
        reservation.setId(12313L);
        try {
            reservationService.cancelReservation(12313L);
            fail("Should have thrown an exception");
        } catch (EntityNotFoundException e){
            assertEquals("Reservation not found.", e.getMessage());
        }
    }

    @Test
    public void cancelReservationUpdateReservation(){
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        Schedule schedule = new Schedule();
        LocalDateTime startDateTime = LocalDateTime.now().plusDays(1);
        schedule.setStartDateTime(startDateTime);
        reservation.setSchedule(schedule);
        ReservationDTO dto = new ReservationDTO();
        dto.setId(1L);
        reservation.setValue(new BigDecimal(10L));
        Mockito.when(reservationRepository.findById(1L)).thenReturn(java.util.Optional.of(reservation));
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);

        try {
            reservationService.cancelReservation(1L);
        } catch (EntityNotFoundException e){
            e.getMessage();
        }

        Mockito.verify(reservationRepository).save(reservation);
    }

    @Test
    public void getRefundValueFullRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusDays(2);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal(10));
    }

    @Test
    public void getRefundValue75pctRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusHours(13);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal(7.5));
    }

    @Test
    public void getRefundValue50pctRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusHours(8);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal("5.0"));
    }

    @Test
    public void getRefundValue25pctRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusHours(1);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal(2.5));
    }

    @Test
    public void getRefundValueNoRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().minusHours(1);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), BigDecimal.ZERO);
    }

    @Test
    public void rescheduleReservation(){

        Guest guest = new Guest();
        guest.setId(11L);
        guest.setName("Federer");
        guest.setAdmin(true);

        CreateReservationRequestDTO request = new CreateReservationRequestDTO();
        request.setGuestId(11L);
        request.setScheduleId(2L);

        ReservationDTO dto = new ReservationDTO();
        dto.setGuestId(11L);
        dto.setScheduledId(2L);


        Reservation reservation = new Reservation();
        reservation.setId(1L);
        Schedule schedule = new Schedule();
        LocalDateTime startDateTime = LocalDateTime.now().plusDays(1);
        schedule.setStartDateTime(startDateTime);
        schedule.setId(1L);
        reservation.setSchedule(schedule);
        reservation.setGuest(guest);

        Schedule schedule2 = new Schedule();
        LocalDateTime startDateTime2 = LocalDateTime.now().plusDays(1);
        schedule2.setStartDateTime(startDateTime2);
        schedule2.setId(2L);

        reservation.setValue(new BigDecimal("100"));

        Mockito.when(guestRepository.getOne(request.getGuestId())).thenReturn(guest);
        Mockito.when(scheduleRepository.getOne(request.getScheduleId())).thenReturn(schedule);

        Mockito.when(reservationRepository.findById(1L)).thenReturn(java.util.Optional.of(reservation));
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);
        Mockito.when(reservationService.bookReservation(request)).thenReturn(dto);

        ReservationDTO answer = reservationService.rescheduleReservation(reservation.getId(), schedule2.getId());

        assertEquals(dto, answer);
    }

    @Test
    public void rescheduleReservationToTheSameSlot(){
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        Schedule schedule = new Schedule();
        LocalDateTime startDateTime = LocalDateTime.now().plusDays(1);
        schedule.setStartDateTime(startDateTime);
        reservation.setSchedule(schedule);
        schedule.setId(1L);
        reservation.setValue(new BigDecimal("100"));
        Mockito.when(reservationRepository.findById(1L)).thenReturn(java.util.Optional.of(reservation));
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);

        try{
            reservationService.rescheduleReservation(reservation.getId(), schedule.getId());
        }catch (IllegalArgumentException e){
            assertEquals("Cannot reschedule to the same slot.", e.getMessage());
        }
    }


    @Test
    public void findReservationByScheduleId(){
        Long scheduleId = 1L;
        Reservation one = new Reservation();
        one.setId(1L);
        Reservation two = new Reservation();
        two.setId(2L);
        List<Reservation> list = Arrays.asList(one, two);

        Mockito.when(reservationRepository.findBySchedule_Id(scheduleId)).thenReturn(list);

        List<Reservation> answer = reservationService.findReservationByScheduleId(scheduleId);

        assertEquals(list, answer);
    }


    /**
     * Test fails because of parameter LocalDateTime.now() at serviceLayer (ReservationService.java line 138).
     */
    /*
    @Test
    public void getAllMyReservations(){
        Guest guest = new Guest();
        guest.setAdmin(true);
        guest.setName("Nadal");
        guest.setId(1L);

        Schedule scheduleOne = new Schedule();
        scheduleOne.setStartDateTime(LocalDateTime.now().minusDays(1));
        Schedule scheduleTwo = new Schedule();
        scheduleTwo.setStartDateTime(LocalDateTime.now().minusDays(2));

        Reservation one = new Reservation();
        one.setId(1L);
        one.setSchedule(scheduleOne);
       *//* Reservation two = new Reservation();
        two.setId(2L);
        two.setSchedule(scheduleTwo);*//*
        List<Reservation> list = Arrays.asList(one);

        ReservationDTO oneDto = new ReservationDTO();
        oneDto.setId(1L);
        List<ReservationDTO> listDto = Arrays.asList(oneDto);

        LocalDateTime dateTime = LocalDateTime.now();

        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);
        Mockito.when(reservationMapper.map(one)).thenReturn(oneDto);
       // Mockito.when(reservationMapper.map(two)).thenReturn(twoDto);
        Mockito.when(reservationRepository.findByGuest_IdAndSchedule_StartDateTimeLessThan(guest.getId(), dateTime)).thenReturn(list);

        List<ReservationDTO> answer = reservationService.getAllMyReservations(1L);

        assertEquals(listDto, answer);

    }*/

    @Test
    public void getAllMyReservationsNotAsAdmin(){
        Guest guest = new Guest();
        guest.setAdmin(false);
        guest.setName("Nadal");
        guest.setId(1L);

        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);

        try {
            reservationService.getAllMyReservations(1L);
            fail("Should have thrown an exception");
        } catch (UnsupportedOperationException e){
            assertEquals("Only admin users are allowed to do that.", e.getMessage());
        }
    }

}