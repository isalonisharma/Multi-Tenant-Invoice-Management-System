package com.case_study.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.case_study.entity.Role;
import com.case_study.entity.UserRole;
import com.case_study.exception.RoleNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserRoleNotFoundException;
import com.case_study.model.UserRoleModel;
import com.case_study.service.UserRoleService;

@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
@RestController
public class UserRoleController {
	@Autowired
	@Qualifier("userRoleService")
	private UserRoleService userRoleService;

	@GetMapping("users/roles")
	ResponseEntity<List<UserRoleModel>> getAllUserRoles() {
		List<UserRole> userRoles = userRoleService.findAll();
		List<UserRoleModel> userRoleModels = new ArrayList<>();
		for (UserRole userRole : userRoles) {
			userRoleModels.add(new UserRoleModel(userRole));
		}
		return ResponseEntity.ok().body(userRoleModels);
	}

	@GetMapping("users/{id}/roles")
	ResponseEntity<Set<Role>> findByUserId(@PathVariable(value = "id") Long userId)
			throws UserRoleNotFoundException, UserNotFoundException {
		List<UserRole> userRoles = userRoleService.findByUserId(userId);
		List<Role> roles = new ArrayList<>();
		for (UserRole userRoleModel : userRoles) {
			roles.add(userRoleModel.getRole());
		}
		Set<Role> roleSet = new HashSet<>();
		for (Role role : roles) {
			roleSet.add(role);
		}
		return ResponseEntity.ok().body(roleSet);
	}

	@PutMapping("users/{id}/roles")
	ResponseEntity<UserRoleModel> updateUserRole(@PathVariable(value = "id") Long id,
			@Valid @RequestBody UserRoleModel userRoleModel)
			throws UserRoleNotFoundException, UserNotFoundException, RoleNotFoundException {
		UserRole userRole = userRoleService.updateById(id, userRoleModel);
		UserRoleModel updatedUserRoleModel = new UserRoleModel();
		BeanUtils.copyProperties(userRole, updatedUserRoleModel);
		return ResponseEntity.ok().body(updatedUserRoleModel);
	}
}