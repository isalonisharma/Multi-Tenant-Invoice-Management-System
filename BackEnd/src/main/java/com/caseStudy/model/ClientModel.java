package com.caseStudy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@Entity
@Table(name = "client")
@EntityListeners(AuditingEntityListener.class)
public class ClientModel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@JacksonXmlProperty(localName = "id", isAttribute = true)
	private long clientId;

	@JacksonXmlProperty(localName = "firstName")
	private String firstName;

	@JacksonXmlProperty(localName = "lastName")
	private String lastName;

	@JacksonXmlProperty(localName = "mobileNumber")
	private String mobileNumber;

	@JacksonXmlProperty(localName = "organization")
	private String organization;

	@JacksonXmlProperty(localName = "emailId")
	private String emailId;

	@JacksonXmlProperty(localName = "clientIsLocked")
	private boolean clientIsLocked;

	public ClientModel() {

	}

	public ClientModel(String firstName, String lastName, String mobileNumber, String organization, String emailId,
			boolean clientIsLocked) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.organization = organization;
		this.emailId = emailId;
		this.clientIsLocked = clientIsLocked;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	@Column(name = "first_name", nullable = false)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", nullable = false)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "mobile_number", nullable = false)
	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Column(name = "organization", nullable = false)
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	@Column(name = "email_id", nullable = false)
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Column(name = "client_is_locked", nullable = false)
	public boolean isClientIsLocked() {
		return clientIsLocked;
	}

	public void setClientIsLocked(boolean clientIsLocked) {
		this.clientIsLocked = clientIsLocked;
	}

	@Override
	public String toString() {
		return "ClientModel [clientId=" + clientId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", mobileNumber=" + mobileNumber + ", organization=" + organization + ", emailId=" + emailId
				+ ", clientIsLocked=" + clientIsLocked + "]";
	}
}