package com.tenniscourts.reservations;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.tenniscourts.guests.Guest;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleDTO;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.tenniscourts.utils.Constants;

public class ReservationMapperImpl implements ReservationMapper {

	@Override
	public Reservation map(ReservationDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservationDTO map(Reservation source) {
		Schedule schedule = source.getSchedule();
		TennisCourt tennisCourt = schedule.getTennisCourt();
		TennisCourtDTO tennisCourtDTO = new TennisCourtDTO(tennisCourt.getId(), tennisCourt.getName(), new ArrayList<>());
		ScheduleDTO scheduleDTO = new ScheduleDTO(schedule.getId(), tennisCourtDTO, tennisCourtDTO.getId(), schedule.getStartDateTime(), schedule.getEndDateTime());
		ReservationDTO reservationDTO = new ReservationDTO(source.getId(), scheduleDTO, source.getReservationStatus().toString(), null, source.getRefundValue(),
				source.getValue(), scheduleDTO.getId(), source.getGuest().getId());
		return reservationDTO;
	}

	/*7. As a Tennis Court Admin, I want to charge a reservation deposit of $10 to the user, charged per court, 
		which is refunded upon completion of their match, so that Users don’t abuse my schedule*/
	
	//10. As a Tennis Court Admin, I want to keep 100% of the reservation deposit if the User does not show up for their reservation
	@Override
	public Reservation map(CreateReservationRequestDTO source, Schedule schedule) {
		Guest guest = new Guest();
		guest.setId(source.getGuestId());
		Reservation reservation = new Reservation(guest, schedule, new BigDecimal(Constants.HOUR_VALUE + Constants.TAX_VALUE), ReservationStatus.READY_TO_PLAY, new BigDecimal(0.0));
		return reservation;
	}

}
