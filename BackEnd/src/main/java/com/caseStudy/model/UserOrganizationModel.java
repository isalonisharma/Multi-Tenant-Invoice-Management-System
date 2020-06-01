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
@Table(name = "user_organization")
@EntityListeners(AuditingEntityListener.class)
public class UserOrganizationModel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private long userOrganizationId;
	private UserModel userModel;
	private OrganizationModel organizationModel;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getUserOrganizationId() {
		return userOrganizationId;
	}

	public void setUserOrganizationId(long userOrganizationId) {
		this.userOrganizationId = userOrganizationId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "organization_id", nullable = false)
	public OrganizationModel getOrganizationModel() {
		return organizationModel;
	}

	public void setOrganizationModel(OrganizationModel organizationModel) {
		this.organizationModel = organizationModel;
	}
}