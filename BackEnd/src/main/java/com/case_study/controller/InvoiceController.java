package com.case_study.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.case_study.bean.CreateInvoiceBean;
import com.case_study.bean.CreateInvoiceOrganizationBean;
import com.case_study.bean.UpdateInvoiceBean;
import com.case_study.entity.Invoice;
import com.case_study.enumeration.PaymentStatus;
import com.case_study.exception.ClientNotFoundException;
import com.case_study.exception.ClientOrganizationNotFoundException;
import com.case_study.exception.InvoiceItemNotFoundException;
import com.case_study.exception.InvoiceNotFoundException;
import com.case_study.exception.InvoiceOrganizationNotFoundException;
import com.case_study.exception.ItemNotFoundException;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.exception.RecurringInvoiceNotFoundException;
import com.case_study.exception.RegularInvoiceNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.model.InvoiceModel;
import com.case_study.security.GetCredentials;
import com.case_study.service.ClientOrganizationService;
import com.case_study.service.InvoiceOrganizationService;
import com.case_study.service.InvoiceService;
import com.case_study.service.OrganizationService;
import com.case_study.service.UserOrganizationService;
import com.case_study.service.UserService;
import com.itextpdf.text.DocumentException;

@RestController
public class InvoiceController {
	@Autowired
	@Qualifier("invoiceService")
	private InvoiceService invoiceService;

	@Autowired
	@Qualifier("invoiceOrganizationService")
	private InvoiceOrganizationService invoiceOrganizationService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("organizationService")
	private OrganizationService organizationService;

	@Autowired
	@Qualifier("userOrganizationService")
	UserOrganizationService userOrganizationService;

