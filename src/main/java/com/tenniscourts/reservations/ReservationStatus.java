package com.tenniscourts.reservations;


public enum ReservationStatus {
  READY_TO_PLAY(0),
  CANCELLED(1),
  RESCHEDULED(2);
  
  private Integer value;
  
  private ReservationStatus(Integer value){
	  this.value = value;
  }
  
}
