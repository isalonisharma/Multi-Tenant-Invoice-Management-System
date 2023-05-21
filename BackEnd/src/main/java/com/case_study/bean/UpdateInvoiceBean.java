package com.case_study.bean;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.case_study.enumeration.PaymentStatus;

public class UpdateInvoiceBean {
	private long id;
	private long userId;
	private long clientId;
	private LocalDateTime dueDate;
	private boolean isRecurring;
	private boolean isLocked;
	private PaymentStatus paymentStatus;
	private LocalDateTime renewDate;
	private long recurringPeriod;
	private Map<Long, Long> items = new HashMap<>();

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

	public boolean isRecurring() {
		return isRecurring;
	}

	public void setRecurring(boolean isRecurring) {
		this.isRecurring = isRecurring;
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

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public Map<Long, Long> getItems() {
		return items;
	}

	public void setItems(Map<Long, Long> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "UpdateInvoiceRequestBean [id=" + id + ", userId=" + userId + ", clientId=" + clientId
				+ ", dueDate=" + dueDate + ", isRecurring=" + isRecurring + ", isLocked="
				+ isLocked + ", paymentStatus=" + paymentStatus + ", renewDate=" + renewDate
				+ ", recurringPeriod=" + recurringPeriod + ", items=" + items + "]";
	}
}