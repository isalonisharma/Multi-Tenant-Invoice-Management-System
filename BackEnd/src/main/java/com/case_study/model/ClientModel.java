package com.case_study.model;

import com.case_study.entity.Client;

public class ClientModel {
	private long id;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String organization;
	private String emailId;
	private boolean isLocked;

	public ClientModel() {
		super();
	}

	public ClientModel(Client client) {
		super();
		this.id = client.getId();
		this.organization = client.getOrganization();
		this.firstName = client.getFirstName();
		this.lastName = client.getLastName();
		this.mobileNumber = client.getMobileNumber();
		this.emailId = client.getEmailId();
		this.isLocked = client.isLocked();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
}