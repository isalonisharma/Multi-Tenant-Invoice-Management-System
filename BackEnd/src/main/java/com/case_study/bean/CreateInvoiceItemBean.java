package com.case_study.bean;

public class CreateInvoiceItemBean {
	private long invoiceItemId;
	private long invoiceId;
	private long itemId;
	private long quantity;

	public long getInvoiceItemId() {
		return invoiceItemId;
	}

	public void setInvoiceItemId(long invoiceItemId) {
		this.invoiceItemId = invoiceItemId;
	}

	public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public CreateInvoiceItemBean(long invoiceItemId, long invoiceId, long itemId, long quantity) {
		super();
		this.invoiceItemId = invoiceItemId;
		this.invoiceId = invoiceId;
		this.itemId = itemId;
		this.quantity = quantity;
	}
}