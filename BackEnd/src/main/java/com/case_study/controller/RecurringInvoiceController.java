package com.case_study.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.case_study.entity.RecurringInvoice;
import com.case_study.exception.InvoiceNotFoundException;
import com.case_study.exception.InvoiceOrganizationNotFoundException;
import com.case_study.exception.RecurringInvoiceNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.model.RecurringInvoiceModel;
import com.case_study.security.GetCredentials;
import com.case_study.service.InvoiceOrganizationService;
import com.case_study.service.InvoiceService;
import com.case_study.service.OrganizationService;
import com.case_study.service.RecurringInvoiceService;
import com.case_study.service.UserService;

@RestController
public class RecurringInvoiceController {
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
	@PutMapping("recurringinvoices/{id}")
	ResponseEntity<RecurringInvoiceModel> updateRecurringInvoice(@PathVariable(value = "id") Long id,
			@Valid @RequestBody RecurringInvoiceModel requestRecurringInvoiceModel)
			throws RecurringInvoiceNotFoundException, InvoiceNotFoundException, UserOrganizationNotFoundException,
			UserNotFoundException, InvoiceOrganizationNotFoundException {
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdOfRequestedInvoice = invoiceOrganizationService
				.findByInvoiceId(requestRecurringInvoiceModel.getInvoiceId()).getOrganization().getId();
		String dateFormat = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getDateFormat();
		RecurringInvoiceModel recurringInvoiceModel = new RecurringInvoiceModel();
		if (GetCredentials.checkRoleAuthentication() == 2
				&& organizationIdOfCurrentUser == organizationIdOfRequestedInvoice) {
			RecurringInvoice recurringInvoice = recurringInvoiceService.updateById(id, requestRecurringInvoiceModel);
			recurringInvoiceModel = recurringInvoiceService.convertToModel(recurringInvoice, dateFormat);
		} else if (GetCredentials.checkRoleAuthentication() == 0) {
			String userName = invoiceService.findById(requestRecurringInvoiceModel.getInvoiceId()).getUser()
					.getUsername();
			if (organizationIdOfCurrentUser == organizationIdOfRequestedInvoice
					&& GetCredentials.checkUserName().equalsIgnoreCase(userName)) {
				RecurringInvoice recurringInvoice = recurringInvoiceService.updateById(id,
						requestRecurringInvoiceModel);
				recurringInvoiceModel = recurringInvoiceService.convertToModel(recurringInvoice, dateFormat);
			}
		}
		return ResponseEntity.ok().body(recurringInvoiceModel);
	}
}