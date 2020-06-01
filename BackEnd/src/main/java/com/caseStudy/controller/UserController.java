package com.caseStudy.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.activation.FileTypeMap;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.caseStudy.bean.CreateUserBean;
import com.caseStudy.dto.UserDTO;
import com.caseStudy.exception.IllegalTryToAccessException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.RoleNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.exception.UserRoleNotFoundException;
import com.caseStudy.model.RoleModel;
import com.caseStudy.model.UserModel;
import com.caseStudy.security.GetCredentials;
import com.caseStudy.service.OrganizationService;
import com.caseStudy.service.RoleService;
import com.caseStudy.service.UserService;
import com.itextpdf.text.DocumentException;

@RestController
public class UserController {
	static final Logger logger = Logger.getLogger(UserController.class);

	UserController() {
	}

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
	@GetMapping("/users")
	List<UserDTO> getAllUserModelsByOrganizationId()
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException {
		logger.info("UserController--->>getAllUserModelsByOrganizationId--->>Start");

		List<UserDTO> userDTOList = new ArrayList<UserDTO>();

		if (GetCredentials.checkRoleAuthentication() == 1) {

			List<UserModel> userModelList = userService.getAllUserModels();

			for (UserModel userModel : userModelList) {
				userDTOList.add(new UserDTO(userModel));
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 2) {
			long organizationIdOfCurrentUser = organizationService
					.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
					.getOrganizationId();

			List<UserModel> userModelList = userService.getAllUserModelsByOrganizationId(organizationIdOfCurrentUser);

			for (UserModel userModel : userModelList) {
				userDTOList.add(new UserDTO(userModel));
			}
		}

		logger.info("UserController--->>getAllUserModelsByOrganizationId--->>Ended");
		return userDTOList;
	}

	@GetMapping("/user/{id}")
	ResponseEntity<UserDTO> getUserModelById(@PathVariable(value = "id") Long userId)
			throws UserNotFoundException, UserOrganizationNotFoundException {

		logger.info("UserController--->>getUserModelById--->>Start");

		UserDTO userDTO = new UserDTO();

		UserModel userModel = userService.getUserModelById(userId);

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdofRequestedUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(userModel.getUsername()))
				.getOrganizationId();

		if (GetCredentials.checkRoleAuthentication() == 1) {

			BeanUtils.copyProperties(userModel, userDTO);

		} else if (GetCredentials.checkRoleAuthentication() == 2) {
			if (organizationIdOfCurrentUser == organizationIdofRequestedUser) {

				BeanUtils.copyProperties(userModel, userDTO);
			}
		} else {
			if (organizationIdOfCurrentUser == organizationIdofRequestedUser
					&& GetCredentials.checkUserName().equalsIgnoreCase(userModel.getUsername())) {

				BeanUtils.copyProperties(userModel, userDTO);
			}
		}
		logger.info("UserController--->>getUserModelById--->>Ended");
		return ResponseEntity.ok().body(userDTO);

	}

	@PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@PostMapping("/createuser")
	UserDTO createUserModel(@Valid @RequestBody CreateUserBean createUserBean) throws BeansException,
			UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserRoleNotFoundException, RoleNotFoundException, IllegalTryToAccessException {

		logger.info("UserController--->>createUserModel--->>Start");

		RoleModel roleModel = roleService.findByRole(createUserBean.getRole());

		UserDTO userDTO = new UserDTO();

		if (GetCredentials.checkRoleAuthentication() == 1) {

			if (roleModel.getRole().equalsIgnoreCase("ROLE_ORGANIZATION_ADMIN")) {
				BeanUtils.copyProperties(userService.createUserModel(createUserBean, createUserBean.getOrganizationId(),
						roleModel.getRoleId()), userDTO);
			} else {
				throw new IllegalTryToAccessException("Super Admin is only allowed to create organization admins.");
			}

		} else if (GetCredentials.checkRoleAuthentication() == 2) {

			long organizationIdOfCurrentUser = organizationService
					.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
					.getOrganizationId();

			if (roleModel.getRole().equalsIgnoreCase("ROLE_USER")) {
				BeanUtils.copyProperties(
						userService.createUserModel(createUserBean, organizationIdOfCurrentUser, roleModel.getRoleId()),
						userDTO);
			} else {
				throw new IllegalTryToAccessException("Organization Admin is only allowed to create user role.");
			}
		}

		logger.info("UserController--->>createUserModel--->>Ended");

		return userDTO;
	}

