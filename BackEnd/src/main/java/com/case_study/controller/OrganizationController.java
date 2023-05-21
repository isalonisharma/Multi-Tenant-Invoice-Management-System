package com.case_study.controller;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.case_study.entity.Organization;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.model.OrganizationModel;
import com.case_study.security.GetCredentials;
import com.case_study.service.FileStorageService;
import com.case_study.service.OrganizationService;
import com.case_study.service.UserService;

@RestController
public class OrganizationController {
	static final Logger logger = Logger.getLogger(OrganizationController.class);

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
	@GetMapping("organizations")
	ResponseEntity<List<OrganizationModel>> getActiveOrganizations() {
		List<OrganizationModel> organizationModels = new ArrayList<>();
		List<Organization> organizations = organizationService.getActiveOrganizations();
		for (Organization organization : organizations) {
			organizationModels.add(new OrganizationModel(organization));
		}
		return ResponseEntity.ok().body(organizationModels);
	}

	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("organizations/{id}")
	ResponseEntity<OrganizationModel> findById(@PathVariable(value = "id") Long id)
			throws OrganizationNotFoundException, UserOrganizationNotFoundException, UserNotFoundException {
		OrganizationModel organizationModel = new OrganizationModel();
		if (GetCredentials.checkRoleAuthentication() == 2) {
			long organizationIdOfCurrentUser = organizationService
					.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
			if (organizationIdOfCurrentUser == id) {
				Organization organization = organizationService.findById(id);
				BeanUtils.copyProperties(organization, organizationModel);
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 1) {
			Organization organization = organizationService.findById(id);
			BeanUtils.copyProperties(organization, organizationModel);
		}
		return ResponseEntity.ok().body(organizationModel);
	}

	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
	@PostMapping("organizations")
	ResponseEntity<OrganizationModel> createOrganization(
			@Valid @RequestBody OrganizationModel requestOrganizationModel) {
		OrganizationModel organizationModel = new OrganizationModel();
		Organization createdOrganization = organizationService.createOrganization(requestOrganizationModel);
		BeanUtils.copyProperties(createdOrganization, organizationModel);
		return ResponseEntity.ok().body(organizationModel);
	}

	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
	@DeleteMapping("organizations/{id}")
	ResponseEntity<OrganizationModel> deleteById(@PathVariable(value = "id") Long id)
			throws OrganizationNotFoundException {
		OrganizationModel organizationModel = new OrganizationModel();
		Organization organization = organizationService.deleteById(id);
		BeanUtils.copyProperties(organization, organizationModel);
		return ResponseEntity.ok().body(organizationModel);
	}

	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@PutMapping("organizations/{id}")
	ResponseEntity<OrganizationModel> updateById(@PathVariable(value = "id") Long id,
			@Valid @RequestBody OrganizationModel requestOrganizationModel)
			throws OrganizationNotFoundException, UserOrganizationNotFoundException, UserNotFoundException {
		OrganizationModel organizationModel = new OrganizationModel();
		if (GetCredentials.checkRoleAuthentication() == 2) {
			long organizationIdOfCurrentUser = organizationService
					.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
			if (organizationIdOfCurrentUser == id) {
				Organization organization = organizationService.updateById(id, requestOrganizationModel);
				BeanUtils.copyProperties(organization, organizationModel);
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 1) {
			Organization organization = organizationService.updateById(id, requestOrganizationModel);
			BeanUtils.copyProperties(organization, organizationModel);
		}
		return ResponseEntity.ok().body(organizationModel);
	}

	@PreAuthorize("hasRole('ROLE_ORGANIZATION_ADMIN')")
	@PutMapping("organizations/current/upload/logo")
	ResponseEntity<OrganizationModel> uploadOrganizationLogo(@RequestParam("file") MultipartFile file)
			throws OrganizationNotFoundException, UserOrganizationNotFoundException, UserNotFoundException {
		OrganizationModel organizationModel = new OrganizationModel();
		if (GetCredentials.checkRoleAuthentication() == 2) {
			long organizationIdOfCurrentUser = organizationService
					.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
			String[] uploadedFileName = StringUtils.cleanPath(file.getOriginalFilename()).split(Pattern.quote("."));
			Organization organization = organizationService.findById(organizationIdOfCurrentUser);
			String fileName = fileStorageService.storeFile(file,
					String.valueOf(organization.getId()) + "." + uploadedFileName[1]);
			organization.setLogo(fileName);
			BeanUtils.copyProperties(organization, organizationModel);
		}
		return ResponseEntity.ok().body(organizationModel);
	}
}