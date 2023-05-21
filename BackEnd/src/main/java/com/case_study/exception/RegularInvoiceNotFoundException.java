package com.case_study.exception;

/**
 * Class: RegularInvoiceNotFoundException
 * 
 * Use:  this exception is used to notify user that regular invoice not found of a
 * particular id
 * 
 * @author saloni.sharma
 */
public class RegularInvoiceNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public RegularInvoiceNotFoundException(String message) {
		super(message);
	}

	public RegularInvoiceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
