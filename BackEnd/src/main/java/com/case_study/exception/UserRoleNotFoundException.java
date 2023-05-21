package com.case_study.exception;

/**
 * Class: UserRoleNotFoundException
 * 
 * Use:  this exception is used to notify user that role of a particular id not found
 * for that user
 * 
 * @author saloni.sharma
 */
public class UserRoleNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserRoleNotFoundException(String message) {
		super(message);
	}

	public UserRoleNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
