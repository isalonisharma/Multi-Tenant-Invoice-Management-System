package com.caseStudy.dto;

import com.caseStudy.model.InvoiceItemModel;

public class InvoiceItemDTO {

	private long invoiceItemId;
	private long invoiceId;
	private long itemId;
	private long quantity;
	private double itemAmountTotal;

	public InvoiceItemDTO(InvoiceItemModel invoiceItemModel) {
		this.invoiceItemId = invoiceItemModel.getInvoiceItemId();
		this.invoiceId = invoiceItemModel.getInvoiceModel().getInvoiceId();
		this.itemId = invoiceItemModel.getItemModel().getItemId();
		this.quantity = invoiceItemModel.getQuantity();
		this.itemAmountTotal = invoiceItemModel.getItemAmountTotal();
	}

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

	public double getItemAmountTotal() {
		return itemAmountTotal;
	}

	public void setItemAmountTotal(double itemAmountTotal) {
		this.itemAmountTotal = itemAmountTotal;
	}

}
