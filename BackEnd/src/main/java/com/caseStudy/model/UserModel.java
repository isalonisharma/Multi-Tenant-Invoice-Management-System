package com.caseStudy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class UserModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long userId;
	private String username;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String password;
	private boolean userIsLocked;

	public UserModel() {
	}

	public UserModel(UserModel userModel) {
		this.userId = userModel.getUserId();
		this.username = userModel.getUsername();
		this.firstName = userModel.getFirstName();
		this.lastName = userModel.getLastName();
		this.mobileNumber = userModel.getMobileNumber();
		this.password = userModel.getPassword();
		this.userIsLocked = userModel.isUserIsLocked();
		// this.roles = userModel.getRoles();
	}

	public UserModel(String username, String firstName, String lastName, String mobileNumber, String password,
			boolean userIsLocked) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.userIsLocked = userIsLocked;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@NotBlank
	@Email
	@Column(name = "username", nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotBlank
	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "first_name", nullable = false)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", nullable = false)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "mobile_number", nullable = false)
	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Column(name = "user_is_locked", nullable = false)
	public boolean isUserIsLocked() {
		return userIsLocked;
	}

	public void setUserIsLocked(boolean userIsLocked) {
		this.userIsLocked = userIsLocked;
	}

	@Override
	public String toString() {
		return "UserModel [userId=" + userId + ", username=" + username + ", firstName=" + firstName + ", lastName="
				+ lastName + ", mobileNumber=" + mobileNumber + ", password=" + password + ", organization="
				+ ", userIsLocked=" + userIsLocked + "]";
	}
}