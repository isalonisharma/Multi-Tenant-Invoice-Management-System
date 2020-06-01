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
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "invoice")
@EntityListeners(AuditingEntityListener.class)
public class InvoiceModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long invoiceId;
	private UserModel userModel;
	private ClientModel clientModel;
	private LocalDateTime datePlaced;
	private boolean invoiceIsRecurring;
	private double amount;
	private boolean invoiceIsLocked;

	public InvoiceModel() {
	}

	public InvoiceModel(UserModel userModel, ClientModel clientModel, LocalDateTime datePlaced,
			boolean invoiceIsRecurring, double amount, boolean invoiceIsLocked) {
		super();
		this.userModel = userModel;
		this.clientModel = clientModel;
		this.datePlaced = datePlaced;
		this.invoiceIsRecurring = invoiceIsRecurring;
		this.amount = amount;
		this.invoiceIsLocked = invoiceIsLocked;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	public ClientModel getClientModel() {
		return clientModel;
	}

	public void setClientModel(ClientModel clientModel) {
		this.clientModel = clientModel;
	}

	@Column(name = "date_placed", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	public LocalDateTime getDatePlaced() {
		return datePlaced;
	}

	public void setDatePlaced(LocalDateTime datePlaced) {
		this.datePlaced = datePlaced;
	}

	@Column(name = "invoice_is_recurring", nullable = false)
	public boolean isInvoiceIsRecurring() {
		return invoiceIsRecurring;
	}

	public void setInvoiceIsRecurring(boolean invoiceIsRecurring) {
		this.invoiceIsRecurring = invoiceIsRecurring;
	}

	@Column(name = "amount", nullable = false)
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Column(name = "invoice_is_locked", nullable = false)
	public boolean isInvoiceIsLocked() {
		return invoiceIsLocked;
	}

	public void setInvoiceIsLocked(boolean invoiceIsLocked) {
		this.invoiceIsLocked = invoiceIsLocked;
	}

	@Override
	public String toString() {
		return "InvoiceModel [invoiceId=" + invoiceId + ", userId=" + userModel + ", clientId=" + clientModel
				+ ", datePlaced=" + datePlaced + ", invoiceIsRecurring=" + invoiceIsRecurring + ", amount=" + amount
				+ "]";
	}
}
