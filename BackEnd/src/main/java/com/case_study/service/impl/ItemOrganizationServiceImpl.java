package com.case_study.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.case_study.bean.CreateItemOrganizationBean;
import com.case_study.entity.Item;
import com.case_study.entity.ItemOrganization;
import com.case_study.entity.Organization;
import com.case_study.exception.ItemNotFoundException;
import com.case_study.exception.ItemOrganizationNotFoundException;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.repository.ItemOrganizationRepository;
import com.case_study.service.ItemOrganizationService;
import com.case_study.service.ItemService;
import com.case_study.service.OrganizationService;

@Service("itemOrganizationService")
public class ItemOrganizationServiceImpl implements ItemOrganizationService {
	@Autowired
	private ItemOrganizationRepository itemOrganizationRepository;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private ItemService itemService;

	@Override
	public ItemOrganization createItemOrganization(CreateItemOrganizationBean itemOrganizationBean)
			throws ItemNotFoundException, OrganizationNotFoundException {
		ItemOrganization itemOrganization = new ItemOrganization();
		Item item = itemService.findById(itemOrganizationBean.getItemId());
		Organization organization = organizationService.findById(itemOrganizationBean.getOrganizationId());
		itemOrganization.setItem(item);
		itemOrganization.setOrganization(organization);
		return itemOrganizationRepository.save(itemOrganization);
	}

	@Override
	public List<ItemOrganization> findByOrganizationId(long organizationId) throws OrganizationNotFoundException {
		Organization organization = organizationService.findById(organizationId);
		return itemOrganizationRepository.findByOrganization(organization);
	}

	@Override
	public ItemOrganization findByItemId(Long itemId) throws ItemOrganizationNotFoundException, ItemNotFoundException {
		Item item = itemService.findById(itemId);
		return itemOrganizationRepository.findByItem(item);
	}
}