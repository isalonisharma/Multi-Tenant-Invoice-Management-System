package com.caseStudy.dto;

import java.time.LocalDateTime;

import com.caseStudy.model.InvoiceModel;
import com.caseStudy.utility.DateConversion;

public class InvoiceDTO {
	private static final String defaultDateFormat = "dd-MMM-yy";

	DateConversion dateConversion = new DateConversion();

	private long invoiceId;
	private long userId;
	private long clientid;
	private String datePlaced;
	private boolean invoiceIsRecurring;
	private double amount;

	public InvoiceDTO() {
		super();
	}

	public InvoiceDTO(long invoiceId, long userId, long clientid, LocalDateTime datePlaced, boolean invoiceIsRecurring,
			double amount, String dateFormat) {
		super();
		this.invoiceId = invoiceId;
		this.userId = userId;
		this.clientid = clientid;
		this.datePlaced = dateConversion.LocalDateTimeToString(datePlaced, dateFormat);
		this.invoiceIsRecurring = invoiceIsRecurring;
		this.amount = amount;
	}

	public InvoiceDTO(InvoiceModel invoiceModel, String dateFormat) {
		this.invoiceId = invoiceModel.getInvoiceId();
		this.userId = invoiceModel.getUserModel().getUserId();
		this.clientid = invoiceModel.getClientModel().getClientId();
		this.datePlaced = dateConversion.LocalDateTimeToString(invoiceModel.getDatePlaced(), dateFormat);
		this.amount = invoiceModel.getAmount();
		this.invoiceIsRecurring = invoiceModel.isInvoiceIsRecurring();
	}

	public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
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

	public String getDatePlaced() {
		return datePlaced;
	}

	public void setDatePlaced(LocalDateTime datePlaced) {
		this.datePlaced = dateConversion.LocalDateTimeToString(datePlaced, defaultDateFormat);
	}

	public boolean isInvoiceIsRecurring() {
		return invoiceIsRecurring;
	}

	public void setInvoiceIsRecurring(boolean invoiceIsRecurring) {
		this.invoiceIsRecurring = invoiceIsRecurring;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}