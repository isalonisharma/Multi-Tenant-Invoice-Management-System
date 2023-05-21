package com.case_study.model;

import java.util.ArrayList;
import java.util.List;

import com.case_study.entity.User;
import com.case_study.entity.UserRole;

public class UserResponseModel {
	private long id;
	private String username;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String token;
	private List<String> roles;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserResponseModel() {
		super();
	}

	public UserResponseModel(User user) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.mobileNumber = user.getMobileNumber();
	}

	public UserResponseModel(User user, List<UserRole> userRoles, String token) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.mobileNumber = user.getMobileNumber();
		this.token = token;
		this.roles = new ArrayList<>();
		for (UserRole userRole : userRoles) {
			roles.add(userRole.getRole().getDesignation());
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}