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
@Table(name = "item_organization")
@EntityListeners(AuditingEntityListener.class)
public class ItemOrganizationModel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long itemOrganizationId;
	private ItemModel itemModel;
	private OrganizationModel organizationModel;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getItemOrganizationId() {
		return itemOrganizationId;
	}

	public void setItemOrganizationId(long itemOrganizationId) {
		this.itemOrganizationId = itemOrganizationId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_id", nullable = false)
	public ItemModel getItemModel() {
		return itemModel;
	}

	public void setItemModel(ItemModel itemModel) {
		this.itemModel = itemModel;
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