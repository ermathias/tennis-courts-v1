package com.tenniscourts.reservations;

import com.tenniscourts.guests.Guest;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.tenniscourts.TennisCourt;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ReservationService.class)
public class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Test
    public void givenAReservation_whenHoursBetweenNowAndScheduleIsGreaterOrEqual24HoursAndGetRefundValue_thenReturnReservationValueWithTax() {
        LocalDateTime testReferenceDate = LocalDate.of(2021, 07, 01).atStartOfDay();

        Guest guestMock = Guest.builder().name("Roger Federer").build();

        TennisCourt tennisCourtMock = TennisCourt.builder().name("Roland Garros - Court Philippe-Chatrier").build();

        Schedule schedulMock = Schedule.builder()
                .tennisCourt(tennisCourtMock)
                .startDateTime(testReferenceDate.plusDays(2).plusHours(6))
                .endDateTime(testReferenceDate.plusDays(2).plusHours(22))
                .build();

        Reservation reservationMock = Reservation.builder()
                .guest(guestMock)
                .schedule(schedulMock)
                .startDateTime(testReferenceDate.plusDays(2).plusHours(6))
                .endDateTime(testReferenceDate.plusDays(2).plusHours(7))
                .value(BigDecimal.TEN)
                .reservationStatus(ReservationStatus.READY_TO_PLAY)
                .refundValue(BigDecimal.ZERO)
                .build();

        BigDecimal refundValueTest = reservationService.getRefundValue(testReferenceDate, reservationMock);
        assertEquals(BigDecimal.TEN, refundValueTest);
    }

    @Test
    public void givenAReservation_whenHoursBetweenNowAndScheduleIsGreaterOrEqual12HoursAndIsLessThan24HoursAndGetRefundValue_thenReturnReservationValueWithTax() {
        LocalDateTime testReferenceDate = LocalDate.of(2021, 07, 01).atStartOfDay();

        Guest guestMock = Guest.builder().name("Roger Federer").build();

        TennisCourt tennisCourtMock = TennisCourt.builder().name("Roland Garros - Court Philippe-Chatrier").build();

        Schedule schedulMock = Schedule.builder()
                .tennisCourt(tennisCourtMock)
                .startDateTime(testReferenceDate.plusHours(6))
                .endDateTime(testReferenceDate.plusHours(22))
                .build();

        Reservation reservationMock = Reservation.builder()
                .guest(guestMock)
                .schedule(schedulMock)
                .startDateTime(testReferenceDate.plusHours(21))
                .endDateTime(testReferenceDate.plusHours(22))
                .value(BigDecimal.TEN)
                .reservationStatus(ReservationStatus.READY_TO_PLAY)
                .refundValue(BigDecimal.ZERO)
                .build();

        BigDecimal refundValueTest = reservationService.getRefundValue(testReferenceDate, reservationMock);
        assertEquals(new BigDecimal("7.50"), refundValueTest);
    }

    @Test
    public void givenAReservation_whenHoursBetweenNowAndScheduleIsGreaterOrEqual2HoursAndIsLessThan12HoursAndGetRefundValue_thenReturnReservationValueWithTax() {
        LocalDateTime testReferenceDate = LocalDate.of(2021, 07, 01).atStartOfDay();

        Guest guestMock = Guest.builder().name("Roger Federer").build();

        TennisCourt tennisCourtMock = TennisCourt.builder().name("Roland Garros - Court Philippe-Chatrier").build();

        Schedule schedulMock = Schedule.builder()
                .tennisCourt(tennisCourtMock)
                .startDateTime(testReferenceDate.plusHours(6))
                .endDateTime(testReferenceDate.plusHours(22))
                .build();

        Reservation reservationMock = Reservation.builder()
                .guest(guestMock)
                .schedule(schedulMock)
                .startDateTime(testReferenceDate.plusHours(6))
                .endDateTime(testReferenceDate.plusHours(7))
                .value(BigDecimal.TEN)
                .reservationStatus(ReservationStatus.READY_TO_PLAY)
                .refundValue(BigDecimal.ZERO)
                .build();

        BigDecimal refundValueTest = reservationService.getRefundValue(testReferenceDate, reservationMock);
        assertEquals(new BigDecimal("5.0"), refundValueTest);
    }

    @Test
    public void givenAReservation_whenHoursBetweenNowAndScheduleIsGreaterOrEqual1MinuteAndIsLessThan2HoursAndGetRefundValue_thenReturnReservationValueWithTax() {
        LocalDateTime testReferenceDate = LocalDate.of(2021, 07, 01).atStartOfDay();

        Guest guestMock = Guest.builder().name("Roger Federer").build();

        TennisCourt tennisCourtMock = TennisCourt.builder().name("Roland Garros - Court Philippe-Chatrier").build();

        Schedule schedulMock = Schedule.builder()
                .tennisCourt(tennisCourtMock)
                .startDateTime(testReferenceDate.plusHours(6))
                .endDateTime(testReferenceDate.plusHours(22))
                .build();

        Reservation reservationMock = Reservation.builder()
                .guest(guestMock)
                .schedule(schedulMock)
                .startDateTime(testReferenceDate.plusHours(1))
                .endDateTime(testReferenceDate.plusHours(2))
                .value(BigDecimal.TEN)
                .reservationStatus(ReservationStatus.READY_TO_PLAY)
                .refundValue(BigDecimal.ZERO)
                .build();

        BigDecimal refundValueTest = reservationService.getRefundValue(testReferenceDate, reservationMock);
        assertEquals(new BigDecimal("2.50"), refundValueTest);
    }

    @Test
    public void givenAReservation_whenHoursBetweenNowAndScheduleIsGreaterOrEqual0MinuteAndIsLessThan1MinuteAndGetRefundValue_thenReturnReservationValueWithTax() {
        LocalDateTime testReferenceDate = LocalDate.of(2021, 07, 01).atTime(06, 00);

        Guest guestMock = Guest.builder().name("Roger Federer").build();

        TennisCourt tennisCourtMock = TennisCourt.builder().name("Roland Garros - Court Philippe-Chatrier").build();

        Schedule schedulMock = Schedule.builder()
                .tennisCourt(tennisCourtMock)
                .startDateTime(testReferenceDate.plusHours(0))
                .endDateTime(testReferenceDate.plusHours(1))
                .build();

        Reservation reservationMock = Reservation.builder()
                .guest(guestMock)
                .schedule(schedulMock)
                .startDateTime(testReferenceDate.plusHours(0))
                .endDateTime(testReferenceDate.plusHours(1))
                .value(BigDecimal.TEN)
                .reservationStatus(ReservationStatus.READY_TO_PLAY)
                .refundValue(BigDecimal.ZERO)
                .build();

        BigDecimal refundValueTest = reservationService.getRefundValue(testReferenceDate, reservationMock);
        assertEquals(BigDecimal.ZERO, refundValueTest);
    }
}
