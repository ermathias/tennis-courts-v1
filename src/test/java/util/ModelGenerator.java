package util;

import com.tenniscourts.guests.Guest;
import com.tenniscourts.reservations.CreateReservationRequestDTO;
import com.tenniscourts.reservations.Reservation;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.tenniscourts.TennisCourt;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ModelGenerator {

    public Guest createGuestWithId1() {
        Guest guest = new Guest();
        guest.setId(1L);
        return guest;
    }

    public Schedule createScheduleWithId1() {
        Schedule schedule = new Schedule();
        schedule.setId(1L);
        return schedule;
    }

    public TennisCourt createTennisCourtWithId1() {
        TennisCourt tennisCourt = new TennisCourt();
        tennisCourt.setId(1L);
        return tennisCourt;
    }

    public Reservation createReservationToSaveWithGuestIdAndScheduleId1() {
        Reservation reservation = new Reservation();
        reservation.setGuest(createGuestWithId1());
        reservation.setSchedule(createScheduleWithId1());
        reservation.setValue(BigDecimal.valueOf(10));
        return reservation;
    }

    public CreateReservationRequestDTO createCreateReservationDTOWithGuestIdAndScheduleId1() {
        return new CreateReservationRequestDTO(1L, 1L, BigDecimal.valueOf(10));
    }

    public Schedule createScheduleToBeSaved() {
        Schedule schedule = new Schedule();
        schedule.setTennisCourt(createTennisCourtWithId1());
        schedule.setStartDateTime(LocalDateTime.now().plusDays(2));
        schedule.setEndDateTime(schedule.getStartDateTime().plusHours(1));
        return schedule;
    }

}
