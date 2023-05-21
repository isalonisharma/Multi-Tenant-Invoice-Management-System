package com.case_study.exception;

/**
 * Class: ItemNotFoundException
 * 
 * Use: this exception is used to notify user that item not found of a particular id
 * 
 * @author saloni.sharma
 */
public class ItemNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public ItemNotFoundException(String message) {
		super(message);
	}

	public ItemNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
