package com.tenniscourts.reservations;

import com.tenniscourts.schedules.Schedule;
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

import static org.mockito.Mockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ReservationService.class)
public class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Mock
    ReservationRepository reservationRepositoryMock;

    @Test
    public void getRefundValueFullRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusDays(2);

        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build()), new BigDecimal(10));
    }

    @Test
    public void testReschedule_whenSameTimeSlot_exceptionIsThrownAndReservationIsNotChanged() {
        long scheduleId = 2L;
        long reservationId = 1L;
        Schedule oldSchedule = mockSchedule(scheduleId);
        mockReservation(reservationId, oldSchedule);

        try {
            reservationService.rescheduleReservation(reservationId, scheduleId);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Cannot reschedule to the same slot.", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        verify(reservationRepositoryMock, times(0)).save(any());

    }

    private Reservation mockReservation(long reservationId, Schedule oldSchedule) {
        Reservation reservation = Mockito.mock(Reservation.class);
        when(reservation.getSchedule()).thenReturn(oldSchedule);
        when(reservationRepositoryMock.findById(reservationId)).thenReturn(Optional.of(reservation));
        return reservation;
    }

    private Schedule mockSchedule(long scheduleId) {
        Schedule oldSchedule = mock(Schedule.class);
        when(oldSchedule.getId()).thenReturn(scheduleId);
        return oldSchedule;
    }


}