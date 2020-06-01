package com.caseStudy.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.caseStudy.dto.RegularInvoiceDTO;
import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.InvoiceOrganizationNotFoundException;
import com.caseStudy.exception.RegularInvoiceNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.RegularInvoiceModel;
import com.caseStudy.security.GetCredentials;
import com.caseStudy.service.InvoiceOrganizationService;
import com.caseStudy.service.InvoiceService;
import com.caseStudy.service.OrganizationService;
import com.caseStudy.service.RegularInvoiceService;
import com.caseStudy.service.UserService;

@RestController
public class RegularInvoiceController {
	static final Logger logger = Logger.getLogger(RegularInvoiceController.class);

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("organizationService")
	private OrganizationService organizationService;

	@Autowired
	@Qualifier("regularInvoiceService")
	private RegularInvoiceService regularInvoiceService;

	@Autowired
	@Qualifier("invoiceOrganizationService")
	private InvoiceOrganizationService invoiceOrganizationService;

	@Autowired
	@Qualifier("invoiceService")
	private InvoiceService invoiceService;

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@PostMapping("/updateregularinvoice/{id}")
	ResponseEntity<RegularInvoiceDTO> updateRegularInvoiceModel(
			@PathVariable(value = "id") Long regularInvoiceId,
			@Valid @RequestBody RegularInvoiceModel regularInvoiceDetails)
			throws RegularInvoiceNotFoundException, UserOrganizationNotFoundException, UserNotFoundException,
			InvoiceOrganizationNotFoundException, InvoiceNotFoundException {

		logger.info("RegularInvoiceController--->>updateRegularInvoiceModel--->>Start");

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdOfRequestedInvoice = invoiceOrganizationService
				.getInvoiceOrganizationModelByInvoiceId(regularInvoiceDetails.getInvoiceModel().getInvoiceId())
				.getOrganizationModel().getOrganizationId();

		String dateFormat = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationDate();

		RegularInvoiceDTO regularInvoiceDTO = new RegularInvoiceDTO();

		if (GetCredentials.checkRoleAuthentication() == 2) {

			if (organizationIdOfCurrentUser == organizationIdOfRequestedInvoice) {

				RegularInvoiceModel regularInvoiceModel = regularInvoiceService
						.updateRegularInvoiceModel(regularInvoiceId, regularInvoiceDetails);

				regularInvoiceDTO = regularInvoiceService.RegularInvoiceModelToRegularInvoiceDTO(regularInvoiceModel,
						dateFormat);
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 0) {

			String userName = invoiceService.getInvoiceModelById(regularInvoiceDetails.getInvoiceModel().getInvoiceId())
					.getUserModel().getUsername();

			if (organizationIdOfCurrentUser == organizationIdOfRequestedInvoice
					&& GetCredentials.checkUserName().equalsIgnoreCase(userName)) {

				RegularInvoiceModel regularInvoiceModel = regularInvoiceService
						.updateRegularInvoiceModel(regularInvoiceId, regularInvoiceDetails);

				regularInvoiceDTO = regularInvoiceService.RegularInvoiceModelToRegularInvoiceDTO(regularInvoiceModel,
						dateFormat);
			}
		}
		logger.info("RegularInvoiceController--->>updateRegularInvoiceModel--->>Ended");

		return ResponseEntity.ok().body(regularInvoiceDTO);
	}
}