package com.caseStudy.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.caseStudy.bean.CreateInvoiceBean;
import com.caseStudy.bean.CreateInvoiceOrganizationBean;
import com.caseStudy.bean.UpdateInvoiceBean;
import com.caseStudy.dto.InvoiceDTO;
import com.caseStudy.enumeration.PaymentStatus;
import com.caseStudy.exception.ClientNotFoundException;
import com.caseStudy.exception.ClientOrganizationNotFoundException;
import com.caseStudy.exception.InvoiceItemNotFoundException;
import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.InvoiceOrganizationNotFoundException;
import com.caseStudy.exception.ItemNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.RecurringInvoiceNotFoundException;
import com.caseStudy.exception.RegularInvoiceNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.InvoiceModel;
import com.caseStudy.security.GetCredentials;
import com.caseStudy.service.ClientOrganizationService;
import com.caseStudy.service.InvoiceOrganizationService;
import com.caseStudy.service.InvoiceService;
import com.caseStudy.service.OrganizationService;
import com.caseStudy.service.UserOrganizationService;
import com.caseStudy.service.UserService;
import com.itextpdf.text.DocumentException;

@RestController
public class InvoiceController {
	static final Logger logger = Logger.getLogger(InvoiceController.class);

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
	@PostMapping("/createinvoice")
	ResponseEntity<InvoiceDTO> createInvoiceModel(@Valid @RequestBody CreateInvoiceBean invoice)
			throws UserNotFoundException, ClientNotFoundException, ItemNotFoundException, InvoiceNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {

		logger.info("InvoiceController--->>createInvoiceModel--->>Start");

		InvoiceDTO invoiceDTO = null;

		if (invoice.getPaymentStatus() != PaymentStatus.PAYMENT_DUE) {

			InvoiceModel invoiceModel = invoiceService.createInvoiceModel(invoice);

			long organizationIdOfCurrentUser = organizationService
					.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
					.getOrganizationId();

			String dateFormat = organizationService
					.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
					.getOrganizationDate();

			invoiceOrganizationService.createInvoiceOrganizationModel(
					new CreateInvoiceOrganizationBean(invoiceModel.getInvoiceId(), organizationIdOfCurrentUser));

			invoiceDTO = new InvoiceDTO(invoiceModel, dateFormat);
		} else {
			logger.error(
					"InvoiceController--->>createInvoiceModel--->>Exception occured-->> Payment due status not allowed");

			logger.error("Payment due status not allowed");
		}

		logger.info("InvoiceController--->>createInvoiceModel--->>Ended");

		return ResponseEntity.ok().body(invoiceDTO);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@PostMapping("/updateinvoice")
	ResponseEntity<InvoiceDTO> updateInvoiceModel(@Valid @RequestBody UpdateInvoiceBean invoiceBean)
			throws InvoiceNotFoundException, UserNotFoundException, ClientNotFoundException,
			InvoiceItemNotFoundException, ItemNotFoundException, RecurringInvoiceNotFoundException,
			RegularInvoiceNotFoundException, UserOrganizationNotFoundException, InvoiceOrganizationNotFoundException {

		logger.info("InvoiceController--->>updateInvoiceModel--->>Start");

		InvoiceDTO invoiceDTO = null;

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdofRequestedUser = invoiceOrganizationService
				.getInvoiceOrganizationModelByInvoiceId(invoiceBean.getInvoiceId()).getOrganizationModel()
				.getOrganizationId();

		String dateFormat = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationDate();

		if (GetCredentials.checkRoleAuthentication() == 2) {
			if (organizationIdOfCurrentUser == organizationIdofRequestedUser) {

				if (invoiceBean.getPaymentStatus() == PaymentStatus.PAYMENT_DRAFT) {

					InvoiceModel invoiceModel = invoiceService.updateInvoiceModel(invoiceBean);

					invoiceDTO = new InvoiceDTO(invoiceModel, dateFormat);
				} else {
					logger.error(
							"InvoiceController--->>updateInvoiceModel--->>Exception occured-->> Payment draft status is only allowed to update");

					logger.error("Payment draft status is only allowed to update");
				}
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 0) {

			String userName = invoiceService.getInvoiceModelById(invoiceBean.getInvoiceId()).getUserModel()
					.getUsername();

			if (organizationIdOfCurrentUser == organizationIdofRequestedUser
					&& GetCredentials.checkUserName().equalsIgnoreCase(userName)) {

				if (invoiceBean.getPaymentStatus() == PaymentStatus.PAYMENT_DRAFT) {

					InvoiceModel invoiceModel = invoiceService.updateInvoiceModel(invoiceBean);

					invoiceDTO = new InvoiceDTO(invoiceModel, dateFormat);

				} else {
					logger.error(
							"InvoiceController--->>updateInvoiceModel--->>Exception occured-->> Payment draft status is only allowed to update");

					logger.error("Payment draft status is only allowed to update");
				}

			}
		}

		logger.info("InvoiceController--->>updateInvoiceModel--->>Ended");

		return ResponseEntity.ok().body(invoiceDTO);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/invoices")
	ResponseEntity<List<InvoiceDTO>> getAllInvoiceModels() throws SQLException, UserOrganizationNotFoundException,
			UserNotFoundException, OrganizationNotFoundException {

		logger.info("InvoiceController--->>getAllInvoiceModels--->>Start");

		List<InvoiceDTO> invoiceDTOList = null;

		String dateFormat = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationDate();

		if (GetCredentials.checkRoleAuthentication() == 2) {

			long organizationIdOfCurrentUser = organizationService
					.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
					.getOrganizationId();

			List<InvoiceModel> invoiceModelList = invoiceService
					.getAllInvoiceModelByOrganizationId(organizationIdOfCurrentUser);

			invoiceDTOList = invoiceService.convertToInvoiceDTOList(invoiceModelList, dateFormat);
		}
		if (GetCredentials.checkRoleAuthentication() == 0) {
			
			long userId = userService.findByUsername(GetCredentials.checkUserName()).getUserId();
					
			List<InvoiceModel> invoiceModelList = invoiceService
					.getInvoiceModelsByUserId(userId);

			invoiceDTOList = invoiceService.convertToInvoiceDTOList(invoiceModelList, dateFormat);
		}

		logger.info("InvoiceController--->>getAllInvoiceModels--->>Ended");

		return ResponseEntity.ok().body(invoiceDTOList);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/invoices/{id}")
	ResponseEntity<InvoiceDTO> getInvoiceModelById(@PathVariable(value = "id") Long invoiceId)
			throws InvoiceNotFoundException, InvoiceOrganizationNotFoundException, UserOrganizationNotFoundException,
			UserNotFoundException {

		logger.info("InvoiceController--->>getInvoiceModelById--->>Start");

		InvoiceDTO invoiceDTO = null;

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdofRequestedUser = invoiceOrganizationService
				.getInvoiceOrganizationModelByInvoiceId(invoiceId).getOrganizationModel().getOrganizationId();

		String dateFormat = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationDate();

		if (GetCredentials.checkRoleAuthentication() == 2) {

			if (organizationIdOfCurrentUser == organizationIdofRequestedUser) {

				InvoiceModel invoiceModel = invoiceService.getInvoiceModelById(invoiceId);

				invoiceDTO = new InvoiceDTO(invoiceModel, dateFormat);
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 0) {

			String userName = invoiceService.getInvoiceModelById(invoiceId).getUserModel().getUsername();

			if (organizationIdOfCurrentUser == organizationIdofRequestedUser
					&& GetCredentials.checkUserName().equalsIgnoreCase(userName)) {

				InvoiceModel invoiceModel = invoiceService.getInvoiceModelById(invoiceId);

				invoiceDTO = new InvoiceDTO(invoiceModel, dateFormat);
			}
		}

		logger.info("InvoiceController--->>getInvoiceModelById--->>Ended");

		return ResponseEntity.ok().body(invoiceDTO);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("{id}/invoicesbyuser")
	ResponseEntity<List<InvoiceDTO>> getInvoiceModelsByUserId(@PathVariable(value = "id") long userId)
			throws InvoiceNotFoundException, UserNotFoundException, UserOrganizationNotFoundException {

		logger.info("InvoiceController--->>getInvoiceModelsByUserId--->>Start");

		List<InvoiceDTO> invoiceDTOList = null;

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdofRequestedUser = userOrganizationService.getUserOrganizationModelByUserId(userId)
				.getOrganizationModel().getOrganizationId();

		String dateFormat = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationDate();

		if (GetCredentials.checkRoleAuthentication() == 2) {

			if (organizationIdOfCurrentUser == organizationIdofRequestedUser) {

				List<InvoiceModel> invoiceModelList = invoiceService.getInvoiceModelsByUserId(userId);

				invoiceDTOList = invoiceService.convertToInvoiceDTOList(invoiceModelList, dateFormat);
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 0) {

			if (userService.findByUsername(GetCredentials.checkUserName()).getUserId() == userId) {

				List<InvoiceModel> invoiceModelList = invoiceService.getInvoiceModelsByUserId(userId);

				invoiceDTOList = invoiceService.convertToInvoiceDTOList(invoiceModelList, dateFormat);
			}
		}

		logger.info("InvoiceController--->>getInvoiceModelsByUserId--->>Ended");

		return ResponseEntity.ok().body(invoiceDTOList);
	}

	@PreAuthorize("hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("{id}/invoicesbyclient")
	ResponseEntity<List<InvoiceDTO>> getInvoiceModelsByClientId(@PathVariable(value = "id") long clientId)
			throws InvoiceNotFoundException, ClientNotFoundException, ClientOrganizationNotFoundException,
			UserOrganizationNotFoundException, UserNotFoundException {

		logger.info("InvoiceController--->>getInvoiceModelsByClientId--->>Start");

		List<InvoiceDTO> invoiceDTOList = null;

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdofRequestedClient = clientOrganizationService.getClientOrganizationModelByClientId(clientId)
				.getOrganizationModel().getOrganizationId();

		String dateFormat = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationDate();

		if (GetCredentials.checkRoleAuthentication() == 2) {

			if (organizationIdOfCurrentUser == organizationIdofRequestedClient) {

				List<InvoiceModel> invoiceModelList = invoiceService.getInvoiceModelsByClientId(clientId);

				invoiceDTOList = invoiceService.convertToInvoiceDTOList(invoiceModelList, dateFormat);
			}
		}

		logger.info("InvoiceController--->>getInvoiceModelsByClientId--->>Ended");

		return ResponseEntity.ok().body(invoiceDTOList);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/{id}/recurringinvoices")
	ResponseEntity<List<InvoiceDTO>> getInvoiceModelsRecurringByUserId(@PathVariable(value = "id") long userId)
			throws InvoiceNotFoundException, UserNotFoundException, UserOrganizationNotFoundException {

		logger.info("InvoiceController--->>getInvoiceModelsRecurringByUserId--->>Start");

		List<InvoiceDTO> invoiceDTOList = null;

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdofRequestedUser = userOrganizationService.getUserOrganizationModelByUserId(userId)
				.getOrganizationModel().getOrganizationId();

		String dateFormat = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationDate();

		if (GetCredentials.checkRoleAuthentication() == 2) {

			if (organizationIdOfCurrentUser == organizationIdofRequestedUser) {

				List<InvoiceModel> invoiceModelList = invoiceService.getInvoiceModelsRecurringByUserId(userId);

				invoiceDTOList = invoiceService.convertToInvoiceDTOList(invoiceModelList, dateFormat);
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 0) {

			if (userService.findByUsername(GetCredentials.checkUserName()).getUserId() == userId) {

				List<InvoiceModel> invoiceModelList = invoiceService.getInvoiceModelsRecurringByUserId(userId);

				invoiceDTOList = invoiceService.convertToInvoiceDTOList(invoiceModelList, dateFormat);
			}
		}

		logger.info("InvoiceController--->>getInvoiceModelsRecurringByUserId--->>Ended");

		return ResponseEntity.ok().body(invoiceDTOList);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/{id}/regularinvoices")
	ResponseEntity<List<InvoiceDTO>> getInvoiceModelsRegularByUserId(@PathVariable(value = "id") long userId)
			throws InvoiceNotFoundException, UserNotFoundException, UserOrganizationNotFoundException {

		logger.info("InvoiceController--->>getInvoiceModelsRegularByUserId--->>Start");

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdofRequestedUser = userOrganizationService.getUserOrganizationModelByUserId(userId)
				.getOrganizationModel().getOrganizationId();

		String dateFormat = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationDate();

		List<InvoiceDTO> invoiceDTOList = null;

		if (GetCredentials.checkRoleAuthentication() == 2) {

			if (organizationIdOfCurrentUser == organizationIdofRequestedUser) {

				List<InvoiceModel> invoiceModelList = invoiceService.getInvoiceModelsRegularByUserId(userId);

				invoiceDTOList = invoiceService.convertToInvoiceDTOList(invoiceModelList, dateFormat);
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 0) {

			if (userService.findByUsername(GetCredentials.checkUserName()).getUserId() == userId) {

				List<InvoiceModel> invoiceModelList = invoiceService.getInvoiceModelsRegularByUserId(userId);

				invoiceDTOList = invoiceService.convertToInvoiceDTOList(invoiceModelList, dateFormat);
			}
		}

		logger.info("InvoiceController--->>getInvoiceModelsRegularByUserId--->>Ended");

		return ResponseEntity.ok().body(invoiceDTOList);
	}

	@PreAuthorize("hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/recurringinvoices")
	ResponseEntity<List<InvoiceDTO>> getAllInvoiceModelsRecurring() throws InvoiceNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, UserNotFoundException {

		logger.info("InvoiceController--->>getAllInvoiceModelsRecurring--->>Start");

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		String dateFormat = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationDate();

		List<InvoiceModel> invoiceModelList = invoiceService.getAllInvoiceModelsRecurring(organizationIdOfCurrentUser);

		List<InvoiceDTO> invoiceDTOList = invoiceService.convertToInvoiceDTOList(invoiceModelList, dateFormat);

		logger.info("InvoiceController--->>getAllInvoiceModelsRecurring--->>Ended");

		return ResponseEntity.ok().body(invoiceDTOList);
	}

	@PreAuthorize("hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/regularinvoices")
	ResponseEntity<List<InvoiceDTO>> getAllInvoiceModelsRegular() throws InvoiceNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, UserNotFoundException {

		logger.info("InvoiceController--->>getAllInvoiceModelsRegular--->>Start");

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		String dateFormat = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationDate();

		List<InvoiceModel> invoiceModelList = invoiceService.getAllInvoiceModelsRegular(organizationIdOfCurrentUser);

		List<InvoiceDTO> invoiceDTOList = invoiceService.convertToInvoiceDTOList(invoiceModelList, dateFormat);

		logger.info("InvoiceController--->>getAllInvoiceModelsRegular--->>Ended");

		return ResponseEntity.ok().body(invoiceDTOList);
	}

	@PreAuthorize("hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/deleteinvoice/{id}")
	ResponseEntity<InvoiceDTO> deleteInvoiceModel(@PathVariable(value = "id") Long invoiceId)
			throws InvoiceNotFoundException, UserOrganizationNotFoundException, UserNotFoundException,
			InvoiceOrganizationNotFoundException {

		logger.info("InvoiceController--->>deleteInvoiceModel--->>Start");

		InvoiceDTO invoiceDTO = new InvoiceDTO();

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdofDeletedUser = invoiceOrganizationService.getInvoiceOrganizationModelByInvoiceId(invoiceId)
				.getOrganizationModel().getOrganizationId();

		String dateFormat = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationDate();

		if (GetCredentials.checkRoleAuthentication() == 2) {

			if (organizationIdOfCurrentUser == organizationIdofDeletedUser) {

				InvoiceModel invoiceModel = invoiceService.deleteInvoiceModel(invoiceId);

				invoiceDTO = invoiceService.InvoiceModelToInvoiceDTO(invoiceModel, dateFormat);
			}
		}

		logger.info("InvoiceController--->>deleteInvoiceModel--->>Ended");

		return ResponseEntity.ok().body(invoiceDTO);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/recentinvoices/{id}")
	ResponseEntity<List<InvoiceDTO>> getRecentInvoiceModels(@PathVariable(value = "id") long userId)
			throws InvoiceNotFoundException, UserNotFoundException, UserOrganizationNotFoundException {

		logger.info("InvoiceController--->>getRecentInvoiceModels--->>Start");

		List<InvoiceDTO> invoiceDTOList = null;

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdofRequestedUser = userOrganizationService.getUserOrganizationModelByUserId(userId)
				.getOrganizationModel().getOrganizationId();

		String dateFormat = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationDate();

		if (GetCredentials.checkRoleAuthentication() == 2) {
			if (organizationIdOfCurrentUser == organizationIdofRequestedUser) {

				List<InvoiceModel> invoiceModelList = invoiceService.getRecentInvoiceModels(userId);

				invoiceDTOList = invoiceService.convertToInvoiceDTOList(invoiceModelList, dateFormat);
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 0) {
			if (userService.findByUsername(GetCredentials.checkUserName()).getUserId() == userId) {

				List<InvoiceModel> invoiceModelList = invoiceService.getRecentInvoiceModels(userId);

				invoiceDTOList = invoiceService.convertToInvoiceDTOList(invoiceModelList, dateFormat);
			}
		}

		logger.info("InvoiceController--->>getRecentInvoiceModels--->>Ended");

		return ResponseEntity.ok().body(invoiceDTOList);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/duerecurringinvoices/{id}")
	ResponseEntity<List<InvoiceDTO>> getRecurringDueInvoiceModels(@PathVariable(value = "id") long userId)
			throws InvoiceNotFoundException, UserNotFoundException, UserOrganizationNotFoundException {
		logger.info("InvoiceController--->>getRecurringDueInvoiceModels--->>Start");

		List<InvoiceDTO> invoiceDTOList = null;

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdofRequestedUser = userOrganizationService.getUserOrganizationModelByUserId(userId)
				.getOrganizationModel().getOrganizationId();

		String dateFormat = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationDate();

		if (GetCredentials.checkRoleAuthentication() == 2) {
			if (organizationIdOfCurrentUser == organizationIdofRequestedUser) {

				List<InvoiceModel> invoiceModelList = invoiceService.getRecurringDueInvoiceModels(userId);

				invoiceDTOList = invoiceService.convertToInvoiceDTOList(invoiceModelList, dateFormat);
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 0) {
			if (userService.findByUsername(GetCredentials.checkUserName()).getUserId() == userId) {

				List<InvoiceModel> invoiceModelList = invoiceService.getRecurringDueInvoiceModels(userId);

				invoiceDTOList = invoiceService.convertToInvoiceDTOList(invoiceModelList, dateFormat);
			}
		}

		logger.info("InvoiceController--->>getRecurringDueInvoiceModels--->>Ended");

		return ResponseEntity.ok().body(invoiceDTOList);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/dueregularinvoices/{id}")
	ResponseEntity<List<InvoiceDTO>> getRegularDueInvoiceModels(@PathVariable(value = "id") long userId)
			throws InvoiceNotFoundException, UserNotFoundException, UserOrganizationNotFoundException {
		logger.info("InvoiceController--->>getRegularDueInvoiceModels--->>Start");

		List<InvoiceDTO> invoiceDTOList = null;

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdofRequestedUser = userOrganizationService.getUserOrganizationModelByUserId(userId)
				.getOrganizationModel().getOrganizationId();

		String dateFormat = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationDate();

		if (GetCredentials.checkRoleAuthentication() == 2) {
			if (organizationIdOfCurrentUser == organizationIdofRequestedUser) {

				List<InvoiceModel> invoiceModelList = invoiceService.getRegularDueInvoiceModels(userId);

				invoiceDTOList = invoiceService.convertToInvoiceDTOList(invoiceModelList, dateFormat);
			}
		}

		if (GetCredentials.checkRoleAuthentication() == 0) {
			if (userService.findByUsername(GetCredentials.checkUserName()).getUserId() == userId) {

				List<InvoiceModel> invoiceModelList = invoiceService.getRegularDueInvoiceModels(userId);

				invoiceDTOList = invoiceService.convertToInvoiceDTOList(invoiceModelList, dateFormat);
			}
		}

		logger.info("InvoiceController--->>getRegularDueInvoiceModels--->>Ended");

		return ResponseEntity.ok().body(invoiceDTOList);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/updateinvoicepaymentstatus/{id}/{paymentStatus}")
	ResponseEntity<String> updatePaymentStatus(@PathVariable(value = "id") long invoiceId,
			@PathVariable(value = "paymentStatus") PaymentStatus paymentStatus)
			throws InvoiceNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserOrganizationNotFoundException, IOException, InvoiceOrganizationNotFoundException, DocumentException {

		logger.info("InvoiceController--->>updatePaymentStatus--->>Start");

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdofRequestedUser = invoiceOrganizationService
				.getInvoiceOrganizationModelByInvoiceId(invoiceId).getOrganizationModel().getOrganizationId();

		String updateStatus = null;

		if (GetCredentials.checkRoleAuthentication() == 2) {
			if (organizationIdOfCurrentUser == organizationIdofRequestedUser) {

				updateStatus = invoiceService.updatePaymentStatus(invoiceId, paymentStatus);
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 0) {

			String userName = invoiceService.getInvoiceModelById(invoiceId).getUserModel().getUsername();

			if (organizationIdOfCurrentUser == organizationIdofRequestedUser
					&& GetCredentials.checkUserName().equalsIgnoreCase(userName)) {

				updateStatus = invoiceService.updatePaymentStatus(invoiceId, paymentStatus);
			}
		}

		logger.info("InvoiceController--->>updatePaymentStatus--->>Ended");

		return ResponseEntity.ok().body(updateStatus);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping(value = "/invoicepdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	ResponseEntity<InputStreamResource> invoicePDF(@PathVariable(value = "id") long invoiceId)
			throws IOException, InvoiceNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserOrganizationNotFoundException, InvoiceOrganizationNotFoundException, DocumentException {
		logger.info("InvoiceController--->>invoicePDF--->>Start");

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdofRequestedUser = invoiceOrganizationService
				.getInvoiceOrganizationModelByInvoiceId(invoiceId).getOrganizationModel().getOrganizationId();

		HttpHeaders headers = new HttpHeaders();

		ByteArrayInputStream bis = invoiceService.pdfGeneration(invoiceId);

		headers.add("Content-Disposition", "inline; filename=customers.pdf");

		if (GetCredentials.checkRoleAuthentication() == 2) {
			if (organizationIdOfCurrentUser == organizationIdofRequestedUser) {

				logger.info("InvoiceController--->>invoicePDF--->>Ended");

				return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
						.body(new InputStreamResource(bis));
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 0) {

			String userName = invoiceService.getInvoiceModelById(invoiceId).getUserModel().getUsername();

			if (organizationIdOfCurrentUser == organizationIdofRequestedUser
					&& GetCredentials.checkUserName().equalsIgnoreCase(userName)) {

				logger.info("InvoiceController--->>invoicePDF--->>Ended");

				return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
						.body(new InputStreamResource(bis));
			}
		}

		logger.info("InvoiceController--->>invoicePDF--->>Ended");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(null);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/sentmail/{id}")
	ResponseEntity<String> sentInvoice(@PathVariable(value = "id") long invoiceId)
			throws InvoiceNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserOrganizationNotFoundException, IOException, InvoiceOrganizationNotFoundException, DocumentException {
		logger.info("InvoiceController--->>sentInvoice--->>Start");

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdofRequestedUser = invoiceOrganizationService
				.getInvoiceOrganizationModelByInvoiceId(invoiceId).getOrganizationModel().getOrganizationId();

		String sentStatus = null;

		if (GetCredentials.checkRoleAuthentication() == 2) {
			if (organizationIdOfCurrentUser == organizationIdofRequestedUser) {

				sentStatus = invoiceService.sentInvoice(invoiceId);
			}
		}
		if (GetCredentials.checkRoleAuthentication() == 0) {

			String userName = invoiceService.getInvoiceModelById(invoiceId).getUserModel().getUsername();

			if (organizationIdOfCurrentUser == organizationIdofRequestedUser
					&& GetCredentials.checkUserName().equalsIgnoreCase(userName)) {

				sentStatus = invoiceService.sentInvoice(invoiceId);
			}
		}
		logger.info("InvoiceController--->>sentInvoice--->>Ended");

		return ResponseEntity.ok().body(sentStatus);
	}
}