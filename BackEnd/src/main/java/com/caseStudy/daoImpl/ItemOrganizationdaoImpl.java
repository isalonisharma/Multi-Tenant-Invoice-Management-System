package com.caseStudy.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caseStudy.bean.CreateItemOrganizationBean;
import com.caseStudy.dao.ItemOrganizationdao;
import com.caseStudy.exception.ItemNotFoundException;
import com.caseStudy.exception.ItemOrganizationNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.ItemModel;
import com.caseStudy.model.ItemOrganizationModel;
import com.caseStudy.model.OrganizationModel;
import com.caseStudy.repository.ItemOrganizationRepository;
import com.caseStudy.service.ItemService;
import com.caseStudy.service.OrganizationService;

@Repository
public class ItemOrganizationdaoImpl implements ItemOrganizationdao {
	static final Logger logger = Logger.getLogger(ItemOrganizationdaoImpl.class);

	@Autowired
	private ItemOrganizationRepository itemOrganizationRepository;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private ItemService itemService;

	@Override
	public ItemOrganizationModel createItemOrganizationModel(CreateItemOrganizationBean itemOrganizationBean)
			throws ItemNotFoundException, OrganizationNotFoundException {

		logger.info("ItemOrganizationdaoImpl--->>createItemOrganizationModel--->>Start");

		ItemOrganizationModel itemOrganizationModel = new ItemOrganizationModel();

		ItemModel itemModel = itemService.getItemModelById(itemOrganizationBean.getItemId());

		OrganizationModel organizationModel = organizationService
				.getOrganizationModelById(itemOrganizationBean.getOrganizationId());

		itemOrganizationModel.setItemModel(itemModel);
		itemOrganizationModel.setOrganizationModel(organizationModel);

		itemOrganizationRepository.save(itemOrganizationModel);

		logger.info("ItemOrganizationdaoImpl--->>createItemOrganizationModel--->>End");

		return itemOrganizationModel;
	}

	@Override
	public ItemOrganizationModel getItemOrganizationModelByItemId(Long itemId)
			throws ItemOrganizationNotFoundException, ItemNotFoundException {
		logger.info("ItemOrganizationdaoImpl--->>getItemOrganizationModelByItemId--->>Start");

		ItemModel itemModel = itemService.getItemModelById(itemId);

		ItemOrganizationModel itemOrganizationModel = itemOrganizationRepository.findByitemModel(itemModel);

		logger.info("ItemOrganizationdaoImpl--->>getItemOrganizationModelByItemId--->>End");

		return itemOrganizationModel;

	}

	@Override
	public List<ItemOrganizationModel> getItemOrganizationModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException {
		logger.info("ItemOrganizationdaoImpl--->>getItemOrganizationModelByOrganizationId--->>Start");

		OrganizationModel organizationModel = organizationService.getOrganizationModelById(organizationId);

		logger.info("ItemOrganizationdaoImpl--->>getItemOrganizationModelByOrganizationId--->>End");

		return itemOrganizationRepository.findByorganizationModel(organizationModel);
	}
}
