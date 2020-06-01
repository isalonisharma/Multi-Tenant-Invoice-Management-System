package com.caseStudy.bean;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.caseStudy.enumeration.PaymentStatus;

public class UpdateInvoiceBean {
	private long invoiceId;
	private long userId;
	private long clientId;
	private LocalDateTime dueDate;
	private boolean invoiceIsRecurring;
	private boolean invoiceIsLocked;

	private PaymentStatus paymentStatus;
	private LocalDateTime renewDate;
	private long recurringPeriod;

	private Map<Long, Long> invoiceItem = new HashMap<>();

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

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isInvoiceIsRecurring() {
		return invoiceIsRecurring;
	}

	public void setInvoiceIsRecurring(boolean invoiceIsRecurring) {
		this.invoiceIsRecurring = invoiceIsRecurring;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LocalDateTime getRenewDate() {
		return renewDate;
	}

	public void setRenewDate(LocalDateTime renewDate) {
		this.renewDate = renewDate;
	}

	public long getRecurringPeriod() {
		return recurringPeriod;
	}

	public void setRecurringPeriod(long recurringPeriod) {
		this.recurringPeriod = recurringPeriod;
	}

	public boolean isInvoiceIsLocked() {
		return invoiceIsLocked;
	}

	public void setInvoiceIsLocked(boolean invoiceIsLocked) {
		this.invoiceIsLocked = invoiceIsLocked;
	}

	public Map<Long, Long> getInvoiceItem() {
		return invoiceItem;
	}

	public void setInvoiceItem(Map<Long, Long> invoiceItem) {
		this.invoiceItem = invoiceItem;
	}

	@Override
	public String toString() {
		return "UpdateInvoiceRequestBean [invoiceId=" + invoiceId + ", userId=" + userId + ", clientId=" + clientId
				+ ", dueDate=" + dueDate + ", invoiceIsRecurring=" + invoiceIsRecurring + ", invoiceIsLocked="
				+ invoiceIsLocked + ", paymentStatus=" + paymentStatus + ", renewDate=" + renewDate
				+ ", recurringPeriod=" + recurringPeriod + ", invoiceItem=" + invoiceItem + "]";
	}

}
