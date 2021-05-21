package com.tenniscourts.reservations;

import com.tenniscourts.schedules.Schedule;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.ScheduleRepository;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import org.mapstruct.factory.Mappers;
import static org.mockito.BDDMockito.given;

import org.mockito.Mock;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ReservationService.class)

public class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;
    @Spy
    ReservationMapper reservationMapper = Mappers.getMapper(ReservationMapper.class);

    @Mock
    GuestRepository guestRepository;

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    TennisCourtRepository tennisCourtRepository;

    @Mock
    ReservationRepository reservationRepository;



    @Test
    public void getRefundValueFullRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusDays(2);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal(10));
    }

    @Test
    public void keepTwentyFivePercentOfFee() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusHours(20);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal("7.50"));
    }

    @Test
    public void keepSeventyFivePercentOfFee() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusHours(1);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal("2.50"));
    }

    @Test
    public void keepHalfOfFee() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusHours(4);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal("5.0"));
    }

    @Test
    public void bookReservationTest() {
        Schedule schedule = Schedule.builder().startDateTime(LocalDateTime.now().plusDays(4)).build();
        schedule.setId(1L);
        schedule.setStartDateTime(LocalDateTime.now().plusDays(4));
        schedule.setEndDateTime(schedule.getStartDateTime().plusHours(1));
        schedule.setTennisCourt(new TennisCourt("Tennis Court Example"));

        given(scheduleRepository.findById(1L)).willReturn(java.util.Optional.of(schedule));

        Guest guest = Guest.builder().name("Dev 1").build();
        guest.setId(1L);
        given(guestRepository.findById(1L)).willReturn(java.util.Optional.of(guest));

        CreateReservationRequestDTO createReservationRequestDTO = CreateReservationRequestDTO.builder()
                .guestId(1L)
                .scheduleId(1L)
                .build();

        ReservationDTO reservationDTO = reservationService.bookReservation(createReservationRequestDTO);

        Assert.assertEquals(reservationDTO.getGuest().getId(), createReservationRequestDTO.getGuestId());
        Assert.assertEquals(reservationDTO.getSchedule().getId(), createReservationRequestDTO.getScheduleId());
    }

}