package com.caseStudy.dto;

import java.time.LocalDateTime;

import com.caseStudy.enumeration.PaymentStatus;
import com.caseStudy.model.RegularInvoiceModel;
import com.caseStudy.utility.DateConversion;

public class RegularInvoiceDTO {
	private static final String defaultDateFormat = "dd-MMM-yy";

	DateConversion dateConversion = new DateConversion();
	
	private long regularInvoiceId;
	private long invoiceId;
	private String dueDate;
	private PaymentStatus paymentStatus;

	public RegularInvoiceDTO(RegularInvoiceModel regularInvoiceModel, String dateFormat) {
		this.regularInvoiceId = regularInvoiceModel.getRegularInvoiceId();
		this.invoiceId = regularInvoiceModel.getInvoiceModel().getInvoiceId();
		this.dueDate = dateConversion.LocalDateTimeToString(regularInvoiceModel.getDueDate(), dateFormat);
		this.paymentStatus = regularInvoiceModel.getPaymentStatus();
	}

	public RegularInvoiceDTO() {
		super();
	}

	public long getRegularInvoiceId() {
		return regularInvoiceId;
	}

	public void setRegularInvoiceId(long regularInvoiceId) {
		this.regularInvoiceId = regularInvoiceId;
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

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

}
