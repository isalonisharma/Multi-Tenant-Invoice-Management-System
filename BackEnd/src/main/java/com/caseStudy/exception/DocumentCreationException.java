package com.caseStudy.exception;

/**
 * Class: DocumentCreationException
 * 
 * Use: this exception is used to notify the user that document creation or access while fetching it was failed
 * 
 * @author saloni.sharma
 */
public class DocumentCreationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DocumentCreationException(String message) {
		super(message);
	}

	public DocumentCreationException(String message, Throwable cause) {
		super(message, cause);
	}
}