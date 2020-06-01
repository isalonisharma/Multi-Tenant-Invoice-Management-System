package com.caseStudy.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caseStudy.bean.CreateItemOrganizationBean;
import com.caseStudy.dao.Itemdao;
import com.caseStudy.exception.ItemNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.ItemModel;
import com.caseStudy.model.ItemOrganizationModel;
import com.caseStudy.repository.ItemRepository;
import com.caseStudy.service.ItemOrganizationService;

@Repository
public class ItemdaoImpl implements Itemdao {
	static final Logger logger = Logger.getLogger(ItemdaoImpl.class);

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ItemOrganizationService itemOrganizationService;

	@Override
	public List<ItemModel> getAllItemModels() {
		logger.info("ItemdaoImpl--->>getAllItemModels--->>Start");

		List<ItemModel> itemModelList = itemRepository.findAll();
		
		itemModelList.removeIf((ItemModel itemModel) -> itemModel.isItemIsLocked());

		logger.info("ItemdaoImpl--->>getAllItemModels--->>End");

		return itemModelList;
	}

	@Override
	public ItemModel getItemModelById(Long itemId) throws ItemNotFoundException {
		logger.info("ItemdaoImpl--->>getItemModelById--->>Start");

		ItemModel itemModel = itemRepository.findById(itemId)
				.orElseThrow(() -> new ItemNotFoundException("Item not found :: " + itemId));

		if (itemModel.isItemIsLocked()) {
			throw new ItemNotFoundException("Item not found :: " + itemId);
		}

		logger.info("ItemdaoImpl--->>getItemModelById--->>End");

		return itemModel;
	}

	@Override
	public ItemModel createItemModel(ItemModel itemModel, long organizationId)
			throws ItemNotFoundException, OrganizationNotFoundException {
		logger.info("ItemdaoImpl--->>createItemModel--->>Start");

		itemOrganizationService.createItemOrganizationModel(
				new CreateItemOrganizationBean(itemRepository.save(itemModel).getItemId(), organizationId));

		logger.info("ItemdaoImpl--->>createItemModel--->>End");

		return itemModel;
	}

	@Override
	public ItemModel deleteItemModel(Long itemId) throws ItemNotFoundException {
		logger.info("ItemdaoImpl--->>deleteItemModel--->>Start");

		ItemModel itemModel = itemRepository.findById(itemId)
				.orElseThrow(() -> new ItemNotFoundException("Item not found :: " + itemId));

		itemModel.setItemIsLocked(true);

		final ItemModel deletedItemModel = itemRepository.save(itemModel);

		logger.info("ItemdaoImpl--->>deleteItemModel--->>End");

		return deletedItemModel;
	}

	@Override
	public ItemModel updateItemModel(Long itemId, ItemModel itemDetails) throws ItemNotFoundException {
		logger.info("ItemdaoImpl--->>updateItemModel--->>Start");

		ItemModel itemModel = itemRepository.findById(itemId)
				.orElseThrow(() -> new ItemNotFoundException("Item not found :: " + itemId));

		if (itemModel.isItemIsLocked()) {
			throw new ItemNotFoundException("Item not found :: " + itemId);
		}

		itemModel.setItemName(itemDetails.getItemName());
		itemModel.setItemManufacturer(itemDetails.getItemManufacturer());
		itemModel.setItemRate(itemDetails.getItemRate());
		itemModel.setItemIsLocked(itemDetails.isItemIsLocked());

		final ItemModel updatedItemModel = itemRepository.save(itemModel);

		logger.info("ItemdaoImpl--->>updateItemModel--->>End");

		return updatedItemModel;
	}

	@Override
	public List<ItemModel> getAllItemModelsByOrganizationId(long organizationId) throws OrganizationNotFoundException {
		logger.info("ItemdaoImpl--->>getAllItemModelsByOrganizationId--->>Start");

		List<ItemModel> itemModelList = new ArrayList<ItemModel>();

		List<ItemOrganizationModel> itemOrganizationModelList = itemOrganizationService
				.getItemOrganizationModelByOrganizationId(organizationId);

		for (ItemOrganizationModel itemOrganizationModel : itemOrganizationModelList) {
			itemModelList.add(itemOrganizationModel.getItemModel());
		}

		itemModelList.removeIf((ItemModel itemModel) -> itemModel.isItemIsLocked());
		
		logger.info("ItemdaoImpl--->>getAllItemModelsByOrganizationId--->>End");

		return itemModelList;
	}
}