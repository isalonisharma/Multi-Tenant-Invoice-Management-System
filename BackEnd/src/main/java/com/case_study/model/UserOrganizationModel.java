package com.case_study.model;

import com.case_study.entity.UserOrganization;

public class UserOrganizationModel {
	private long id;
	private UserResponseModel user;
	private OrganizationModel organization;

	public UserOrganizationModel() {
		super();
	}

	public UserOrganizationModel(UserOrganization userOrganizationModel) {
		this.user = new UserResponseModel(userOrganizationModel.getUser());
		this.organization = new OrganizationModel(userOrganizationModel.getOrganization());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserResponseModel getUser() {
		return user;
	}

	public void setUser(UserResponseModel user) {
		this.user = user;
	}

	public OrganizationModel getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
	}
}