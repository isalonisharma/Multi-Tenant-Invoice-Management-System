package com.case_study.bean;

public class CreateClientOrganizationBean {
	private long clientId;
	private long organizationId;

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	public CreateClientOrganizationBean(long clientId, long organizationId) {
		super();
		this.clientId = clientId;
		this.organizationId = organizationId;
	}
}