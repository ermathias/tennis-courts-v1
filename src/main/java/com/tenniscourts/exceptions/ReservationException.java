package com.tenniscourts.exceptions;

/**
 * The type Business exception.
 */
public class ReservationException extends RuntimeException {
  /**
   * Instantiates a new Business exception.
   *
   * @param msg the msg
   */
  public ReservationException(String msg){
        super(msg);
    }

    private ReservationException(){}
}
