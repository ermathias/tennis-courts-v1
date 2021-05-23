package com.tenniscourts.exceptions;

public class BookingSlotNotAvailableException extends RuntimeException {
	  /**
	   * Instantiates a new Already exists entity exception.
	   *
	   * @param msg the msg
	   */
	  public BookingSlotNotAvailableException(String msg){
	        super(msg);
	    }

	    private BookingSlotNotAvailableException(){}
	}
