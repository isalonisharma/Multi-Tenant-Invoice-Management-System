package com.case_study.exception;

/**
 * Class: FileStorageException
 * 
 * Use: this exception is used to notify the user that file storage exception
 * occurred for a particular file
 * 
 * @author saloni.sharma
 */
public class FileStorageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileStorageException(String message) {
		super(message);
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
