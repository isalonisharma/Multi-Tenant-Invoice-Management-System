package com.caseStudy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "organization")
@EntityListeners(AuditingEntityListener.class)
public class OrganizationModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long organizationId;
	private String organizationName;
	private String organizationLogo;
	private String organizationCurrency;
	private String organizationDate;
	private String organizationEmailId;
	private boolean organizationIsLocked;
	private boolean organizationThankYouMail;
	private boolean organizationReminderMail;

	public OrganizationModel() {
		super();
	}

	public OrganizationModel(long organizationId, String organizationName, String organizationLogo,
			String organizationCurrency, String organizationDate, String organizationEmailId,
			boolean organizationIsLocked, boolean organizationThankYouMail, boolean organizationReminderMail) {
		super();
		this.organizationId = organizationId;
		this.organizationName = organizationName;
		this.organizationLogo = organizationLogo;
		this.organizationCurrency = organizationCurrency;
		this.organizationDate = organizationDate;
		this.organizationEmailId = organizationEmailId;
		this.organizationIsLocked = organizationIsLocked;
		this.organizationThankYouMail = organizationThankYouMail;
		this.organizationReminderMail = organizationReminderMail;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	@NotBlank
	@Column(name = "organization_name", nullable = false)
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	@NotBlank
	@Column(name = "organization_logo", nullable = false)
	public String getOrganizationLogo() {
		return organizationLogo;
	}

	public void setOrganizationLogo(String organizationLogo) {
		this.organizationLogo = organizationLogo;
	}

	@Column(name = "organization_currency", nullable = false)
	public String getOrganizationCurrency() {
		return organizationCurrency;
	}

	public void setOrganizationCurrency(String organizationCurrency) {
		this.organizationCurrency = organizationCurrency;
	}

	@Column(name = "organization_date", nullable = false)
	public String getOrganizationDate() {
		return organizationDate;
	}

	public void setOrganizationDate(String organizationDate) {
		this.organizationDate = organizationDate;
	}

	@Column(name = "organization_email_id", nullable = false)
	public String getOrganizationEmailId() {
		return organizationEmailId;
	}

	public void setOrganizationEmailId(String organizationEmailId) {
		this.organizationEmailId = organizationEmailId;
	}

	@Column(name = "organization_is_locked", nullable = false)
	public boolean isOrganizationIsLocked() {
		return organizationIsLocked;
	}

	public void setOrganizationIsLocked(boolean organizationIsLocked) {
		this.organizationIsLocked = organizationIsLocked;
	}

	@Column(name = "organization_thankyou_email", nullable = false)
	public boolean isOrganizationThankYouMail() {
		return organizationThankYouMail;
	}

	public void setOrganizationThankYouMail(boolean organizationThankYouMail) {
		this.organizationThankYouMail = organizationThankYouMail;
	}

	@Column(name = "organization_reminder_email", nullable = false)
	public boolean isOrganizationReminderMail() {
		return organizationReminderMail;
	}

	public void setOrganizationReminderMail(boolean organizationReminderMail) {
		this.organizationReminderMail = organizationReminderMail;
	}

}
