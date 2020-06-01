package com.caseStudy.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

import com.caseStudy.dto.UserOrganizationDTO;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.exception.UserRoleNotFoundException;
import com.caseStudy.model.UserOrganizationModel;
import com.caseStudy.service.UserOrganizationService;

@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
@RestController
public class UserOrganizationController {
	static final Logger logger = Logger.getLogger(UserOrganizationController.class);

	public UserOrganizationController() {
	}

	@Autowired
	@Qualifier("userOrganizationService")
	private UserOrganizationService userOrganizationService;

	@GetMapping("/userorganizations")
	List<UserOrganizationDTO> getAllUserOrganizationModel() throws SQLException {
		logger.info("UserOrganizationController--->>getAllUserOrganizationModel--->>Start");

		List<UserOrganizationDTO> userOrganizationDTOList = new ArrayList<UserOrganizationDTO>();

		List<UserOrganizationModel> userOrganizationModelList = userOrganizationService.getAllUserOrganizationModel();

		for (UserOrganizationModel userOrganizationModel : userOrganizationModelList) {
			userOrganizationDTOList.add(new UserOrganizationDTO(userOrganizationModel));
		}

		logger.info("UserOrganizationController--->>getAllUserOrganizationModel--->>Ended");
		return userOrganizationDTOList;
	}

	@GetMapping("/userorganization/{id}")
	UserOrganizationDTO getUserOrganizationModelByUserId(@PathVariable(value = "id") Long userId)
			throws UserRoleNotFoundException, UserOrganizationNotFoundException, UserNotFoundException {
		logger.info("UserOrganizationController--->>getUserOrganizationModelByUserId--->>Start");

		UserOrganizationDTO userOrganizationDTO = new UserOrganizationDTO();

		UserOrganizationModel userOrganizationModel = userOrganizationService.getUserOrganizationModelByUserId(userId);

		BeanUtils.copyProperties(userOrganizationModel, userOrganizationDTO);

		logger.info("UserOrganizationController--->>getUserOrganizationModelByUserId--->>Ended");

		return userOrganizationDTO;
	}

	@PostMapping("/updateuserorganization/{id}")
	ResponseEntity<UserOrganizationDTO> updateUserOrganizationModel(
			@PathVariable(value = "id") Long userOrganizationId,
			@Valid @RequestBody UserOrganizationModel userOrganizationDetails)
			throws UserRoleNotFoundException, UserOrganizationNotFoundException {

		logger.info("UserOrganizationController--->>updateUserOrganizationModel--->>Start");

		UserOrganizationDTO userOrganizationDTO = new UserOrganizationDTO();

		UserOrganizationModel userOrganizationModel = userOrganizationService
				.updateUserOrganizationModel(userOrganizationId, userOrganizationDetails);

		BeanUtils.copyProperties(userOrganizationModel, userOrganizationDTO);

		logger.info("UserOrganizationController--->>updateUserOrganizationModel--->>Ended");

		return ResponseEntity.ok().body(userOrganizationDTO);
	}
}
