package com.case_study.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.case_study.model.RoleModel;

@Entity
@Table(name = "role")
@EntityListeners(AuditingEntityListener.class)
public class Role implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String designation;

	public Role() {
		super();
	}

	public Role(long id, String designation) {
		super();
		this.id = id;
		this.designation = designation;
	}

	public Role(RoleModel roleModel) {
		super();
		this.id = roleModel.getId();
		this.designation = roleModel.getDesignation();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "designation", nullable = false)
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
}