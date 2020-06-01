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

import com.caseStudy.enumeration.PaymentStatus;

@Entity
@Table(name = "audit_table")
@EntityListeners(AuditingEntityListener.class)
public class AuditModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long auditTableId;
	private InvoiceModel invoiceModel;
	private LocalDateTime entryDate;
	private PaymentStatus paymentStatus;
	private String description;

	public AuditModel() {
	}

	public AuditModel(InvoiceModel invoiceModel, LocalDateTime entryDate, PaymentStatus paymentStatus,
			String description) {
		super();
		this.invoiceModel = invoiceModel;
		this.entryDate = entryDate;
		this.paymentStatus = paymentStatus;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getAuditTableId() {
		return auditTableId;
	}

	public void setAuditTableId(long auditTableId) {
		this.auditTableId = auditTableId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id")
	public InvoiceModel getInvoiceModel() {
		return invoiceModel;
	}

	public void setInvoiceModel(InvoiceModel invoiceModel) {
		this.invoiceModel = invoiceModel;
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
