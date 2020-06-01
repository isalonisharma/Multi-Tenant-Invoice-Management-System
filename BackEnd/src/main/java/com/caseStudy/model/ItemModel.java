package com.caseStudy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "item")
@EntityListeners(AuditingEntityListener.class)
public class ItemModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long itemId;
	private String itemName;
	private String itemManufacturer;
	private float itemRate;
	private boolean itemIsLocked;

	public ItemModel() {
	}

	public ItemModel(long itemId, String itemName, String itemManufacturer, float itemRate, boolean itemIsLocked) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemManufacturer = itemManufacturer;
		this.itemRate = itemRate;
		this.itemIsLocked = itemIsLocked;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	@Column(name = "item_name", nullable = false)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "item_manufacturer", nullable = false)
	public String getItemManufacturer() {
		return itemManufacturer;
	}

	public void setItemManufacturer(String itemManufacturer) {
		this.itemManufacturer = itemManufacturer;
	}

	@Column(name = "item_rate", nullable = false)
	public float getItemRate() {
		return itemRate;
	}

	public void setItemRate(float itemRate) {
		this.itemRate = itemRate;
	}

	@Column(name = "item_is_locked", nullable = false)
	public boolean isItemIsLocked() {
		return itemIsLocked;
	}

	public void setItemIsLocked(boolean itemIsLocked) {
		this.itemIsLocked = itemIsLocked;
	}
}
