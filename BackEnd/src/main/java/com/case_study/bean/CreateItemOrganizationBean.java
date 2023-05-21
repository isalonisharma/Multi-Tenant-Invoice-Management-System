package com.case_study.bean;

public class CreateItemOrganizationBean {
	private long itemId;
	private long organizationId;

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	public CreateItemOrganizationBean(long itemId, long organizationId) {
		super();
		this.itemId = itemId;
		this.organizationId = organizationId;
	}
}