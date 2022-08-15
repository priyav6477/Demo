package com.anniyamtech.bbps.exception;


public class ResponseFailureException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The message. */
	private String message;
		
	public ResponseFailureException() {

	}

	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	

}
