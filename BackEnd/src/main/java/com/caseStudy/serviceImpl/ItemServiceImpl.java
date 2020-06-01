package com.caseStudy.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caseStudy.dao.Itemdao;
import com.caseStudy.exception.ItemNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.ItemModel;
import com.caseStudy.service.ItemService;

@Service("itemService")
public class ItemServiceImpl implements ItemService {
	@Autowired
	private Itemdao itemDao;

	@Override
	public List<ItemModel> getAllItemModels() {
		return itemDao.getAllItemModels();
	}

	@Override
	public ItemModel getItemModelById(Long itemId) throws ItemNotFoundException {
		return itemDao.getItemModelById(itemId);
	}

	@Override
	public ItemModel createItemModel(ItemModel itemModel, long organizationId)
			throws ItemNotFoundException, OrganizationNotFoundException {
		return itemDao.createItemModel(itemModel, organizationId);
	}

	@Override
	public ItemModel deleteItemModel(Long itemId) throws ItemNotFoundException {
		return itemDao.deleteItemModel(itemId);
	}

	@Override
	public ItemModel updateItemModel(Long itemId, ItemModel itemDetails) throws ItemNotFoundException {
		return itemDao.updateItemModel(itemId, itemDetails);
	}

	@Override
	public List<ItemModel> getAllItemModelsByOrganizationId(long organizationId) throws OrganizationNotFoundException {
		return itemDao.getAllItemModelsByOrganizationId(organizationId);
	}
}