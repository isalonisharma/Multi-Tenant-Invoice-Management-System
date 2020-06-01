package com.caseStudy.dto;

import com.caseStudy.model.ItemModel;

public class ItemDTO {
	private long itemId;
	private String itemName;
	private String itemManufacturer;
	private float itemRate;

	public ItemDTO() {
		super();
	}

	public ItemDTO(ItemModel model) {
		super();
		this.itemId = model.getItemId();
		this.itemName = model.getItemName();
		this.itemRate = model.getItemRate();
		this.itemManufacturer = model.getItemManufacturer();
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemManufacturer() {
		return itemManufacturer;
	}

	public void setItemManufacturer(String itemManufacturer) {
		this.itemManufacturer = itemManufacturer;
	}

	public float getItemRate() {
		return itemRate;
	}

	public void setItemRate(float itemRate) {
		this.itemRate = itemRate;
	}
}
