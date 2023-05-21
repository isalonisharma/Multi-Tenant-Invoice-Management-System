package com.case_study.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.case_study.entity.InvoiceItem;
import com.case_study.exception.InvoiceNotFoundException;
import com.case_study.exception.InvoiceOrganizationNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.model.InvoiceItemModel;
import com.case_study.security.GetCredentials;
import com.case_study.service.InvoiceItemService;
import com.case_study.service.InvoiceOrganizationService;
import com.case_study.service.InvoiceService;
import com.case_study.service.OrganizationService;
import com.case_study.service.UserService;

@RestController
public class InvoiceItemController {
	@Autowired
	@Qualifier("invoiceItemService")
	private InvoiceItemService invoiceItemService;

	@Autowired
	@Qualifier("invoiceOrganizationService")
	private InvoiceOrganizationService invoiceOrganizationService;

	@Autowired
	@Qualifier("invoiceService")
	private InvoiceService invoiceService;

	@Autowired
	@Qualifier("organizationService")
	private OrganizationService organizationService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("invoice/{id}/items")
	ResponseEntity<List<InvoiceItemModel>> findByInvoiceId(@PathVariable(value = "id") long invoiceId)
			throws InvoiceNotFoundException, UserOrganizationNotFoundException, UserNotFoundException,
			InvoiceOrganizationNotFoundException {
		List<InvoiceItemModel> invoiceItemModels = new ArrayList<>();
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdofRequestedInvoice = invoiceOrganizationService.findByInvoiceId(invoiceId).getOrganization()
				.getId();
		if (GetCredentials.checkRoleAuthentication() == 2
				&& organizationIdOfCurrentUser == organizationIdofRequestedInvoice) {
			List<InvoiceItem> invoiceItems = invoiceItemService.findByInvoiceId(invoiceId);
			for (InvoiceItem invoiceItem : invoiceItems) {
				invoiceItemModels.add(new InvoiceItemModel(invoiceItem));
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 0) {
			String userName = invoiceService.findById(invoiceId).getUser().getUsername();
			if (organizationIdOfCurrentUser == organizationIdofRequestedInvoice
					&& GetCredentials.checkUserName().equalsIgnoreCase(userName)) {
				List<InvoiceItem> invoiceItems = invoiceItemService.findByInvoiceId(invoiceId);
				for (InvoiceItem invoiceItem : invoiceItems) {
					invoiceItemModels.add(new InvoiceItemModel(invoiceItem));
				}
			}
		}
		return ResponseEntity.ok().body(invoiceItemModels);
	}
}