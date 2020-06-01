package com.caseStudy.service;

import java.util.List;

import com.caseStudy.exception.ItemNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.ItemModel;

/**
 * Class: ItemService
 * 
 * @author saloni.sharma
 */
public interface ItemService {
	/**
	 * Function Name: getAllItemModels
	 * 
	 * @return list of ItemModel
	 */
	List<ItemModel> getAllItemModels();

	/**
	 * Function Name: getAllItemModelsByOrganizationId
	 * 
	 * @param organizationId
	 * 
	 * @return list of ItemModel
	 * 
	 * @throws OrganizationNotFoundException
	 */
	List<ItemModel> getAllItemModelsByOrganizationId(long organizationId) throws OrganizationNotFoundException;

	/**
	 * Function Name: getItemModelById
	 * 
	 * @param itemId
	 * 
	 * @return ItemModel
	 * 
	 * @throws ItemNotFoundException
	 */
	ItemModel getItemModelById(Long itemId) throws ItemNotFoundException;

	/**
	 * Function Name: createItemModel
	 * 
	 * @param itemModel
	 * @param organizationId
	 * 
	 * @return ItemModel
	 * 
	 * @throws ItemNotFoundException
	 * @throws OrganizationNotFoundException
	 */
	ItemModel createItemModel(ItemModel itemModel, long organizationId)
			throws ItemNotFoundException, OrganizationNotFoundException;

	/**
	 * Function Name: deleteItemModel
	 * 
	 * @param itemId
	 * 
	 * @return ItemModel
	 * 
	 * @throws ItemNotFoundException
	 */
	ItemModel deleteItemModel(Long itemId) throws ItemNotFoundException;

	/**
	 * Function Name: updateItemModel
	 * 
	 * @param itemId
	 * @param itemDetails
	 * 
	 * @return ItemModel
	 * 
	 * @throws ItemNotFoundException
	 */
	ItemModel updateItemModel(Long itemId, ItemModel itemDetails) throws ItemNotFoundException;
}
