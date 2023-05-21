package com.case_study.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.case_study.entity.UserOrganization;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.model.UserOrganizationModel;
import com.case_study.service.UserOrganizationService;

@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
@RestController
public class UserOrganizationController {
	@Autowired
	@Qualifier("userOrganizationService")
	private UserOrganizationService userOrganizationService;

	@GetMapping("users/organizations")
	ResponseEntity<List<UserOrganizationModel>> getAllUserOrganizations() {
		List<UserOrganizationModel> userOrganizationModels = new ArrayList<>();
		List<UserOrganization> userOrganizations = userOrganizationService.findAll();
		for (UserOrganization userOrganization : userOrganizations) {
			userOrganizationModels.add(new UserOrganizationModel(userOrganization));
		}
		return ResponseEntity.ok().body(userOrganizationModels);
	}

	@GetMapping("users/{id}/organizations")
	ResponseEntity<UserOrganizationModel> findByUserId(@PathVariable(value = "id") Long userId)
			throws UserOrganizationNotFoundException, UserNotFoundException {
		UserOrganization userOrganization = userOrganizationService.findByUserId(userId);
		UserOrganizationModel userOrganizationModel = new UserOrganizationModel();
		BeanUtils.copyProperties(userOrganization, userOrganizationModel);
		return ResponseEntity.ok().body(userOrganizationModel);
	}

	@PutMapping("users/{id}/organizations")
	ResponseEntity<UserOrganizationModel> updateUserOrganization(@PathVariable(value = "id") Long userOrganizationId,
			@Valid @RequestBody UserOrganizationModel userOrganizationModel)
			throws UserOrganizationNotFoundException, OrganizationNotFoundException {
		UserOrganizationModel updatedUserOrganizationModel = new UserOrganizationModel();
		UserOrganization userOrganization = userOrganizationService.updateById(userOrganizationId,
				userOrganizationModel);
		BeanUtils.copyProperties(userOrganization, updatedUserOrganizationModel);
		return ResponseEntity.ok().body(updatedUserOrganizationModel);
	}
}