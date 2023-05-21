package com.case_study.exception;

/**
 * Class: ClientOrganizationNotFoundException
 * 
 * Use: this exception is used to notify user that client of a particular id not
 * found for that organization
 * 
 * @author saloni.sharma
 */
public class ClientOrganizationNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public ClientOrganizationNotFoundException(String message) {
		super(message);
	}

	public ClientOrganizationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
