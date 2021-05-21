package com.tenniscourts.reservations;

import com.tenniscourts.guests.Guest;
import com.tenniscourts.schedules.Schedule;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test logic not covered by integration tests
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ReservationService.class)
public class ReservationTest {

    @Test
    public void reservationPast_shouldRefundZero() {
        Reservation reservation = mock(-1);
        assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(reservation.calcRefundValue()));
    }

    @Test
    public void reservation1Hour_shouldRefund2_5() {
        Reservation reservation = mock(1);
        assertThat(new BigDecimal("2.5"), Matchers.comparesEqualTo(reservation.calcRefundValue()));
    }

    @Test
    public void reservation10Hours_shouldRefund5() {
        Reservation reservation = mock(10);
        assertThat(new BigDecimal("5"), Matchers.comparesEqualTo(reservation.calcRefundValue()));
    }

    @Test
    public void reservation20Hours_shouldRefund7_5() {
        Reservation reservation = mock(20);
        assertThat(new BigDecimal("7.5"), Matchers.comparesEqualTo(reservation.calcRefundValue()));
    }

    @Test
    public void reservation30Hours_shouldRefundFull() {
        Reservation reservation = mock(30);
        assertThat(BigDecimal.TEN, Matchers.comparesEqualTo(reservation.calcRefundValue()));
    }

    private Reservation mock(int hours) {
        Schedule schedule = new Schedule();
        schedule.setStartDateTime(LocalDateTime.now().plusHours(hours));
        return new Reservation(new Guest(), schedule);
    }
}