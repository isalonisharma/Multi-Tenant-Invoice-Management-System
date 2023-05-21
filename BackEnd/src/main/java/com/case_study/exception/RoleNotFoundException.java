package com.case_study.exception;

/**
 * Class: RoleNotFoundException
 * 
 * Use:  this exception is used to notify user that role not found of a particular id
 * 
 * @author saloni.sharma
 */
public class RoleNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public RoleNotFoundException(String message) {
		super(message);
	}

	public RoleNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
