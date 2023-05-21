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
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "invoice")
@EntityListeners(AuditingEntityListener.class)
public class Invoice implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private User user;
	private Client client;
	private LocalDateTime datePlaced;
	private boolean isRecurring;
	private double amount;
	private boolean isLocked;

	public Invoice() {
		super();
	}

	public Invoice(User user, Client client, LocalDateTime datePlaced, boolean isRecurring, double amount,
			boolean isLocked) {
		super();
		this.user = user;
		this.client = client;
		this.datePlaced = datePlaced;
		this.isRecurring = isRecurring;
		this.amount = amount;
		this.isLocked = isLocked;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Column(name = "date_placed", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	public LocalDateTime getDatePlaced() {
		return datePlaced;
	}

	public void setDatePlaced(LocalDateTime datePlaced) {
		this.datePlaced = datePlaced;
	}

	@Column(name = "is_recurring", nullable = false)
	public boolean isRecurring() {
		return isRecurring;
	}

	public void setRecurring(boolean isRecurring) {
		this.isRecurring = isRecurring;
	}

	@Column(name = "amount", nullable = false)
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Column(name = "is_locked", nullable = false)
	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	@Override
	public String toString() {
		return "InvoiceModel [invoiceId=" + id + ", userId=" + user + ", clientId=" + client + ", datePlaced="
				+ datePlaced + ", invoiceIsRecurring=" + isRecurring + ", amount=" + amount + "]";
	}
}