package com.caseStudy.exception;

/**
 * Class: InvoiceOrganizationNotFoundException
 * 
 * Use:  this exception is used to notify user that invoice of a particular id not
 * found for that organization
 * 
 * @author saloni.sharma
 */
public class InvoiceOrganizationNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvoiceOrganizationNotFoundException(String message) {
		super(message);
	}

	public InvoiceOrganizationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
