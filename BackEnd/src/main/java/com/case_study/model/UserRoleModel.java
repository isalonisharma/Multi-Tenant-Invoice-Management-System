package com.case_study.model;

import com.case_study.entity.UserRole;

public class UserRoleModel {
	private long id;
	private UserResponseModel user;
	private RoleModel role;

	public UserRoleModel() {
		super();
	}

	public UserRoleModel(UserRole userRoleModel) {
		this.role = new RoleModel(userRoleModel.getRole());
		this.user = new UserResponseModel(userRoleModel.getUser());
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

	public RoleModel getRole() {
		return role;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}
}