package com.caseStudy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.caseStudy.dto.ItemDTO;
import com.caseStudy.exception.ItemNotFoundException;
import com.caseStudy.exception.ItemOrganizationNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.ItemModel;
import com.caseStudy.security.GetCredentials;
import com.caseStudy.service.ItemOrganizationService;
import com.caseStudy.service.ItemService;
import com.caseStudy.service.OrganizationService;
import com.caseStudy.service.UserService;

@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
@RestController
public class ItemController {
	static final Logger logger = Logger.getLogger(ItemController.class);

	@Autowired
	@Qualifier("itemService")
	private ItemService itemService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("organizationService")
	private OrganizationService organizationService;

	@Autowired
	@Qualifier("itemOrganizationService")
	private ItemOrganizationService itemOrganizationService;

	@GetMapping("/items")
	List<ItemDTO> getAllItemModelsByOrganizationId()
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException {
		logger.info("ItemController--->>getAllItemModelsByOrganizationId--->>Start");

		List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		List<ItemModel> itemModelList = itemService.getAllItemModelsByOrganizationId(organizationIdOfCurrentUser);

		for (ItemModel itemModel : itemModelList) {

			itemDTOList.add(new ItemDTO(itemModel));
		}

		logger.info("ItemController--->>getAllItemModelsByOrganizationId--->>Ended");
		return itemDTOList;
	}

	@GetMapping("/item/{id}")
	ResponseEntity<ItemDTO> getItemModelById(@PathVariable(value = "id") Long itemId) throws ItemNotFoundException,
			UserOrganizationNotFoundException, UserNotFoundException, ItemOrganizationNotFoundException {

		logger.info("ItemController--->>getItemModelById--->>Start");

		ItemDTO itemDTO = new ItemDTO();

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdOfRequestedItem = itemOrganizationService.getItemOrganizationModelByItemId(itemId)
				.getOrganizationModel().getOrganizationId();

		if (organizationIdOfCurrentUser == organizationIdOfRequestedItem) {

			ItemModel itemModel = itemService.getItemModelById(itemId);

			BeanUtils.copyProperties(itemModel, itemDTO);
		}

		logger.info("ItemController--->>getItemModelById--->>Ended");

		return ResponseEntity.ok().body(itemDTO);
	}

	@PostMapping("/createitem")
	ResponseEntity<ItemDTO> createItemModel(@Valid @RequestBody ItemModel itemModel)
			throws UserOrganizationNotFoundException, UserNotFoundException, BeansException, ItemNotFoundException,
			OrganizationNotFoundException {
		logger.info("ItemController--->>createItemModel--->>Start");

		ItemDTO itemDTO = new ItemDTO();

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		BeanUtils.copyProperties(itemService.createItemModel(itemModel, organizationIdOfCurrentUser), itemDTO);

		logger.info("ItemController--->>createItemModel--->>Ended");

		return ResponseEntity.ok().body(itemDTO);
	}

	@GetMapping("/deleteitem/{id}")
	ResponseEntity<ItemDTO> deleteItemModel(@PathVariable(value = "id") Long itemId) throws ItemNotFoundException,
			ItemOrganizationNotFoundException, UserOrganizationNotFoundException, UserNotFoundException {
		logger.info("ItemController--->>deleteItemModel--->>Start");

		ItemDTO itemDTO = new ItemDTO();

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdOfRequestedItem = itemOrganizationService.getItemOrganizationModelByItemId(itemId)
				.getOrganizationModel().getOrganizationId();

		if (organizationIdOfCurrentUser == organizationIdOfRequestedItem) {

			ItemModel itemModel = itemService.deleteItemModel(itemId);

			BeanUtils.copyProperties(itemModel, itemDTO);
		}

		logger.info("ItemController--->>deleteItemModel--->>Ended");

		return ResponseEntity.ok().body(itemDTO);
	}

	@PostMapping("/updateitem/{id}")
	ResponseEntity<ItemDTO> updateItemModel(@PathVariable(value = "id") Long itemId,
			@Valid @RequestBody ItemModel itemDetails) throws ItemNotFoundException, BeansException,
			ItemOrganizationNotFoundException, UserOrganizationNotFoundException, UserNotFoundException {

		logger.info("ItemController--->>updateItemModel--->>Start");

		ItemDTO itemDTO = new ItemDTO();

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdOfRequestedItem = itemOrganizationService.getItemOrganizationModelByItemId(itemId)
				.getOrganizationModel().getOrganizationId();

		if (organizationIdOfCurrentUser == organizationIdOfRequestedItem) {

			ItemModel itemModel = itemService.updateItemModel(itemId, itemDetails);

			BeanUtils.copyProperties(itemModel, itemDTO);
		}

		logger.info("ItemController--->>updateItemModel--->>Ended");

		return ResponseEntity.ok().body(itemDTO);
	}
}