package com.case_study.model;

import com.case_study.entity.Item;

public class ItemModel {
	private long id;
	private String name;
	private String manufacturer;
	private float rate;
	private boolean isLocked;

	public ItemModel() {
		super();
	}

	public ItemModel(Item item) {
		super();
		this.id = item.getId();
		this.name = item.getName();
		this.rate = item.getRate();
		this.manufacturer = item.getManufacturer();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
}