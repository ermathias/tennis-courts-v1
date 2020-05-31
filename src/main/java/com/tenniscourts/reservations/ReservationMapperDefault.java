package com.tenniscourts.reservations;

import org.springframework.stereotype.Component;

@Component
public final class ReservationMapperDefault
	implements
	ReservationMapper {

	@Override
	public Reservation map(
		final ReservationDTO source) {
		// TODO Auto-generated method stub		
		throw new UnsupportedOperationException();
	}

	@Override
	public ReservationDTO map(
		final Reservation source) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Reservation map(
		final CreateReservationRequestDTO source) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
