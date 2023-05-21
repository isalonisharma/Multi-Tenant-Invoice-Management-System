package com.case_study.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.case_study.model.OrganizationModel;

@Entity
@Table(name = "organization")
@EntityListeners(AuditingEntityListener.class)
public class Organization implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String logo;
	private String currency;
	private String dateFormat;
	private String emailId;
	private boolean isLocked;
	private boolean thankYouEMail;
	private boolean reminderEMail;

	public Organization() {
		super();
	}

	public Organization(long id, String name, String logo, String currency, String dateFormat, String emailId,
			boolean isLocked, boolean thankYouEMail, boolean reminderEMail) {
		super();
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.currency = currency;
		this.dateFormat = dateFormat;
		this.emailId = emailId;
		this.isLocked = isLocked;
		this.thankYouEMail = thankYouEMail;
		this.reminderEMail = reminderEMail;
	}

	public Organization(OrganizationModel organizationModel) {
		super();
		this.id = organizationModel.getId();
		this.name = organizationModel.getName();
		this.logo = organizationModel.getLogo();
		this.currency = organizationModel.getCurrency();
		this.dateFormat = organizationModel.getDateFormat();
		this.emailId = organizationModel.getEmailId();
		this.thankYouEMail = organizationModel.isThankYouEMail();
		this.reminderEMail = organizationModel.isReminderEMail();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@NotBlank
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	@Column(name = "logo", nullable = false)
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Column(name = "currency", nullable = false)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "date_format", nullable = false)
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	@Column(name = "email_id", nullable = false)
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Column(name = "is_locked", nullable = false)
	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	@Column(name = "thankyou_email", nullable = false)
	public boolean isThankYouEMail() {
		return thankYouEMail;
	}

	public void setThankYouEMail(boolean thankYouEMail) {
		this.thankYouEMail = thankYouEMail;
	}

	@Column(name = "reminder_email", nullable = false)
	public boolean isReminderEMail() {
		return reminderEMail;
	}

	public void setReminderEMail(boolean reminderEMail) {
		this.reminderEMail = reminderEMail;
	}
}