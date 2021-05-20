package com.tenniscourts.reservations;

import java.util.Arrays;
import java.util.List;

public enum ReservationStatus {
	
	READY_TO_PLAY,
	CANCELLED,
	RESCHEDULED,
	FREE;
  
	public static List<ReservationStatus> getStatusAvailableToScheduling() {
		return Arrays.asList(CANCELLED, FREE, RESCHEDULED);
	}
	
	public static List<ReservationStatus> getStatusUnavailableToScheduling() {
		return Arrays.asList(READY_TO_PLAY);
	}
}
