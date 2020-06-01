package com.caseStudy.dto;

import java.util.ArrayList;
import java.util.List;

import com.caseStudy.model.UserModel;
import com.caseStudy.model.UserRoleModel;

public class UserDTO {
	private long userId;
	private String username;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String token;
	private List<String> role;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserDTO() {
		super();
	}

	public UserDTO(UserModel model) {
		super();
		this.userId = model.getUserId();
		this.username = model.getUsername();
		this.firstName = model.getFirstName();
		this.lastName = model.getLastName();
		this.mobileNumber = model.getMobileNumber();
	}

	public UserDTO(UserModel model, List<UserRoleModel> userRole, String token) {
		this.userId = model.getUserId();
		this.username = model.getUsername();
		this.firstName = model.getFirstName();
		this.lastName = model.getLastName();
		this.mobileNumber = model.getMobileNumber();
		this.token = token;
		this.role = new ArrayList<String>();
		for (UserRoleModel userRoleModel : userRole) {
			role.add(userRoleModel.getRoleModel().getRole());
		}
		System.out.println(this.role);
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}
}
