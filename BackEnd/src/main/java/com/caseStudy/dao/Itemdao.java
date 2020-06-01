package com.caseStudy.dao;

import java.util.List;

import com.caseStudy.exception.ItemNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.ItemModel;

public interface Itemdao {
	
	//CRUD Operations
	
	// 1. Create Operation
	ItemModel createItemModel(ItemModel itemModel, long organizationId)
			throws ItemNotFoundException, OrganizationNotFoundException;
	
	// 2. Read Operation
	ItemModel getItemModelById(Long itemId) throws ItemNotFoundException;
	
	// 3. Update Operation
	ItemModel updateItemModel(Long itemId, ItemModel itemDetails) throws ItemNotFoundException;
	
	// 4. Delete Operation
	ItemModel deleteItemModel(Long itemId) throws ItemNotFoundException;
	
	// Other Operations
	
	// Getting all entries from item table
	List<ItemModel> getAllItemModels();

	// Getting the entries from item table of given organization id
	List<ItemModel> getAllItemModelsByOrganizationId(long organizationId) throws OrganizationNotFoundException;
}