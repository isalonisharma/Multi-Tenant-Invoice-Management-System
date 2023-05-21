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

import com.case_study.entity.RegularInvoice;
import com.case_study.exception.InvoiceNotFoundException;
import com.case_study.exception.InvoiceOrganizationNotFoundException;
import com.case_study.exception.RegularInvoiceNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.model.RegularInvoiceModel;
import com.case_study.security.GetCredentials;
import com.case_study.service.InvoiceOrganizationService;
import com.case_study.service.InvoiceService;
import com.case_study.service.OrganizationService;
import com.case_study.service.RegularInvoiceService;
import com.case_study.service.UserService;

@RestController
public class RegularInvoiceController {
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
	@PutMapping("regularinvoices/{id}")
	ResponseEntity<RegularInvoiceModel> updateRegularInvoice(@PathVariable(value = "id") Long id,
			@Valid @RequestBody RegularInvoiceModel requestRegularInvoiceModel)
			throws RegularInvoiceNotFoundException, UserOrganizationNotFoundException, UserNotFoundException,
			InvoiceOrganizationNotFoundException, InvoiceNotFoundException {
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdOfRequestedInvoice = invoiceOrganizationService
				.findByInvoiceId(requestRegularInvoiceModel.getInvoiceId()).getOrganization().getId();
		String dateFormat = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getDateFormat();
		RegularInvoiceModel updatedRegularInvoiceModel = new RegularInvoiceModel();
		if (GetCredentials.checkRoleAuthentication() == 2
				&& organizationIdOfCurrentUser == organizationIdOfRequestedInvoice) {
			RegularInvoice regularInvoice = regularInvoiceService.updateById(id, requestRegularInvoiceModel);
			updatedRegularInvoiceModel = regularInvoiceService.convertToModel(regularInvoice, dateFormat);
		} else if (GetCredentials.checkRoleAuthentication() == 0) {
			String userName = invoiceService.findById(requestRegularInvoiceModel.getInvoiceId()).getUser()
					.getUsername();
			if (organizationIdOfCurrentUser == organizationIdOfRequestedInvoice
					&& GetCredentials.checkUserName().equalsIgnoreCase(userName)) {
				RegularInvoice regularInvoice = regularInvoiceService.updateById(id, requestRegularInvoiceModel);
				updatedRegularInvoiceModel = regularInvoiceService.convertToModel(regularInvoice, dateFormat);
			}
		}
		return ResponseEntity.ok().body(updatedRegularInvoiceModel);
	}
}