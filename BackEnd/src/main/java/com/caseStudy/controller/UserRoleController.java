package com.caseStudy.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.caseStudy.dto.UserRoleDTO;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserRoleNotFoundException;
import com.caseStudy.model.RoleModel;
import com.caseStudy.model.UserRoleModel;
import com.caseStudy.service.UserRoleService;

@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
@RestController
public class UserRoleController {
	static final Logger logger = Logger.getLogger(UserRoleController.class);

	@Autowired
	@Qualifier("userRoleService")
	private UserRoleService userRoleService;

	@GetMapping("/userroles")
	List<UserRoleDTO> getAllUserRoleModel() throws SQLException {
		logger.info("UserRoleController--->>getAllUserRoleModel--->>Start");

		List<UserRoleDTO> userRoleDTOList = new ArrayList<UserRoleDTO>();

		List<UserRoleModel> userRoleModelList = userRoleService.getAllUserRoleModel();

		for (UserRoleModel userRoleModel : userRoleModelList) {
			userRoleDTOList.add(new UserRoleDTO(userRoleModel));
		}

		logger.info("UserRoleController--->>getAllUserRoleModel--->>Ended");

		return userRoleDTOList;
	}

	@GetMapping("/userrole/{id}")
	Set<RoleModel> getUserRoleModelByUserId(@PathVariable(value = "id") Long userId)
			throws UserRoleNotFoundException, UserNotFoundException {
		logger.info("UserRoleController--->>getUserRoleModelByUserId--->>Start");

		Set<RoleModel> uniqueRoleSet = new HashSet<RoleModel>();

		List<UserRoleModel> userRoleModelList = userRoleService.getUserRoleModelByUserId(userId);

		List<RoleModel> roleModelList = new ArrayList<RoleModel>();

		for (UserRoleModel userRoleModel : userRoleModelList) {
			roleModelList.add(userRoleModel.getRoleModel());
		}

		for (RoleModel roleModel : roleModelList) {
			uniqueRoleSet.add(roleModel);
		}

		logger.info("UserRoleController--->>getUserRoleModelByUserId--->>Ended");
		return uniqueRoleSet;
	}

	@PostMapping("/updateuserrole/{id}")
	ResponseEntity<UserRoleDTO> updateUserRoleModel(@PathVariable(value = "id") Long userRoleId,
			@Valid @RequestBody UserRoleModel userRoleDetails) throws UserRoleNotFoundException {

		logger.info("UserRoleController--->>updateUserRoleModel--->>Start");

		UserRoleDTO userRoleDTO = new UserRoleDTO();

		UserRoleModel userRolemodel = userRoleService.updateUserRoleModel(userRoleId, userRoleDetails);

		BeanUtils.copyProperties(userRolemodel, userRoleDTO);

		logger.info("UserRoleController--->>updateUserRoleModel--->>Ended");
		return ResponseEntity.ok().body(userRoleDTO);
	}
}
