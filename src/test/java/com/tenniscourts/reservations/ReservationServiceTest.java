/*package com.tenniscourts.reservations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.tenniscourts.schedules.Schedule;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@ContextConfiguration(classes = ReservationService.class)
public class ReservationServiceTest {

	@Autowired
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
}*/