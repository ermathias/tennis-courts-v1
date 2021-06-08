package com.tenniscourts.exceptions;

/**
 * The type Business exception.
 */
public class DateParseException extends RuntimeException {
  /**
   * Instantiates a new Business exception.
   *
   * @param msg the msg
   */
  public DateParseException(String msg){
        super(msg);
    }

    private DateParseException(){}
}
