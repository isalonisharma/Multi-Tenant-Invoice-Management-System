package com.caseStudy.bean;

public class CreateInvoiceOrganizationBean {
	private long invoiceId;

	private long organizationId;

	public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	public CreateInvoiceOrganizationBean(long invoiceId, long organizationId) {
		super();
		this.invoiceId = invoiceId;
		this.organizationId = organizationId;
	}
}