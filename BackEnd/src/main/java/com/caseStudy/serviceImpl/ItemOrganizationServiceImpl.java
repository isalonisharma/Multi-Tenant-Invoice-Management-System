package com.caseStudy.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caseStudy.bean.CreateItemOrganizationBean;
import com.caseStudy.dao.ItemOrganizationdao;
import com.caseStudy.exception.ItemNotFoundException;
import com.caseStudy.exception.ItemOrganizationNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.ItemOrganizationModel;
import com.caseStudy.service.ItemOrganizationService;

@Service("itemOrganizationService")
public class ItemOrganizationServiceImpl implements ItemOrganizationService {

	@Autowired
	private ItemOrganizationdao itemOrganizationdao;

	@Override
	public ItemOrganizationModel createItemOrganizationModel(CreateItemOrganizationBean itemOrganizationBean)
			throws ItemNotFoundException, OrganizationNotFoundException {

		return itemOrganizationdao.createItemOrganizationModel(itemOrganizationBean);
	}

	@Override
	public List<ItemOrganizationModel> getItemOrganizationModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException {

		return itemOrganizationdao.getItemOrganizationModelByOrganizationId(organizationId);
	}

	@Override
	public ItemOrganizationModel getItemOrganizationModelByItemId(Long itemId)
			throws ItemOrganizationNotFoundException, ItemNotFoundException {

		return itemOrganizationdao.getItemOrganizationModelByItemId(itemId);
	}
}
