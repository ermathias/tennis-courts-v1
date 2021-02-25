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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ReservationService.class)
public class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Test
    public void getRefundValue75PercentCharged() {
        Schedule schedule = createSchedule(1);
        Reservation reservation = createReservation(schedule, BigDecimal.TEN);
        Assert.assertTrue(reservationService.getRefundValue(reservation).compareTo(new BigDecimal(2.5)) == 0);
    }

    @Test
    public void getRefundValue50PercentCharged() {
        Schedule schedule = createSchedule(11);
        Reservation reservation = createReservation(schedule, BigDecimal.TEN);
        Assert.assertTrue(reservationService.getRefundValue(reservation).compareTo(new BigDecimal(5)) == 0);
    }

    @Test
    public void getRefundValue25PercentCharged() {
        Schedule schedule = createSchedule(23);
        Reservation reservation = createReservation(schedule, BigDecimal.TEN);
        Assert.assertTrue(reservationService.getRefundValue(reservation).compareTo(new BigDecimal(7.5)) == 0);
    }

    @Test
    public void getRefundValueFullRefund() {
        Schedule schedule = createSchedule(24);
        Reservation reservation = createReservation(schedule, BigDecimal.TEN);
        Assert.assertTrue(reservationService.getRefundValue(reservation).compareTo(BigDecimal.TEN) == 0);
    }

    private Schedule createSchedule(long hoursToPlusToStartDateTime) {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusHours(hoursToPlusToStartDateTime);

        schedule.setStartDateTime(startDateTime);

        return schedule;
    }

    private Reservation createReservation(Schedule schedule, BigDecimal value) {
        return Reservation.builder().schedule(schedule).value(value).build();
    }
}