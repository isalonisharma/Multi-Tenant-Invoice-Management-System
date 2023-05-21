package com.case_study.entity;

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

import com.case_study.enumeration.PaymentStatus;

@Entity
@Table(name = "audit")
@EntityListeners(AuditingEntityListener.class)
public class Audit implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private Invoice invoice;
	private LocalDateTime entryDate;
	private PaymentStatus paymentStatus;
	private String description;

	public Audit() {
		super();
	}

	public Audit(Invoice invoice, LocalDateTime entryDate, PaymentStatus paymentStatus, String description) {
		super();
		this.invoice = invoice;
		this.entryDate = entryDate;
		this.paymentStatus = paymentStatus;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id")
	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoiceModel) {
		this.invoice = invoiceModel;
	}

	@Column(name = "entry_date", nullable = false)
	public LocalDateTime getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(LocalDateTime entryDate) {
		this.entryDate = entryDate;
	}

	@Column(name = "payment_status", nullable = false)
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Column(name = "description", nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}