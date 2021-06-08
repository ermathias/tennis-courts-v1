package com.tenniscourts.reservations;

import java.math.BigDecimal;

import com.tenniscourts.guests.Guest;
import com.tenniscourts.schedules.Schedule;

public class ReservationMapperImpl implements ReservationMapper {

	@Override
	public Reservation map(ReservationDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservationDTO map(Reservation source) {
		ReservationDTO reservationDTO = new ReservationDTO();
		reservationDTO.setId(source.getId());
		return reservationDTO;
	}

	@Override
	public Reservation map(CreateReservationRequestDTO source) {
		Guest guest = new Guest();
		guest.setId(source.getGuestId());
		Schedule schedule = new Schedule();
		schedule.setId(source.getScheduleId());
		Reservation reservation = new Reservation(guest, schedule, new BigDecimal(0.0), ReservationStatus.READY_TO_PLAY, new BigDecimal(10.0));
		return reservation;
	}

}
