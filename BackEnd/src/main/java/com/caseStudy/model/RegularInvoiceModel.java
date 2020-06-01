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
@Table(name = "regular_invoice")
@EntityListeners(AuditingEntityListener.class)
public class RegularInvoiceModel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long regularInvoiceId;
	private InvoiceModel invoiceModel;
	private LocalDateTime dueDate;
	private PaymentStatus paymentStatus;

	public RegularInvoiceModel() {
	}

	public RegularInvoiceModel(InvoiceModel invoiceModel, LocalDateTime dueDate, PaymentStatus paymentStatus) {
		super();
		this.invoiceModel = invoiceModel;
		this.dueDate = dueDate;
		this.paymentStatus = paymentStatus;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getRegularInvoiceId() {
		return regularInvoiceId;
	}

	public void setRegularInvoiceId(long regularInvoiceId) {
		this.regularInvoiceId = regularInvoiceId;
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

	@Column(name = "payment_status", nullable = false)
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
}