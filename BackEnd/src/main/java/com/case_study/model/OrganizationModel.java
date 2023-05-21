package com.case_study.model;

import com.case_study.entity.Organization;

public class OrganizationModel {
	private long id;
	private String name;
	private String logo;
	private String currency;
	private String dateFormat;
	private String emailId;
	private boolean thankYouEMail;
	private boolean reminderEMail;
	private boolean isLocked;

	public OrganizationModel(Organization organization) {
		super();
		this.id = organization.getId();
		this.name = organization.getName();
		this.currency = organization.getCurrency();
		this.dateFormat = organization.getDateFormat();
		this.thankYouEMail = organization.isThankYouEMail();
		this.reminderEMail = organization.isReminderEMail();
		this.logo = organization.getLogo();
		this.emailId = organization.getEmailId();
		this.isLocked = organization.isLocked();
	}

	public OrganizationModel() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public boolean isThankYouEMail() {
		return thankYouEMail;
	}

	public void setThankYouEMail(boolean thankYouEMail) {
		this.thankYouEMail = thankYouEMail;
	}

	public boolean isReminderEMail() {
		return reminderEMail;
	}

	public void setReminderEMail(boolean reminderEMail) {
		this.reminderEMail = reminderEMail;
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