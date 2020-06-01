package com.caseStudy.service;

import java.util.List;

import com.caseStudy.bean.CreateItemOrganizationBean;
import com.caseStudy.exception.ItemNotFoundException;
import com.caseStudy.exception.ItemOrganizationNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.ItemOrganizationModel;

/**
 * Class: ItemOrganizationServices
 * 
 * @author saloni.sharma
 */
public interface ItemOrganizationService {
	/**
	 * Function Name: createItemOrganizationModel
	 * 
	 * @param itemOrganizationBean
	 * 
	 * @return ItemOrganizationModel
	 * 
	 * @throws ItemNotFoundException
	 * @throws OrganizationNotFoundException
	 */
	ItemOrganizationModel createItemOrganizationModel(CreateItemOrganizationBean itemOrganizationBean)
			throws ItemNotFoundException, OrganizationNotFoundException;

	/**
	 * Function Name: getItemOrganizationModelByOrganizationId
	 * 
	 * @param organizationId
	 * 
	 * @return list of ItemOrganizationModel
	 * 
	 * @throws OrganizationNotFoundException
	 */
	List<ItemOrganizationModel> getItemOrganizationModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException;

	/**
	 * Function Name: getItemOrganizationModelByItemId
	 * 
	 * @param itemId
	 * 
	 * @return ItemOrganizationModel
	 * 
	 * @throws ItemOrganizationNotFoundException
	 * @throws ItemNotFoundException
	 */
	ItemOrganizationModel getItemOrganizationModelByItemId(Long itemId)
			throws ItemOrganizationNotFoundException, ItemNotFoundException;
}
