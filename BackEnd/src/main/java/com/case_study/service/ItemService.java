package com.case_study.service;

import java.util.List;

import com.case_study.entity.Item;
import com.case_study.exception.ItemNotFoundException;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.model.ItemModel;

public interface ItemService {

	Item createItem(ItemModel itemModel, long organizationId) throws ItemNotFoundException, OrganizationNotFoundException;

	Item deleteById(Long id) throws ItemNotFoundException;

	Item updateById(Long id, ItemModel itemModel) throws ItemNotFoundException;

	List<Item> getActiveItems();

	Item findById(Long id) throws ItemNotFoundException;

	List<Item> findByOrganizationId(long organizationId) throws OrganizationNotFoundException;	
}