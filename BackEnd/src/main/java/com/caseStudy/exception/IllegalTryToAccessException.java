package com.caseStudy.exception;

/**
 * Class: IllegalTryToAccessException
 * 
 * Use:  this exception is used to notify user that it has not the role to access the
 * specific service
 * 
 * @author saloni.sharma
 */
public class IllegalTryToAccessException extends Exception {
	private static final long serialVersionUID = 1L;

	public IllegalTryToAccessException(String message) {
		super(message);
	}

	public IllegalTryToAccessException(String message, Throwable cause) {
		super(message, cause);
	}
}
