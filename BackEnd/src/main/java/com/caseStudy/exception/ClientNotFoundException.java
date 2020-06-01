package com.caseStudy.exception;

/**
 * Class: ClientNotFoundException
 * 
 * Use: this exception is used to notify the user that client not found of a
 * particular id
 * 
 * @author saloni.sharma
 */
public class ClientNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public ClientNotFoundException(String message) {
		super(message);
	}

	public ClientNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
