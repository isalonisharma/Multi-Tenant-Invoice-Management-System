package com.caseStudy.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "user_role")
@EntityListeners(AuditingEntityListener.class)
public class UserRoleModel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long userRoleId;
	private UserModel userModel;
	private RoleModel roleModel;

	public UserRoleModel() {
	}

	public UserRoleModel(long userRoleId, UserModel userId, RoleModel roleId) {
		super();
		this.userRoleId = userRoleId;
		this.userModel = userId;
		this.roleModel = roleId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(long userRoleId) {
		this.userRoleId = userRoleId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", nullable = false)
	public RoleModel getRoleModel() {
		return roleModel;
	}

	public void setRoleModel(RoleModel roleModel) {
		this.roleModel = roleModel;
	}
}