	@Autowired
	@Qualifier("clientOrganizationService")
	ClientOrganizationService clientOrganizationService;

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@PostMapping("invoices")
	ResponseEntity<InvoiceModel> createInvoice(@Valid @RequestBody CreateInvoiceBean createInvoiceBean)
			throws UserNotFoundException, ClientNotFoundException, ItemNotFoundException, InvoiceNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {
		InvoiceModel invoiceModel = null;
		if (createInvoiceBean.getPaymentStatus() != PaymentStatus.PAYMENT_DUE) {
			Invoice invoice = invoiceService.createInvoice(createInvoiceBean);
			long organizationIdOfCurrentUser = organizationService
					.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
			String dateFormat = organizationService
					.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getDateFormat();
			invoiceOrganizationService.createInvoiceOrganization(
					new CreateInvoiceOrganizationBean(invoice.getId(), organizationIdOfCurrentUser));
			invoiceModel = new InvoiceModel(invoice, dateFormat);
		}
		return ResponseEntity.ok().body(invoiceModel);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@PutMapping("invoices")
	ResponseEntity<InvoiceModel> updateInvoice(@Valid @RequestBody UpdateInvoiceBean updateInvoiceBean)
			throws InvoiceNotFoundException, UserNotFoundException, ClientNotFoundException,
			InvoiceItemNotFoundException, ItemNotFoundException, RecurringInvoiceNotFoundException,
			RegularInvoiceNotFoundException, UserOrganizationNotFoundException, InvoiceOrganizationNotFoundException {
		InvoiceModel invoiceModel = null;
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdofRequestedUser = invoiceOrganizationService
				.findByInvoiceId(updateInvoiceBean.getId()).getOrganization().getId();
		String dateFormat = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getDateFormat();
		if (GetCredentials.checkRoleAuthentication() == 2
				&& organizationIdOfCurrentUser == organizationIdofRequestedUser
				&& updateInvoiceBean.getPaymentStatus() == PaymentStatus.PAYMENT_DRAFT) {
			Invoice invoice = invoiceService.updateInvoice(updateInvoiceBean);
			invoiceModel = new InvoiceModel(invoice, dateFormat);
		} else if (GetCredentials.checkRoleAuthentication() == 0) {
			String userName = invoiceService.findById(updateInvoiceBean.getId()).getUser().getUsername();
			if (organizationIdOfCurrentUser == organizationIdofRequestedUser
					&& GetCredentials.checkUserName().equalsIgnoreCase(userName)
					&& updateInvoiceBean.getPaymentStatus() == PaymentStatus.PAYMENT_DRAFT) {
				Invoice invoice = invoiceService.updateInvoice(updateInvoiceBean);
				invoiceModel = new InvoiceModel(invoice, dateFormat);
			}
		}
		return ResponseEntity.ok().body(invoiceModel);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("invoices")
	ResponseEntity<List<InvoiceModel>> getAllInvoices()
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException {
		List<InvoiceModel> invoiceModels = null;
		String dateFormat = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getDateFormat();
		if (GetCredentials.checkRoleAuthentication() == 2) {
			long organizationIdOfCurrentUser = organizationService
					.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
			List<Invoice> invoices = invoiceService.findByOrganizationId(organizationIdOfCurrentUser);
			invoiceModels = invoiceService.convertToInvoiceModels(invoices, dateFormat);
		} else if (GetCredentials.checkRoleAuthentication() == 0) {
			long userId = userService.findByUsername(GetCredentials.checkUserName()).getId();
			List<Invoice> invoices = invoiceService.findByUserId(userId);
			invoiceModels = invoiceService.convertToInvoiceModels(invoices, dateFormat);
		}
		return ResponseEntity.ok().body(invoiceModels);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("invoices/{id}")
	ResponseEntity<InvoiceModel> findById(@PathVariable(value = "id") Long id) throws InvoiceNotFoundException,
			InvoiceOrganizationNotFoundException, UserOrganizationNotFoundException, UserNotFoundException {
		InvoiceModel invoiceModel = null;
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdofRequestedUser = invoiceOrganizationService.findByInvoiceId(id).getOrganization().getId();
		String dateFormat = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getDateFormat();
		if (GetCredentials.checkRoleAuthentication() == 2
				&& organizationIdOfCurrentUser == organizationIdofRequestedUser) {
			Invoice invoice = invoiceService.findById(id);
			invoiceModel = new InvoiceModel(invoice, dateFormat);
		}
		if (GetCredentials.checkRoleAuthentication() == 0) {
			String userName = invoiceService.findById(id).getUser().getUsername();
			if (organizationIdOfCurrentUser == organizationIdofRequestedUser
					&& GetCredentials.checkUserName().equalsIgnoreCase(userName)) {
				Invoice invoice = invoiceService.findById(id);
				invoiceModel = new InvoiceModel(invoice, dateFormat);
			}
		}
		return ResponseEntity.ok().body(invoiceModel);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("users/{id}/invoices")
	ResponseEntity<List<InvoiceModel>> findByUserId(@PathVariable(value = "id") long userId)
			throws UserNotFoundException, UserOrganizationNotFoundException {
		List<InvoiceModel> invoiceModels = null;
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdofRequestedUser = userOrganizationService.findByUserId(userId).getOrganization().getId();
		String dateFormat = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getDateFormat();
		if ((GetCredentials.checkRoleAuthentication() == 2
				&& organizationIdOfCurrentUser == organizationIdofRequestedUser)
				|| (GetCredentials.checkRoleAuthentication() == 0
						&& userService.findByUsername(GetCredentials.checkUserName()).getId() == userId)) {
			List<Invoice> invoices = invoiceService.findByUserId(userId);
			invoiceModels = invoiceService.convertToInvoiceModels(invoices, dateFormat);
		}
		return ResponseEntity.ok().body(invoiceModels);
	}

	@PreAuthorize("hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("clients/{id}/invoices")
	ResponseEntity<List<InvoiceModel>> findByClientId(@PathVariable(value = "id") long clientId)
			throws ClientNotFoundException, ClientOrganizationNotFoundException, UserOrganizationNotFoundException,
			UserNotFoundException {
		List<InvoiceModel> invoiceModels = null;
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdofRequestedClient = clientOrganizationService.findByClientId(clientId).getOrganization()
				.getId();
		String dateFormat = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getDateFormat();
		if (GetCredentials.checkRoleAuthentication() == 2
				&& organizationIdOfCurrentUser == organizationIdofRequestedClient) {
			List<Invoice> invoices = invoiceService.findByClientId(clientId);
			invoiceModels = invoiceService.convertToInvoiceModels(invoices, dateFormat);
		}
		return ResponseEntity.ok().body(invoiceModels);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("users/{id}/recurringinvoices")
	ResponseEntity<List<InvoiceModel>> findRecurringInvoicesByUserId(@PathVariable(value = "id") long userId)
			throws UserNotFoundException, UserOrganizationNotFoundException {
		List<InvoiceModel> invoiceModels = null;
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdofRequestedUser = userOrganizationService.findByUserId(userId).getOrganization().getId();
		String dateFormat = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getDateFormat();
		if ((GetCredentials.checkRoleAuthentication() == 2
				&& organizationIdOfCurrentUser == organizationIdofRequestedUser)
				|| (GetCredentials.checkRoleAuthentication() == 0
						&& userService.findByUsername(GetCredentials.checkUserName()).getId() == userId)) {
			List<Invoice> invoices = invoiceService.findRecurringInvoicesByUserId(userId);
			invoiceModels = invoiceService.convertToInvoiceModels(invoices, dateFormat);
		}
		return ResponseEntity.ok().body(invoiceModels);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("users/{id}/regularinvoices")
	ResponseEntity<List<InvoiceModel>> findRegularInvoicesByUserId(@PathVariable(value = "id") long userId)
			throws UserNotFoundException, UserOrganizationNotFoundException {
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdofRequestedUser = userOrganizationService.findByUserId(userId).getOrganization().getId();
		String dateFormat = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getDateFormat();
		List<InvoiceModel> invoiceModels = null;
		if ((GetCredentials.checkRoleAuthentication() == 2
				&& organizationIdOfCurrentUser == organizationIdofRequestedUser)
				|| (GetCredentials.checkRoleAuthentication() == 0
						&& userService.findByUsername(GetCredentials.checkUserName()).getId() == userId)) {
			List<Invoice> invoices = invoiceService.findRegularInvoicesByUserId(userId);
			invoiceModels = invoiceService.convertToInvoiceModels(invoices, dateFormat);
		}
		return ResponseEntity.ok().body(invoiceModels);
	}

	@PreAuthorize("hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("organizations/current/recurringinvoices")
	ResponseEntity<List<InvoiceModel>> findRecurringInvoicesByOrganizationId()
			throws OrganizationNotFoundException, UserOrganizationNotFoundException, UserNotFoundException {
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		String dateFormat = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getDateFormat();
		List<Invoice> invoices = invoiceService.findRecurringInvoicesByOrganizationId(organizationIdOfCurrentUser);
		List<InvoiceModel> invoiceModels = invoiceService.convertToInvoiceModels(invoices, dateFormat);
		return ResponseEntity.ok().body(invoiceModels);
	}

	@PreAuthorize("hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("organizations/current/regularinvoices")
	ResponseEntity<List<InvoiceModel>> findRegularInvoicesByOrganizationId()
			throws OrganizationNotFoundException, UserOrganizationNotFoundException, UserNotFoundException {
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		String dateFormat = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getDateFormat();
		List<Invoice> invoices = invoiceService.findRegularInvoicesByOrganizationId(organizationIdOfCurrentUser);
		List<InvoiceModel> invoiceModels = invoiceService.convertToInvoiceModels(invoices, dateFormat);
		return ResponseEntity.ok().body(invoiceModels);
	}

	@PreAuthorize("hasRole('ROLE_ORGANIZATION_ADMIN')")
	@DeleteMapping("invoices/{id}")
	ResponseEntity<InvoiceModel> deleteById(@PathVariable(value = "id") Long id) throws InvoiceNotFoundException,
			UserOrganizationNotFoundException, UserNotFoundException, InvoiceOrganizationNotFoundException {
		InvoiceModel invoiceModel = new InvoiceModel();
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdofDeletedUser = invoiceOrganizationService.findByInvoiceId(id).getOrganization().getId();
		String dateFormat = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getDateFormat();
		if (GetCredentials.checkRoleAuthentication() == 2
				&& organizationIdOfCurrentUser == organizationIdofDeletedUser) {
			Invoice invoice = invoiceService.deleteById(id);
			invoiceModel = invoiceService.convertToInvoiceModel(invoice, dateFormat);
		}
		return ResponseEntity.ok().body(invoiceModel);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("users/{id}/invoices/top3")
	ResponseEntity<List<InvoiceModel>> findTop3InvoicesByUserId(@PathVariable(value = "id") long userId)
			throws UserNotFoundException, UserOrganizationNotFoundException {
		List<InvoiceModel> invoiceModels = null;
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdofRequestedUser = userOrganizationService.findByUserId(userId).getOrganization().getId();
		String dateFormat = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getDateFormat();
		if ((GetCredentials.checkRoleAuthentication() == 2
				&& organizationIdOfCurrentUser == organizationIdofRequestedUser)
				|| (GetCredentials.checkRoleAuthentication() == 0
						&& userService.findByUsername(GetCredentials.checkUserName()).getId() == userId)) {
			List<Invoice> invoices = invoiceService.findTop3InvoicesByUserId(userId);
			invoiceModels = invoiceService.convertToInvoiceModels(invoices, dateFormat);
		}
		return ResponseEntity.ok().body(invoiceModels);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("users/{id}/recurringinvoices/due")
	ResponseEntity<List<InvoiceModel>> findDueRecurringInvoicesByUserId(@PathVariable(value = "id") long userId)
			throws InvoiceNotFoundException, UserNotFoundException, UserOrganizationNotFoundException {
		List<InvoiceModel> invoiceModels = null;
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdofRequestedUser = userOrganizationService.findByUserId(userId).getOrganization().getId();
		String dateFormat = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getDateFormat();
		if ((GetCredentials.checkRoleAuthentication() == 2
				&& organizationIdOfCurrentUser == organizationIdofRequestedUser)
				|| (GetCredentials.checkRoleAuthentication() == 0
						&& userService.findByUsername(GetCredentials.checkUserName()).getId() == userId)) {
			List<Invoice> invoices = invoiceService.findDueRecurringInvoicesByUserId(userId);
			invoiceModels = invoiceService.convertToInvoiceModels(invoices, dateFormat);
		}
		return ResponseEntity.ok().body(invoiceModels);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("users/{id}/regularinvoices/due")
	ResponseEntity<List<InvoiceModel>> findDueRegularInvoicesByUserId(@PathVariable(value = "id") long userId)
			throws InvoiceNotFoundException, UserNotFoundException, UserOrganizationNotFoundException {
		List<InvoiceModel> invoiceModels = null;
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdofRequestedUser = userOrganizationService.findByUserId(userId).getOrganization().getId();
		String dateFormat = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getDateFormat();
		if ((GetCredentials.checkRoleAuthentication() == 2
				&& organizationIdOfCurrentUser == organizationIdofRequestedUser)
				|| (GetCredentials.checkRoleAuthentication() == 0
						&& userService.findByUsername(GetCredentials.checkUserName()).getId() == userId)) {
			List<Invoice> invoiceModelList = invoiceService.findDueRegularInvoicesByUserId(userId);
			invoiceModels = invoiceService.convertToInvoiceModels(invoiceModelList, dateFormat);
		}
		return ResponseEntity.ok().body(invoiceModels);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@PutMapping("invoices/{id}/set/paymentstatus/{paymentStatus}")
	ResponseEntity<String> updatePaymentStatus(@PathVariable(value = "id") long invoiceId,
			@PathVariable(value = "paymentStatus") PaymentStatus paymentStatus)
			throws InvoiceNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserOrganizationNotFoundException, IOException, InvoiceOrganizationNotFoundException, DocumentException {
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdofRequestedUser = invoiceOrganizationService.findByInvoiceId(invoiceId).getOrganization()
				.getId();
		String status = null;
		if (GetCredentials.checkRoleAuthentication() == 2
				&& organizationIdOfCurrentUser == organizationIdofRequestedUser) {
			status = invoiceService.updatePaymentStatus(invoiceId, paymentStatus);
		} else if (GetCredentials.checkRoleAuthentication() == 0) {
			String userName = invoiceService.findById(invoiceId).getUser().getUsername();
			if (organizationIdOfCurrentUser == organizationIdofRequestedUser
					&& GetCredentials.checkUserName().equalsIgnoreCase(userName)) {
				status = invoiceService.updatePaymentStatus(invoiceId, paymentStatus);
			}
		}
		return ResponseEntity.ok().body(status);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping(value = "invoices/{id}/generate/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	ResponseEntity<InputStreamResource> generatePDF(@PathVariable(value = "id") long invoiceId)
			throws IOException, InvoiceNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserOrganizationNotFoundException, InvoiceOrganizationNotFoundException, DocumentException {
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdofRequestedUser = invoiceOrganizationService.findByInvoiceId(invoiceId).getOrganization()
				.getId();
		HttpHeaders headers = new HttpHeaders();
		ByteArrayInputStream bis = invoiceService.generatePDF(invoiceId);
		headers.add("Content-Disposition", "inline; filename=customers.pdf");
		if (GetCredentials.checkRoleAuthentication() == 2
				&& organizationIdOfCurrentUser == organizationIdofRequestedUser) {
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
					.body(new InputStreamResource(bis));
		} else if (GetCredentials.checkRoleAuthentication() == 0) {
			String userName = invoiceService.findById(invoiceId).getUser().getUsername();
			if (organizationIdOfCurrentUser == organizationIdofRequestedUser
					&& GetCredentials.checkUserName().equalsIgnoreCase(userName)) {
				return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
						.body(new InputStreamResource(bis));
			}
		}
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(null);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("invoices/{id}/sent-mail")
	ResponseEntity<String> sentInvoiceInMail(@PathVariable(value = "id") long invoiceId)
			throws InvoiceNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserOrganizationNotFoundException, IOException, InvoiceOrganizationNotFoundException, DocumentException {
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdofRequestedUser = invoiceOrganizationService.findByInvoiceId(invoiceId).getOrganization()
				.getId();
		String status = null;
		if (GetCredentials.checkRoleAuthentication() == 2
				&& organizationIdOfCurrentUser == organizationIdofRequestedUser) {
			status = invoiceService.sentInvoiceInMail(invoiceId);
		} else if (GetCredentials.checkRoleAuthentication() == 0) {
			String userName = invoiceService.findById(invoiceId).getUser().getUsername();
			if (organizationIdOfCurrentUser == organizationIdofRequestedUser
					&& GetCredentials.checkUserName().equalsIgnoreCase(userName)) {
				status = invoiceService.sentInvoiceInMail(invoiceId);
			}
		}
		return ResponseEntity.ok().body(status);
	}
}