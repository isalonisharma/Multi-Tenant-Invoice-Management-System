package com.caseStudy.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.caseStudy.dto.OrganizationDTO;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.OrganizationModel;
import com.caseStudy.security.GetCredentials;
import com.caseStudy.service.FileStorageService;
import com.caseStudy.service.OrganizationService;
import com.caseStudy.service.UserService;

@RestController
public class OrganizationController {
	static final Logger logger = Logger.getLogger(OrganizationController.class);

	public OrganizationController() {
	}

	@Autowired
	@Qualifier("organizationService")
	private OrganizationService organizationService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("fileStorageService")
	private FileStorageService fileStorageService;

	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
	@GetMapping("/organizations")
	List<OrganizationDTO> getAllOrganizationModels() {
		logger.info("OrganizationController--->>getAllOrganizationModels--->>Start");

		List<OrganizationDTO> organizationDTOList = new ArrayList<OrganizationDTO>();

		List<OrganizationModel> organizationModelList = organizationService.getAllOrganizationModels();

		for (OrganizationModel organizationModel : organizationModelList) {
			organizationDTOList.add(new OrganizationDTO(organizationModel));
		}

		logger.info("OrganizationController--->>getAllOrganizationModels--->>End");
		return organizationDTOList;
	}

	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/organization/{id}")
	OrganizationDTO getOrganizationModelById(@PathVariable(value = "id") Long organizationId)
			throws OrganizationNotFoundException, UserOrganizationNotFoundException, UserNotFoundException {
		logger.info("OrganizationController--->>getOrganizationModelById--->>Start");

		OrganizationDTO organizationDTO = new OrganizationDTO();

		if (GetCredentials.checkRoleAuthentication() == 2) {
			long organizationIdOfCurrentUser = organizationService
					.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
					.getOrganizationId();

			if (organizationIdOfCurrentUser == organizationId) {
				OrganizationModel organizationModel = organizationService.getOrganizationModelById(organizationId);

				BeanUtils.copyProperties(organizationModel, organizationDTO);
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 1) {
			OrganizationModel organizationModel = organizationService.getOrganizationModelById(organizationId);

			BeanUtils.copyProperties(organizationModel, organizationDTO);
		}

		logger.info("OrganizationController--->>getOrganizationModelById--->>End");
		return organizationDTO;
	}

	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
	@PostMapping("/createorganization")
	OrganizationDTO createOrganizationModel(@Valid @RequestBody OrganizationModel organizationModel) {
		logger.info("OrganizationController--->>createOrganizationModel--->>Start");

		OrganizationDTO organizationDTO = new OrganizationDTO();

		organizationService.createOrganizationModel(organizationModel);

		BeanUtils.copyProperties(organizationModel, organizationDTO);

		logger.info("OrganizationController--->>createOrganizationModel--->>End");
		return organizationDTO;
	}

	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
	@GetMapping("/deleteorganization/{id}")
	ResponseEntity<OrganizationDTO> deleteOrganizationModel(@PathVariable(value = "id") Long organizationId)
			throws OrganizationNotFoundException {
		logger.info("OrganizationController--->>deleteOrganizationModel--->>Start");

		OrganizationDTO organizationDTO = new OrganizationDTO();

		OrganizationModel organizationModel = organizationService.deleteOrganizationModel(organizationId);

		BeanUtils.copyProperties(organizationModel, organizationDTO);

		logger.info("OrganizationController--->>deleteOrganizationModel--->>End");
		return ResponseEntity.ok().body(organizationDTO);
	}

	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@PostMapping("/updateorganization/{id}")
	ResponseEntity<OrganizationDTO> updateOrganizationModel(@PathVariable(value = "id") Long organizationId,
			@Valid @RequestBody OrganizationModel organizationDetails)
			throws OrganizationNotFoundException, UserOrganizationNotFoundException, UserNotFoundException {
		logger.info("OrganizationController--->>updateOrganizationModel--->>Start");

		OrganizationDTO organizationDTO = new OrganizationDTO();

		if (GetCredentials.checkRoleAuthentication() == 2) {
			long organizationIdOfCurrentUser = organizationService
					.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
					.getOrganizationId();

			if (organizationIdOfCurrentUser == organizationId) {
				OrganizationModel organizationModel = organizationService.updateOrganizationModel(organizationId,
						organizationDetails);

				BeanUtils.copyProperties(organizationModel, organizationDTO);
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 1) {
			OrganizationModel organizationModel = organizationService.updateOrganizationModel(organizationId,
					organizationDetails);

			BeanUtils.copyProperties(organizationModel, organizationDTO);
		}

		logger.info("OrganizationController--->>updateOrganizationModel--->>End");
		return ResponseEntity.ok().body(organizationDTO);
	}

	@PreAuthorize("hasRole('ROLE_ORGANIZATION_ADMIN')")
	@PostMapping("/uploadorganiaztionlogo")
	ResponseEntity<OrganizationDTO> uploadOrganizationLogo(@RequestParam("file") MultipartFile file)
			throws OrganizationNotFoundException, UserOrganizationNotFoundException, UserNotFoundException {

		logger.info("ClientController--->>uploadOrganizationLogo--->>Start");
		OrganizationDTO organizationDTO = new OrganizationDTO();

		if (GetCredentials.checkRoleAuthentication() == 2) {

			long organizationIdOfCurrentUser = organizationService
					.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
					.getOrganizationId();

			String[] uploadedFileName = StringUtils.cleanPath(file.getOriginalFilename()).split(Pattern.quote("."));

			OrganizationModel organizationModel = organizationService
					.getOrganizationModelById(organizationIdOfCurrentUser);

			String fileName = fileStorageService.storeFile(file,
					String.valueOf(organizationModel.getOrganizationId()) + "." + uploadedFileName[1]);

			organizationModel.setOrganizationLogo(fileName);

			BeanUtils.copyProperties(organizationModel, organizationDTO);

		}

		logger.info("OrganizationController--->>uploadOrganizationLogo--->>End");
		return ResponseEntity.ok().body(organizationDTO);
	}
}
