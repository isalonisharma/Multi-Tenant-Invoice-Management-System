package com.case_study.service;

import java.util.List;

import com.case_study.bean.CreateItemOrganizationBean;
import com.case_study.entity.ItemOrganization;
import com.case_study.exception.ItemNotFoundException;
import com.case_study.exception.ItemOrganizationNotFoundException;
import com.case_study.exception.OrganizationNotFoundException;

public interface ItemOrganizationService {

	ItemOrganization createItemOrganization(CreateItemOrganizationBean itemOrganizationBean)
			throws ItemNotFoundException, OrganizationNotFoundException;

	List<ItemOrganization> findByOrganizationId(long organizationId) throws OrganizationNotFoundException;

	ItemOrganization findByItemId(Long itemId) throws ItemOrganizationNotFoundException, ItemNotFoundException;	
}