package com.case_study.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.case_study.model.ItemModel;

@Entity
@Table(name = "item")
@EntityListeners(AuditingEntityListener.class)
public class Item implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String manufacturer;
	private float rate;
	private boolean isLocked;

	public Item() {
		super();
	}

	public Item(long id, String name, String manufacturer, float rate, boolean isLocked) {
		super();
		this.id = id;
		this.name = name;
		this.manufacturer = manufacturer;
		this.rate = rate;
		this.isLocked = isLocked;
	}

	public Item(ItemModel itemModel) {
		super();
		this.id = itemModel.getId();
		this.name = itemModel.getName();
		this.manufacturer = itemModel.getManufacturer();
		this.rate = itemModel.getRate();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "manufacturer", nullable = false)
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Column(name = "rate", nullable = false)
	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	@Column(name = "is_locked", nullable = false)
	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
}