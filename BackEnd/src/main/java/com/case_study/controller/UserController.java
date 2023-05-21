package com.case_study.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.activation.FileTypeMap;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.case_study.bean.CreateUserBean;
import com.case_study.entity.Role;
import com.case_study.entity.User;
import com.case_study.exception.IllegalTryToAccessException;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.exception.RoleNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.exception.UserRoleNotFoundException;
import com.case_study.model.UserRequestModel;
import com.case_study.model.UserResponseModel;
import com.case_study.security.GetCredentials;
import com.case_study.service.OrganizationService;
import com.case_study.service.RoleService;
import com.case_study.service.UserService;
import com.itextpdf.text.DocumentException;

@RestController
public class UserController {
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("organizationService")
	private OrganizationService organizationService;

	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;

	@PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("users")
	ResponseEntity<List<UserResponseModel>> findByOrganizationId()
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException {
		List<UserResponseModel> userModels = new ArrayList<>();
		if (GetCredentials.checkRoleAuthentication() == 1) {
			List<User> users = userService.getActiveUsers();
			for (User user : users) {
				userModels.add(new UserResponseModel(user));
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 2) {
			long organizationIdOfCurrentUser = organizationService
					.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
			List<User> users = userService.findByOrganizationId(organizationIdOfCurrentUser);
			for (User user : users) {
				userModels.add(new UserResponseModel(user));
			}
		}
		return ResponseEntity.ok().body(userModels);
	}

	@GetMapping("users/{id}")
	ResponseEntity<UserResponseModel> findById(@PathVariable(value = "id") Long userId)
			throws UserNotFoundException, UserOrganizationNotFoundException {
		UserResponseModel userModel = new UserResponseModel();
		User user = userService.findById(userId);
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdofRequestedUser = organizationService
				.findByName(userService.findOrganizationByUsername(user.getUsername())).getId();
		if (GetCredentials.checkRoleAuthentication() == 1) {
			BeanUtils.copyProperties(user, userModel);
		} else if (GetCredentials.checkRoleAuthentication() == 2) {
			if (organizationIdOfCurrentUser == organizationIdofRequestedUser) {
				BeanUtils.copyProperties(user, userModel);
			}
		} else if (organizationIdOfCurrentUser == organizationIdofRequestedUser
				&& GetCredentials.checkUserName().equalsIgnoreCase(user.getUsername())) {
			BeanUtils.copyProperties(user, userModel);
		}
		return ResponseEntity.ok().body(userModel);
	}

	@PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@PostMapping("users")
	ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody CreateUserBean createUserBean)
			throws BeansException, UserOrganizationNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserRoleNotFoundException, RoleNotFoundException,
			IllegalTryToAccessException {
		Role role = roleService.findByDesignation(createUserBean.getRole());
		UserResponseModel userModel = new UserResponseModel();
		if (GetCredentials.checkRoleAuthentication() == 1) {
			if (role.getDesignation().equalsIgnoreCase("ROLE_ORGANIZATION_ADMIN")) {
				BeanUtils.copyProperties(
						userService.createUser(createUserBean, createUserBean.getOrganizationId(), role.getId()),
						userModel);
			} else {
				throw new IllegalTryToAccessException("Super Admin is only allowed to create organization admins.");
			}
		} else if (GetCredentials.checkRoleAuthentication() == 2) {
			long organizationIdOfCurrentUser = organizationService
					.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
			if (role.getDesignation().equalsIgnoreCase("ROLE_USER")) {
				BeanUtils.copyProperties(
						userService.createUser(createUserBean, organizationIdOfCurrentUser, role.getId()), userModel);
			} else {
				throw new IllegalTryToAccessException("Organization Admin is only allowed to create user role.");
			}
		}
		return ResponseEntity.ok().body(userModel);
	}

	@PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@DeleteMapping("users/{id}")
	ResponseEntity<UserResponseModel> deleteUser(@PathVariable(value = "id") Long userId)
			throws UserNotFoundException, BeansException, UserOrganizationNotFoundException {
		UserResponseModel userModel = new UserResponseModel();
		if (GetCredentials.checkRoleAuthentication() == 2
				&& userService.findOrganizationByUsername(GetCredentials.checkUserName())
						.equalsIgnoreCase(organizationService
								.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
								.getName())) {
			User user = userService.deleteById(userId);
			BeanUtils.copyProperties(user, userModel);
		}
		if (GetCredentials.checkRoleAuthentication() == 1) {
			User user = userService.deleteById(userId);
			BeanUtils.copyProperties(user, userModel);
		}
		return ResponseEntity.ok().body(userModel);
	}

	@PutMapping("users/{id}")
	ResponseEntity<UserResponseModel> updateUser(@PathVariable(value = "id") Long userId,
			@Valid @RequestBody UserRequestModel userRequestModel)
			throws UserNotFoundException, BeansException, UserOrganizationNotFoundException {
		UserResponseModel userResponseModel = new UserResponseModel();
		if ((GetCredentials.checkRoleAuthentication() == 1
				&& userService.findOrganizationByUsername(GetCredentials.checkUserName())
						.equalsIgnoreCase(userService.findOrganizationByUsername(userRequestModel.getUsername())))
				|| GetCredentials.checkRoleAuthentication() == 2
				|| userRequestModel.getUsername().equals(GetCredentials.checkUserName())) {
			User user = userService.updateById(userId, userRequestModel);
			BeanUtils.copyProperties(user, userResponseModel);
		}
		return ResponseEntity.ok().body(userResponseModel);
	}

	@GetMapping(value = "users/{id}/organizationlogo", produces = MediaType.IMAGE_JPEG_VALUE)
	ResponseEntity<byte[]> getOrganizationLogoByUserId(@PathVariable(value = "id") Long userId)
			throws OrganizationNotFoundException, UserOrganizationNotFoundException, UserNotFoundException, IOException,
			DocumentException {
		String username = userService.findById(userId).getUsername();
		long organizationIdOfRequestedUser = organizationService
				.findByName(userService.findOrganizationByUsername(username)).getId();
		HttpHeaders headers = new HttpHeaders();
		File image = organizationService.getLogoById(organizationIdOfRequestedUser);
		headers.add("Content-Disposition", "inline; filename = organizationIdOfCurrentUser.jpeg");
		return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=" + image.getName())
				.contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(image)))
				.body(Files.readAllBytes(image.toPath()));
	}
}