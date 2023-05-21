package com.case_study.model;

import java.time.LocalDateTime;

import com.case_study.entity.RegularInvoice;
import com.case_study.enumeration.PaymentStatus;
import com.case_study.utility.CommonConstants;
import com.case_study.utility.DateConversion;

public class RegularInvoiceModel {
	private long id;
	private long invoiceId;
	private String formattedDueDate;
	private LocalDateTime dueDate;
	private PaymentStatus paymentStatus;

	public RegularInvoiceModel(RegularInvoice regularInvoice, String dateFormat) {
		this.id = regularInvoice.getId();
		this.invoiceId = regularInvoice.getInvoice().getId();
		this.dueDate = regularInvoice.getDueDate();
		this.formattedDueDate = DateConversion.convertLocalDateTime(regularInvoice.getDueDate(), dateFormat);
		this.paymentStatus = regularInvoice.getPaymentStatus();
	}

	public RegularInvoiceModel() {
		super();
	}

	public long getRegularId() {
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

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public String getFormattedDueDate() {
		return formattedDueDate;
	}

	public void setFormattedDueDate(LocalDateTime dueDate) {
		this.formattedDueDate = DateConversion.convertLocalDateTime(dueDate, CommonConstants.DEFAULT_DATE_FORMAT);
	}

	public void setFormattedDueDate(String formattedDueDate) {
		this.formattedDueDate = formattedDueDate;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
}