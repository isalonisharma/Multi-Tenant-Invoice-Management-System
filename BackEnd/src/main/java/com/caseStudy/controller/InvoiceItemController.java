package com.caseStudy.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.caseStudy.dto.InvoiceItemDTO;
import com.caseStudy.exception.InvoiceItemNotFoundException;
import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.InvoiceOrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.InvoiceItemModel;
import com.caseStudy.security.GetCredentials;
import com.caseStudy.service.InvoiceItemService;
import com.caseStudy.service.InvoiceOrganizationService;
import com.caseStudy.service.InvoiceService;
import com.caseStudy.service.OrganizationService;
import com.caseStudy.service.UserService;

@RestController
public class InvoiceItemController {

	static final Logger logger = Logger.getLogger(InvoiceItemController.class);

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
	@GetMapping("{id}/invoiceitemsbyinvoice")
	ResponseEntity<List<InvoiceItemDTO>> getInvoiceItemByInvoiceId(@PathVariable(value = "id") long invoiceId)
			throws InvoiceItemNotFoundException, InvoiceNotFoundException, UserOrganizationNotFoundException,
			UserNotFoundException, InvoiceOrganizationNotFoundException {

		logger.info("InvoiceItemController--->>getInvoiceItemByInvoiceId--->>Start");

		List<InvoiceItemDTO> invoiceItemDTOList = new ArrayList<InvoiceItemDTO>();

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdofRequestedInvoice = invoiceOrganizationService
				.getInvoiceOrganizationModelByInvoiceId(invoiceId).getOrganizationModel().getOrganizationId();

		if (GetCredentials.checkRoleAuthentication() == 2) {

			if (organizationIdOfCurrentUser == organizationIdofRequestedInvoice) {

				List<InvoiceItemModel> invoiceItemModelList = invoiceItemService.findByinvoiceId(invoiceId);

				for (InvoiceItemModel invoiceItemModel : invoiceItemModelList) {

					invoiceItemDTOList.add(new InvoiceItemDTO(invoiceItemModel));
				}
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 0) {

			String userName = invoiceService.getInvoiceModelById(invoiceId).getUserModel().getUsername();

			if (organizationIdOfCurrentUser == organizationIdofRequestedInvoice
					&& GetCredentials.checkUserName().equalsIgnoreCase(userName)) {

				List<InvoiceItemModel> invoiceItemModelList = invoiceItemService.findByinvoiceId(invoiceId);

				for (InvoiceItemModel invoiceItemModel : invoiceItemModelList) {

					invoiceItemDTOList.add(new InvoiceItemDTO(invoiceItemModel));
				}
			}
		}

		logger.info("InvoiceItemController--->>getInvoiceItemByInvoiceId--->>Ended");
		return ResponseEntity.ok().body(invoiceItemDTOList);
	}
}