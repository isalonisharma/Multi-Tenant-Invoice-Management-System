package com.case_study.model;

import com.case_study.entity.Role;

public class RoleModel {
	private long id;
	private String designation;

	public RoleModel() {
		super();
	}

	public RoleModel(long id, String designation) {
		super();
		this.id = id;
		this.designation = designation;
	}

	public RoleModel(Role role) {
		super();
		this.id = role.getId();
		this.designation = role.getDesignation();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
}