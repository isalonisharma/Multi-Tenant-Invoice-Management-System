package com.caseStudy.model;

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

@Entity
@Table(name = "invoice_item")
@EntityListeners(AuditingEntityListener.class)
public class InvoiceItemModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long invoiceItemId;
	private InvoiceModel invoiceModel;
	private ItemModel itemModel;
	private long quantity;
	private double itemAmountTotal;

	public InvoiceItemModel() {
	}

	public InvoiceItemModel(InvoiceModel invoiceModel, ItemModel itemModel, long quantity, double itemAmountTotal) {
		super();
		this.invoiceModel = invoiceModel;
		this.itemModel = itemModel;
		this.quantity = quantity;
		this.itemAmountTotal = itemAmountTotal;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getInvoiceItemId() {
		return invoiceItemId;
	}

	public void setInvoiceItemId(long invoiceItemId) {
		this.invoiceItemId = invoiceItemId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id")
	public InvoiceModel getInvoiceModel() {
		return invoiceModel;
	}

	public void setInvoiceModel(InvoiceModel invoiceModel) {
		this.invoiceModel = invoiceModel;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_id")
	public ItemModel getItemModel() {
		return itemModel;
	}

	public void setItemModel(ItemModel itemModel) {
		this.itemModel = itemModel;
	}

	@Column(name = "quantity", nullable = false)
	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	@Column(name = "item_amount_total", nullable = false)
	public double getItemAmountTotal() {
		return itemAmountTotal;
	}

	public void setItemAmountTotal(double total_price) {
		this.itemAmountTotal = total_price;
	}
}