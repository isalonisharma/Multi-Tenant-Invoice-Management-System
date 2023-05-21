package com.case_study.bean;

public class CreateUserRoleBean {
	private long userId;
	private long roleId;

	public CreateUserRoleBean(long userId, long roleId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
}