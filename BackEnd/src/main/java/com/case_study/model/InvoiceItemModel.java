package com.case_study.model;

import com.case_study.entity.InvoiceItem;

public class InvoiceItemModel {
	private long id;
	private long invoiceId;
	private long itemId;
	private long quantity;
	private double totalAmount;

	public InvoiceItemModel(InvoiceItem invoiceItem) {
		this.id = invoiceItem.getId();
		this.invoiceId = invoiceItem.getInvoice().getId();
		this.itemId = invoiceItem.getItem().getId();
		this.quantity = invoiceItem.getQuantity();
		this.totalAmount = invoiceItem.getTotalAmount();
	}

	public long getInvoiceItemId() {
		return id;
	}

	public void setInvoiceItemId(long id) {
		this.id = id;
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

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double itemAmountTotal) {
		this.totalAmount = itemAmountTotal;
	}
}