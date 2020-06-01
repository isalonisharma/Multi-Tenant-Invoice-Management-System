package com.caseStudy.dto;

import com.caseStudy.model.OrganizationModel;

public class OrganizationDTO {
	private long organizationId;
	private String organizationName;
	private String organizationLogo;
	private String organizationCurrency;
	private String organizationDate;
	private boolean organizationThankYouMail;
	private boolean organizationReminderMail;

	public OrganizationDTO(OrganizationModel model) {
		super();
		this.organizationId = model.getOrganizationId();
		this.organizationName = model.getOrganizationName();
		this.organizationCurrency = model.getOrganizationCurrency();
		this.organizationDate = model.getOrganizationDate();
		this.organizationThankYouMail = model.isOrganizationThankYouMail();
		this.organizationReminderMail = model.isOrganizationReminderMail();
		this.organizationLogo = model.getOrganizationLogo();
	}

	public OrganizationDTO() {
		super();
	}

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationLogo() {
		return organizationLogo;
	}

	public void setOrganizationLogo(String organizationLogo) {
		this.organizationLogo = organizationLogo;
	}

	public String getOrganizationCurrency() {
		return organizationCurrency;
	}

	public void setOrganizationCurrency(String organizationCurrency) {
		this.organizationCurrency = organizationCurrency;
	}

	public String getOrganizationDate() {
		return organizationDate;
	}

	public void setOrganizationDate(String organizationDate) {
		this.organizationDate = organizationDate;
	}

	public boolean isOrganizationThankYouMail() {
		return organizationThankYouMail;
	}

	public void setOrganizationThankYouMail(boolean organizationThankYouMail) {
		this.organizationThankYouMail = organizationThankYouMail;
	}

	public boolean isOrganizationReminderMail() {
		return organizationReminderMail;
	}

	public void setOrganizationReminderMail(boolean organizationReminderMail) {
		this.organizationReminderMail = organizationReminderMail;
	}
}