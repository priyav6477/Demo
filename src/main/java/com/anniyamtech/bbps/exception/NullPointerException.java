package com.anniyamtech.bbps.exception;


public class NullPointerException extends RuntimeException {


	/** The message. */
	private String message;

	/**
	 * Instantiates a new rest exception.
	 */
	public NullPointerException() {

	}

	
	public String getMessage() {
		return message;
	}
}
