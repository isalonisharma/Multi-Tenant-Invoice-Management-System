package com.case_study.entity;

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
public class InvoiceItem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private Invoice invoice;
	private Item item;
	private long quantity;
	private double totalAmount;

	public InvoiceItem() {
		super();
	}

	public InvoiceItem(Invoice invoice, Item item, long quantity, double totalAmount) {
		super();
		this.invoice = invoice;
		this.item = item;
		this.quantity = quantity;
		this.totalAmount = totalAmount;
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

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_id")
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Column(name = "quantity", nullable = false)
	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	@Column(name = "total_amount", nullable = false)
	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
}