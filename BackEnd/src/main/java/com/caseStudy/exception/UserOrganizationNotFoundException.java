package com.caseStudy.exception;

/**
 * Class: UserOrganizationNotFoundException
 * 
 * Use:  this exception is used to notify user that specific user of a particular id
 * not found for that organization
 * 
 * @author saloni.sharma
 */
public class UserOrganizationNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserOrganizationNotFoundException(String message) {
		super(message);
	}

	public UserOrganizationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
