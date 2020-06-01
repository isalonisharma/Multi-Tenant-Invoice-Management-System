package com.caseStudy.bean;

public class CreateUserOrganizationBean {
	private long userId;

	private long organizationId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	public CreateUserOrganizationBean(long userId, long organizationId) {
		super();
		this.userId = userId;
		this.organizationId = organizationId;
	}

}
