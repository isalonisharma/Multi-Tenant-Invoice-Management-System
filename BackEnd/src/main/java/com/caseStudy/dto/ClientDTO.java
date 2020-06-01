package com.caseStudy.dto;

import com.caseStudy.model.ClientModel;

public class ClientDTO {
	private long clientId;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String organization;
	private String emailId;

	public ClientDTO() {
		super();
	}

	public ClientDTO(ClientModel model) {
		super();
		this.clientId = model.getClientId();
		this.organization = model.getOrganization();
		this.firstName = model.getFirstName();
		this.lastName = model.getLastName();
		this.mobileNumber = model.getMobileNumber();
		this.emailId = model.getEmailId();
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
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
}