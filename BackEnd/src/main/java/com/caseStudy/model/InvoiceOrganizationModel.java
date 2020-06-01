package com.caseStudy.model;

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

@Entity
@Table(name = "invoice_organization")
@EntityListeners(AuditingEntityListener.class)
public class InvoiceOrganizationModel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long invoiceOrganizationId;
	private InvoiceModel invoiceModel;
	private OrganizationModel organizationModel;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getInvoiceOrganizationId() {
		return invoiceOrganizationId;
	}

	public void setInvoiceOrganizationId(long invoiceOrganizationId) {
		this.invoiceOrganizationId = invoiceOrganizationId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "invoice_id", nullable = false)
	public InvoiceModel getInvoiceModel() {
		return invoiceModel;
	}

	public void setInvoiceModel(InvoiceModel invoiceModel) {
		this.invoiceModel = invoiceModel;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "organization_id", nullable = false)
	public OrganizationModel getOrganizationModel() {
		return organizationModel;
	}

	public void setOrganizationModel(OrganizationModel organizationModel) {
		this.organizationModel = organizationModel;
	}
}