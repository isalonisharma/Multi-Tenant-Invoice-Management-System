package com.caseStudy.exception;

/**
 * Class: InvoiceNotFoundException
 * 
 * Use:  this exception is used to notify user that invoice not found of a particular
 * id
 * 
 * @author saloni.sharma
 */
public class InvoiceNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvoiceNotFoundException(String message) {
		super(message);
	}

	public InvoiceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
