package com.caseStudy.exception;

/**
 * Class: OrganizationNotFoundException
 * 
 * Use: this exception is used to notify user that organization not found of a
 * particular id
 * 
 * @author saloni.sharma
 */
public class OrganizationNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public OrganizationNotFoundException(String message) {
		super(message);
	}

	public OrganizationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}