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
@Table(name = "client_organization")
@EntityListeners(AuditingEntityListener.class)
public class ClientOrganizationModel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long clientOrganizationId;
	private ClientModel clientModel;
	private OrganizationModel organizationModel;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getClientOrganizationId() {
		return clientOrganizationId;
	}

	public void setClientOrganizationId(long clientOrganizationId) {
		this.clientOrganizationId = clientOrganizationId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id", nullable = false)
	public ClientModel getClientModel() {
		return clientModel;
	}

	public void setClientModel(ClientModel clientModel) {
		this.clientModel = clientModel;
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