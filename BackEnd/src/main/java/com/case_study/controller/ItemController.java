package com.case_study.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.case_study.entity.Item;
import com.case_study.exception.ItemNotFoundException;
import com.case_study.exception.ItemOrganizationNotFoundException;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.model.ItemModel;
import com.case_study.security.GetCredentials;
import com.case_study.service.ItemOrganizationService;
import com.case_study.service.ItemService;
import com.case_study.service.OrganizationService;
import com.case_study.service.UserService;

@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
@RestController
public class ItemController {
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

	@GetMapping("items")
	ResponseEntity<List<ItemModel>> getActiveItems()
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException {
		List<ItemModel> itemModels = new ArrayList<>();
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		List<Item> items = itemService.findByOrganizationId(organizationIdOfCurrentUser);
		for (Item item : items) {
			itemModels.add(new ItemModel(item));
		}
		return ResponseEntity.ok().body(itemModels);
	}

	@GetMapping("items/{id}")
	ResponseEntity<ItemModel> findById(@PathVariable(value = "id") Long id) throws ItemNotFoundException,
			UserOrganizationNotFoundException, UserNotFoundException, ItemOrganizationNotFoundException {
		ItemModel itemModel = new ItemModel();
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdOfRequestedItem = itemOrganizationService.findByItemId(id).getOrganization().getId();
		if (organizationIdOfCurrentUser == organizationIdOfRequestedItem) {
			Item item = itemService.findById(id);
			BeanUtils.copyProperties(item, itemModel);
		}
		return ResponseEntity.ok().body(itemModel);
	}

	@PostMapping("items")
	ResponseEntity<ItemModel> createItem(@Valid @RequestBody ItemModel requestItemModel)
			throws UserOrganizationNotFoundException, UserNotFoundException, BeansException, ItemNotFoundException,
			OrganizationNotFoundException {
		ItemModel itemModel = new ItemModel();
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		BeanUtils.copyProperties(itemService.createItem(requestItemModel, organizationIdOfCurrentUser), itemModel);
		return ResponseEntity.ok().body(itemModel);
	}

	@DeleteMapping("items/{id}")
	ResponseEntity<ItemModel> deleteById(@PathVariable(value = "id") Long id) throws ItemNotFoundException,
			ItemOrganizationNotFoundException, UserOrganizationNotFoundException, UserNotFoundException {
		ItemModel itemModel = new ItemModel();
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdOfRequestedItem = itemOrganizationService.findByItemId(id).getOrganization().getId();
		if (organizationIdOfCurrentUser == organizationIdOfRequestedItem) {
			Item item = itemService.deleteById(id);
			BeanUtils.copyProperties(item, itemModel);
		}
		return ResponseEntity.ok().body(itemModel);
	}

	@PutMapping("items/{id}")
	ResponseEntity<ItemModel> updateItemModel(@PathVariable(value = "id") Long id,
			@Valid @RequestBody ItemModel requestItemModel) throws ItemNotFoundException, BeansException,
			ItemOrganizationNotFoundException, UserOrganizationNotFoundException, UserNotFoundException {
		ItemModel itemModel = new ItemModel();
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdOfRequestedItem = itemOrganizationService.findByItemId(id).getOrganization().getId();
		if (organizationIdOfCurrentUser == organizationIdOfRequestedItem) {
			Item updatedItem = itemService.updateById(id, requestItemModel);
			BeanUtils.copyProperties(updatedItem, itemModel);
		}
		return ResponseEntity.ok().body(itemModel);
	}
}