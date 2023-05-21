package com.case_study.model;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class ErrorModel implements Serializable {
	private LocalDateTime timestamp;
	private int status;
	private String error;

	public ErrorModel(LocalDateTime timestamp, String error) {
		super();
		this.timestamp = timestamp;
		this.error = error;
	}

	public ErrorModel(LocalDateTime timestamp, int status, String error) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
	}

	public ErrorModel() {
		super();
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "ErrorModel [timestamp=" + timestamp + ", status=" + status + ", error=" + error + "]";
	}
}