	@PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/deleteuser/{id}")
	ResponseEntity<UserDTO> deleteUser(@PathVariable(value = "id") Long userId)
			throws UserNotFoundException, BeansException, UserOrganizationNotFoundException {

		logger.info("UserController--->>deleteUserModel--->>Start");

		UserDTO userDTO = new UserDTO();

		if (GetCredentials.checkRoleAuthentication() == 2
				&& userService.findOrganizationByUsername(GetCredentials.checkUserName())
						.equalsIgnoreCase(organizationService
								.getOrganizationModelByName(
										userService.findOrganizationByUsername(GetCredentials.checkUserName()))
								.getOrganizationName())) {

			UserModel userModel = userService.deleteUserModel(userId);

			BeanUtils.copyProperties(userModel, userDTO);
		}

		if (GetCredentials.checkRoleAuthentication() == 1) {

			UserModel userModel = userService.deleteUserModel(userId);

			BeanUtils.copyProperties(userModel, userDTO);
		}

		logger.info("UserController--->>deleteUserModel--->>Ended");

		return ResponseEntity.ok().body(userDTO);
	}

	@PostMapping("/updateuser/{id}")
	ResponseEntity<UserDTO> updateUserModel(@PathVariable(value = "id") Long userId,
			@Valid @RequestBody UserModel userDetails) throws UserNotFoundException, IllegalTryToAccessException,
			BeansException, UserOrganizationNotFoundException {

		logger.info("UserController--->>updateUserModel--->>Start");

		UserDTO userDTO = new UserDTO();

		if (GetCredentials.checkRoleAuthentication() == 1
				&& userService.findOrganizationByUsername(GetCredentials.checkUserName())
						.equalsIgnoreCase(userService.findOrganizationByUsername(userDetails.getUsername()))) {

			UserModel userModel = userService.updateUserModel(userId, userDetails);

			BeanUtils.copyProperties(userModel, userDTO);

		} else if (GetCredentials.checkRoleAuthentication() == 2) {

			UserModel userModel = userService.updateUserModel(userId, userDetails);

			BeanUtils.copyProperties(userModel, userDTO);

		} else if (userDetails.getUsername().equals(GetCredentials.checkUserName())) {

			UserModel userModel = userService.updateUserModel(userId, userDetails);

			BeanUtils.copyProperties(userModel, userDTO);
		}

		logger.info("UserController--->>updateUserModel--->>Ended");

		return ResponseEntity.ok().body(userDTO);
	}

	@GetMapping(value = "/organizationlogo/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	ResponseEntity<byte[]> getOrganizationLogo(@PathVariable(value = "id") Long userId)
			throws OrganizationNotFoundException, UserOrganizationNotFoundException, UserNotFoundException, IOException,
			DocumentException {

		logger.info("UserController--->>getOrganizationLogo--->>Started");
		
		String username  = userService.getUserModelById(userId).getUsername();

		long organizationIdOfRequestedUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(username))
				.getOrganizationId();
		
		HttpHeaders headers = new HttpHeaders();

		File image = organizationService.getOrganizationLogo(organizationIdOfRequestedUser);

		headers.add("Content-Disposition", "inline; filename = organizationIdOfCurrentUser.jpeg");

		logger.info("UserController--->>getOrganizationLogo--->>Ended");

		return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=" + image.getName())
				.contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(image)))
				.body(Files.readAllBytes(image.toPath()));
	}
}
