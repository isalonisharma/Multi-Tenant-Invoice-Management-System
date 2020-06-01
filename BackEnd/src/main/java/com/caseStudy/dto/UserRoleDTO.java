package com.caseStudy.dto;

import com.caseStudy.model.RoleModel;
import com.caseStudy.model.UserRoleModel;

public class UserRoleDTO {
	private long userRoleId;
	private UserDTO userDTO;
	private RoleModel roleModel;

	public UserRoleDTO() {
		super();
	}

	public UserRoleDTO(UserRoleModel userRoleModel) {
		this.roleModel = userRoleModel.getRoleModel();
		this.userDTO = new UserDTO(userRoleModel.getUserModel());
	}

	public long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public RoleModel getRoleModel() {
		return roleModel;
	}

	public void setRoleModel(RoleModel roleModel) {
		this.roleModel = roleModel;
	}
}