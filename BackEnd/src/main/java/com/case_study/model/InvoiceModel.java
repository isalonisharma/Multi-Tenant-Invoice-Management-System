package com.case_study.model;

import java.time.LocalDateTime;

import com.case_study.entity.Invoice;
import com.case_study.utility.DateConversion;

public class InvoiceModel {
	private static final String DEFAULT_DATE_FORMAT = "dd-MMM-yy";
	private long id;
	private long userId;
	private long clientid;
	private String formattedDatePlaced;
	private boolean isRecurring;
	private double amount;
	private boolean isLocked;

	public InvoiceModel() {
		super();
	}

	public InvoiceModel(long id, long userId, long clientid, LocalDateTime datePlaced, boolean invoiceIsRecurring,
			double amount, String dateFormat) {
		super();
		this.id = id;
		this.userId = userId;
		this.clientid = clientid;
		this.formattedDatePlaced = DateConversion.convertLocalDateTime(datePlaced, dateFormat);
		this.isRecurring = invoiceIsRecurring;
		this.amount = amount;
	}

	public InvoiceModel(Invoice invoice, String dateFormat) {
		this.id = invoice.getId();
		this.userId = invoice.getUser().getId();
		this.clientid = invoice.getClient().getId();
		this.formattedDatePlaced = DateConversion.convertLocalDateTime(invoice.getDatePlaced(), dateFormat);
		this.amount = invoice.getAmount();
		this.isRecurring = invoice.isRecurring();
		this.isLocked = invoice.isLocked();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getClientid() {
		return clientid;
	}

	public void setClientid(long clientid) {
		this.clientid = clientid;
	}

	public String getFormattedDatePlaced() {
		return formattedDatePlaced;
	}

	public void setFormattedDatePlaced(LocalDateTime datePlaced) {
		this.formattedDatePlaced = DateConversion.convertLocalDateTime(datePlaced, DEFAULT_DATE_FORMAT);
	}

	public boolean isRecurring() {
		return isRecurring;
	}

	public void setRecurring(boolean isRecurring) {
		this.isRecurring = isRecurring;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public void setFormattedDatePlaced(String formattedDatePlaced) {
		this.formattedDatePlaced = formattedDatePlaced;
	}
}