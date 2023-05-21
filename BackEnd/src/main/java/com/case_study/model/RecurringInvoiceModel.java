package com.case_study.model;

import java.time.LocalDateTime;

import com.case_study.entity.RecurringInvoice;
import com.case_study.enumeration.PaymentStatus;
import com.case_study.utility.CommonConstants;
import com.case_study.utility.DateConversion;

public class RecurringInvoiceModel {
	private long id;
	private long invoiceId;
	private String formattedDueDate;
	private String formattedRenewDate;
	private LocalDateTime dueDate;
	private LocalDateTime renewDate;
	private long period;
	private PaymentStatus paymentStatus;

	public RecurringInvoiceModel(RecurringInvoice recurringInvoice, String dateFormat) {
		this.id = recurringInvoice.getId();
		this.invoiceId = recurringInvoice.getInvoice().getId();
		this.formattedDueDate = DateConversion.convertLocalDateTime(recurringInvoice.getDueDate(), dateFormat);
		this.formattedRenewDate = DateConversion.convertLocalDateTime(recurringInvoice.getRenewDate(), dateFormat);
		this.period = recurringInvoice.getPeriod();
		this.paymentStatus = recurringInvoice.getPaymentStatus();
		this.dueDate = recurringInvoice.getDueDate();
		this.renewDate = recurringInvoice.getRenewDate();
	}

	public RecurringInvoiceModel() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public void setFormattedDueDate(LocalDateTime dueDate) {
		this.formattedDueDate = DateConversion.convertLocalDateTime(dueDate, CommonConstants.DEFAULT_DATE_FORMAT);
	}

	public void setFormattedRenewDate(LocalDateTime renewDate) {
		this.formattedRenewDate = DateConversion.convertLocalDateTime(renewDate, CommonConstants.DEFAULT_DATE_FORMAT);
	}

	public String getFormattedDueDate() {
		return formattedDueDate;
	}

	public void setFormattedDueDate(String formattedDueDate) {
		this.formattedDueDate = formattedDueDate;
	}

	public String getFormattedRenewDate() {
		return formattedRenewDate;
	}

	public void setFormattedRenewDate(String formattedRenewDate) {
		this.formattedRenewDate = formattedRenewDate;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDateTime getRenewDate() {
		return renewDate;
	}

	public void setRenewDate(LocalDateTime renewDate) {
		this.renewDate = renewDate;
	}
}