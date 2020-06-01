package com.caseStudy.exception;

/**
 * Class: RecurringInvoiceNotFoundException
 * 
 * Use:  this exception is used to notify user that recurring invoice not found of a
 * particular id
 * 
 * @author saloni.sharma
 */
public class RecurringInvoiceNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public RecurringInvoiceNotFoundException(String message) {
		super(message);
	}

	public RecurringInvoiceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
