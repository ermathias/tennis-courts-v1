package com.tenniscourts.exceptions;

/**
 * Invalid Date exception.
 */
public class InvalidDateTimeException extends RuntimeException {
	/**
	 * Instantiates a invalid date exception.
	 *
	 * @param msg
	 *            the msg
	 */
	public InvalidDateTimeException(String msg) {
		super(msg);
	}

	private InvalidDateTimeException() {
	}
}
