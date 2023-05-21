package com.case_study.exception;

/**
 * Class: ItemOrganizationNotFoundException
 * 
 * Use: this exception is used to notify user that item of a particular id not found
 * for that organization
 * 
 * @author saloni.sharma
 */
public class ItemOrganizationNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public ItemOrganizationNotFoundException(String message) {
		super(message);
	}

	public ItemOrganizationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
