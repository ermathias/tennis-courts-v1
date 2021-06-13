package com.tenniscourts.service;

import com.tenniscourts.entity.Reservation;
import com.tenniscourts.entity.Schedule;
import com.tenniscourts.service.ReservationService;
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
    public void getRefundValueFullRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusDays(2);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(
                reservationService
                        .getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()),
                new BigDecimal(10));
    }

    @Test
    public void getRefundValue75Refund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusHours(13);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(""+
                reservationService
                        .getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), "7.50");
    }

    @Test
    public void getRefundValue50Refund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusHours(8);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(""+
                reservationService
                        .getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()),"5.0");
    }

    @Test
    public void getRefundValue25Refund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusHours(1);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(""+
                reservationService
                        .getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), "2.50");
    }
}