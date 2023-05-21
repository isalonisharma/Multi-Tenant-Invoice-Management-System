package com.case_study.exception;

/**
 * Class: UserNotFoundException
 * 
 * Use: this exception is used to notify user that user not found of a particular id
 * 
 * @author saloni.sharma
 */
public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
