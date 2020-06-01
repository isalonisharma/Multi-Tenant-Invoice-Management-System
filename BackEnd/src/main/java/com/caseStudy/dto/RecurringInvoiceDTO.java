package com.caseStudy.dto;

import java.time.LocalDateTime;

import com.caseStudy.enumeration.PaymentStatus;
import com.caseStudy.model.RecurringInvoiceModel;
import com.caseStudy.utility.DateConversion;

public class RecurringInvoiceDTO {
	private static final String defaultDateFormat = "dd-MMM-yy";

	DateConversion dateConversion = new DateConversion();

	private long recurringInvoiceId;
	private long invoiceId;
	private String dueDate;
	private String renewDate;
	private long recurringPeriod;
	private PaymentStatus paymentStatus;
	
	public RecurringInvoiceDTO(RecurringInvoiceModel recurringInvoiceModel, String dateFormat) {
		this.recurringInvoiceId = recurringInvoiceModel.getRecurringInvoiceId();
		this.invoiceId = recurringInvoiceModel.getInvoiceModel().getInvoiceId();
		this.dueDate = dateConversion.LocalDateTimeToString(recurringInvoiceModel.getDueDate(), dateFormat);
		this.renewDate = dateConversion.LocalDateTimeToString(recurringInvoiceModel.getRenewDate(), dateFormat);
		this.recurringPeriod = recurringInvoiceModel.getRecurringPeriod();
		this.paymentStatus = recurringInvoiceModel.getPaymentStatus();
	}

	public RecurringInvoiceDTO() {
		super();
	}

	public long getRecurringInvoiceId() {
		return recurringInvoiceId;
	}

	public void setRecurringInvoiceId(long recurringInvoiceId) {
		this.recurringInvoiceId = recurringInvoiceId;
	}

	public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dateConversion.LocalDateTimeToString(dueDate, defaultDateFormat);
	}

	public String getRenewDate() {
		return renewDate;
	}

	public void setRenewDate(LocalDateTime renewDate) {
		this.renewDate = dateConversion.LocalDateTimeToString(renewDate, defaultDateFormat);
	}

	public long getRecurringPeriod() {
		return recurringPeriod;
	}

	public void setRecurringPeriod(long recurringPeriod) {
		this.recurringPeriod = recurringPeriod;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
}
