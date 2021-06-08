package com.tenniscourts.reservations;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.tenniscourts.guests.Guest;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleDTO;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtDTO;

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

	@Override
	public Reservation map(CreateReservationRequestDTO source, Schedule schedule) {
		Guest guest = new Guest();
		guest.setId(source.getGuestId());
		Reservation reservation = new Reservation(guest, schedule, new BigDecimal(60.0), ReservationStatus.READY_TO_PLAY, new BigDecimal(0.0));
		return reservation;
	}

}
