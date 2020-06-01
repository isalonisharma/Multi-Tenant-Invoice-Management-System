package com.caseStudy.dao;

import java.util.List;

import com.caseStudy.bean.CreateItemOrganizationBean;
import com.caseStudy.exception.ItemNotFoundException;
import com.caseStudy.exception.ItemOrganizationNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.ItemOrganizationModel;

public interface ItemOrganizationdao {

	// Create Operation
	ItemOrganizationModel createItemOrganizationModel(CreateItemOrganizationBean itemOrganizationBean)
			throws ItemNotFoundException, OrganizationNotFoundException;

	// Getting the entries from item organization table of given organization id
	List<ItemOrganizationModel> getItemOrganizationModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException;

	// Getting the specific entry from item organization table of given item id
	ItemOrganizationModel getItemOrganizationModelByItemId(Long itemId)
			throws ItemOrganizationNotFoundException, ItemNotFoundException;
}