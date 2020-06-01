package com.caseStudy.exception;

/**
 * Class: InvoiceItemNotFoundException
 * 
 * Use:  this exception is used to notify user that the invoice does not contain item
 * of a particular id
 * 
 * @author saloni.sharma
 */
public class InvoiceItemNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvoiceItemNotFoundException(String message) {
		super(message);
	}

	public InvoiceItemNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
