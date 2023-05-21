package com.case_study.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.case_study.bean.CreateItemOrganizationBean;
import com.case_study.entity.Item;
import com.case_study.entity.ItemOrganization;
import com.case_study.exception.ItemNotFoundException;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.model.ItemModel;
import com.case_study.repository.ItemRepository;
import com.case_study.service.ItemOrganizationService;
import com.case_study.service.ItemService;
import com.case_study.utility.CommonConstants;

@Service("itemService")
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ItemOrganizationService itemOrganizationService;

	@Override
	public Item createItem(ItemModel itemModel, long organizationId)
			throws ItemNotFoundException, OrganizationNotFoundException {
		Item createdItem = itemRepository.save(new Item(itemModel));
		itemOrganizationService
				.createItemOrganization(new CreateItemOrganizationBean(createdItem.getId(), organizationId));
		return createdItem;
	}

	@Override
	public Item deleteById(Long id) throws ItemNotFoundException {
		Item item = itemRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException(CommonConstants.ITEM_NOT_FOUND + id));
		item.setLocked(true);
		return itemRepository.save(item);
	}

	@Override
	public Item updateById(Long id, ItemModel itemModel) throws ItemNotFoundException {
		Item item = itemRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException(CommonConstants.ITEM_NOT_FOUND + id));
		if (item.isLocked()) {
			throw new ItemNotFoundException(CommonConstants.ITEM_NOT_FOUND + id);
		}
		item.setName(itemModel.getName());
		item.setManufacturer(itemModel.getManufacturer());
		item.setRate(itemModel.getRate());
		item.setLocked(itemModel.isLocked());
		return itemRepository.save(item);
	}

	@Override
	public List<Item> getActiveItems() {
		List<Item> items = itemRepository.findAll();
		items.removeIf(Item::isLocked);
		return items;
	}

	@Override
	public Item findById(Long id) throws ItemNotFoundException {
		Item item = itemRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException(CommonConstants.ITEM_NOT_FOUND + id));
		if (item.isLocked()) {
			throw new ItemNotFoundException(CommonConstants.ITEM_NOT_FOUND + id);
		}
		return item;
	}

	@Override
	public List<Item> findByOrganizationId(long organizationId) throws OrganizationNotFoundException {
		List<Item> items = new ArrayList<>();
		List<ItemOrganization> itemOrganizations = itemOrganizationService.findByOrganizationId(organizationId);
		for (ItemOrganization itemOrganization : itemOrganizations) {
			items.add(itemOrganization.getItem());
		}
		items.removeIf(Item::isLocked);
		return items;
	}
}