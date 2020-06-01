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

import com.caseStudy.dto.RecurringInvoiceDTO;
import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.InvoiceOrganizationNotFoundException;
import com.caseStudy.exception.RecurringInvoiceNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.RecurringInvoiceModel;
import com.caseStudy.security.GetCredentials;
import com.caseStudy.service.InvoiceOrganizationService;
import com.caseStudy.service.InvoiceService;
import com.caseStudy.service.OrganizationService;
import com.caseStudy.service.RecurringInvoiceService;
import com.caseStudy.service.UserService;

@RestController
public class RecurringInvoiceController {
	static final Logger logger = Logger.getLogger(RecurringInvoiceController.class);

	@Autowired
	@Qualifier("recurrringInvoiceService")
	private RecurringInvoiceService recurringInvoiceService;

	@Autowired
	@Qualifier("invoiceOrganizationService")
	private InvoiceOrganizationService invoiceOrganizationService;

	@Autowired
	@Qualifier("invoiceService")
	private InvoiceService invoiceService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("organizationService")
	private OrganizationService organizationService;

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@PostMapping("/updaterecurringinvoice/{id}")
	ResponseEntity<RecurringInvoiceDTO> updateRecurringInvoice(
			@PathVariable(value = "id") Long recurringInvoiceId,
			@Valid @RequestBody RecurringInvoiceModel recurringInvoiceDetails)
			throws RecurringInvoiceNotFoundException, InvoiceNotFoundException, UserOrganizationNotFoundException,
			UserNotFoundException, InvoiceOrganizationNotFoundException {

		logger.info("RecurringInvoiceController--->>updateRecurringInvoiceModel--->>Start");

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdOfRequestedInvoice = invoiceOrganizationService
				.getInvoiceOrganizationModelByInvoiceId(recurringInvoiceDetails.getInvoiceModel().getInvoiceId())
				.getOrganizationModel().getOrganizationId();

		String dateFormat = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationDate();

		RecurringInvoiceDTO recurringInvoiceDTO = new RecurringInvoiceDTO();

		if (GetCredentials.checkRoleAuthentication() == 2) {

			if (organizationIdOfCurrentUser == organizationIdOfRequestedInvoice) {

				RecurringInvoiceModel recurringInvoiceModel = recurringInvoiceService
						.updateRecurringInvoiceModel(recurringInvoiceId, recurringInvoiceDetails);

				recurringInvoiceDTO = recurringInvoiceService
						.RecurringInvoiceModelToRecurringInvoiceDTO(recurringInvoiceModel, dateFormat);
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 0) {

			String userName = invoiceService
					.getInvoiceModelById(recurringInvoiceDetails.getInvoiceModel().getInvoiceId()).getUserModel()
					.getUsername();

			if (organizationIdOfCurrentUser == organizationIdOfRequestedInvoice
					&& GetCredentials.checkUserName().equalsIgnoreCase(userName)) {

				RecurringInvoiceModel recurringInvoiceModel = recurringInvoiceService
						.updateRecurringInvoiceModel(recurringInvoiceId, recurringInvoiceDetails);

				recurringInvoiceDTO = recurringInvoiceService
						.RecurringInvoiceModelToRecurringInvoiceDTO(recurringInvoiceModel, dateFormat);
			}
		}

		logger.info("RecurringInvoiceController--->>updateRecurringInvoiceModel--->>Ended");

		return ResponseEntity.ok().body(recurringInvoiceDTO);
	}
}
