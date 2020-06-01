package com.caseStudy.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.caseStudy.enumeration.PaymentStatus;

@Entity
@Table(name = "recurring_invoice")
@EntityListeners(AuditingEntityListener.class)
public class RecurringInvoiceModel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long recurringInvoiceId;
	private InvoiceModel invoiceModel;
	private LocalDateTime dueDate;
	private LocalDateTime renewDate;
	private long recurringPeriod;
	private PaymentStatus paymentStatus;

	public RecurringInvoiceModel() {
	}

	public RecurringInvoiceModel(InvoiceModel invoiceModel, LocalDateTime dueDate, LocalDateTime renewDate,
			long recurringPeriod, PaymentStatus paymentStatus) {
		super();
		this.invoiceModel = invoiceModel;
		this.dueDate = dueDate;
		this.renewDate = renewDate;
		this.recurringPeriod = recurringPeriod;
		this.paymentStatus = paymentStatus;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getRecurringInvoiceId() {
		return recurringInvoiceId;
	}

	public void setRecurringInvoiceId(long recurringInvoiceId) {
		this.recurringInvoiceId = recurringInvoiceId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "invoice_id")
	public InvoiceModel getInvoiceModel() {
		return invoiceModel;
	}

	public void setInvoiceModel(InvoiceModel invoiceModel) {
		this.invoiceModel = invoiceModel;
	}

	@Column(name = "due_date", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	@Column(name = "renew_date", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
	public LocalDateTime getRenewDate() {
		return renewDate;
	}

	public void setRenewDate(LocalDateTime renewDate) {
		this.renewDate = renewDate;
	}

	@Column(name = "recurring_period", nullable = false)
	public long getRecurringPeriod() {
		return recurringPeriod;
	}

	public void setRecurringPeriod(long recurringPeriod) {
		this.recurringPeriod = recurringPeriod;
	}

	@Column(name = "payment_status", nullable = false)
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
}