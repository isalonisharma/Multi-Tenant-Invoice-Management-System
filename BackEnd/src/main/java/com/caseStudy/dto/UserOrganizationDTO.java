package com.caseStudy.dto;

import com.caseStudy.model.UserOrganizationModel;

public class UserOrganizationDTO {
	private long userOrganizationId;
	private UserDTO userDTO;
	private OrganizationDTO organizationDTO;

	public UserOrganizationDTO() {
		super();
	}

	public UserOrganizationDTO(UserOrganizationModel userOrganizationModel) {
		this.userDTO = new UserDTO(userOrganizationModel.getUserModel());
		this.organizationDTO = new OrganizationDTO(userOrganizationModel.getOrganizationModel());
	}

	public long getUserOrganizationId() {
		return userOrganizationId;
	}

	public void setUserOrganizationId(long userOrganizationId) {
		this.userOrganizationId = userOrganizationId;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public OrganizationDTO getOrganizationDTO() {
		return organizationDTO;
	}

	public void setOrganizationDTO(OrganizationDTO organizationDTO) {
		this.organizationDTO = organizationDTO;
	}
}