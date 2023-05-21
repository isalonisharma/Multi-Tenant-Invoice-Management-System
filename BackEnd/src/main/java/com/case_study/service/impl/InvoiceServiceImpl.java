package com.case_study.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.case_study.bean.CreateInvoiceBean;
import com.case_study.bean.UpdateInvoiceBean;
import com.case_study.entity.Audit;
import com.case_study.entity.Client;
import com.case_study.entity.Invoice;
import com.case_study.entity.InvoiceItem;
import com.case_study.entity.InvoiceOrganization;
import com.case_study.entity.Item;
import com.case_study.entity.RecurringInvoice;
import com.case_study.entity.RegularInvoice;
import com.case_study.entity.User;
import com.case_study.enumeration.PaymentStatus;
import com.case_study.exception.ClientNotFoundException;
import com.case_study.exception.InvoiceItemNotFoundException;
import com.case_study.exception.InvoiceNotFoundException;
import com.case_study.exception.ItemNotFoundException;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.exception.RecurringInvoiceNotFoundException;
import com.case_study.exception.RegularInvoiceNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.model.InvoiceModel;
import com.case_study.repository.InvoiceRepository;
import com.case_study.service.AuditService;
import com.case_study.service.ClientService;
import com.case_study.service.FileStorageService;
import com.case_study.service.InvoiceItemService;
import com.case_study.service.InvoiceOrganizationService;
import com.case_study.service.InvoiceService;
import com.case_study.service.ItemService;
import com.case_study.service.MailService;
import com.case_study.service.OrganizationService;
import com.case_study.service.RecurringInvoiceService;
import com.case_study.service.RegularInvoiceService;
import com.case_study.service.UserOrganizationService;
import com.case_study.service.UserService;
import com.case_study.utility.CommonConstants;
import com.case_study.utility.PdfGeneration;
import com.itextpdf.text.DocumentException;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {
	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private MailService mailService;

	@Autowired
	private UserService userService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private InvoiceItemService invoiceItemService;

	@Autowired
	private RegularInvoiceService regularInvoiceService;

	@Autowired
	private RecurringInvoiceService recurringInvoiceService;

	@Autowired
	private UserOrganizationService userOrganizationService;

	@Autowired
	private InvoiceOrganizationService invoiceOrganizationService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private AuditService auditService;

	@Override
	public Invoice createInvoice(CreateInvoiceBean createInvoiceBean)
			throws UserNotFoundException, ClientNotFoundException, ItemNotFoundException, InvoiceNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {
		/* finding the user of given user id */
		User user = userService.findById(createInvoiceBean.getUserId());
		/* finding the client of given client id */
		Client client = clientService.findById(createInvoiceBean.getClientId());
		double totalAmount = 0;
		/*
		 * taking the map of item id as key and item quantity as value from invoiceBean
		 */
		for (Map.Entry<Long, Long> entry : createInvoiceBean.getItems().entrySet()) {
			/* extracting the item with the help of given item id */
			Item item = itemService.findById(entry.getKey());

			/* calculating the total amount of invoice */
			totalAmount += item.getRate() * entry.getValue();
		}
		/* creating the invoice */
		Invoice createdInvoice = invoiceRepository.save(new Invoice(user, client, LocalDateTime.now(),
				createInvoiceBean.isRecurring(), totalAmount, createInvoiceBean.isLocked()));
		/* entry in the invoice item table */
		for (Map.Entry<Long, Long> entry : createInvoiceBean.getItems().entrySet()) {
			Item item = itemService.findById(entry.getKey());

			/* creating the invoice item table entry of specific item */
			invoiceItemService.createInvoiceItem(
					new InvoiceItem(createdInvoice, item, entry.getValue(), item.getRate() * entry.getValue()));
		}
		if (createdInvoice.isRecurring()) {
			/* entry in the recurring invoice table */
			RecurringInvoice recurringInvoice = recurringInvoiceService.createRecurringInvoice(createdInvoice,
					createInvoiceBean.getPaymentStatus(), createInvoiceBean.getDueDate(),
					createInvoiceBean.getRenewDate(), createInvoiceBean.getRecurringPeriod() - 1);
			if (recurringInvoice != null) {
				/* entry in the audit table */
				auditService.createAudit(new Audit(createdInvoice, LocalDateTime.now(), PaymentStatus.PAYMENT_DRAFT,
						"Invoice Number :" + createdInvoice.getId() + " created succesfully."));
			}
		} else {
			/* entry in the regular invoice table */
			RegularInvoice regularInvoice = regularInvoiceService.createRegularInvoice(createdInvoice,
					createInvoiceBean.getPaymentStatus(), createInvoiceBean.getDueDate());
			if (regularInvoice != null) {
				/* entry in the audit table */
				auditService.createAudit(new Audit(createdInvoice, LocalDateTime.now(), PaymentStatus.PAYMENT_DRAFT,
						"Invoice Number :" + createdInvoice.getId() + " created succesfully."));
			}
		}
		sentInvoiceInMail(createdInvoice.getId());
		return createdInvoice;
	}

	@Override
	public Invoice deleteById(Long id) throws InvoiceNotFoundException {
		Invoice invoice = invoiceRepository.findById(id)
				.orElseThrow(() -> new InvoiceNotFoundException(CommonConstants.INVOICE_NOT_FOUND + id));
		/* invoice is locked and getting deleted for the front end user */
		invoice.setLocked(true);
		/* saving the invoice as locked=true in the database */
		return invoiceRepository.save(invoice);
	}

	@Override
	public Invoice updateInvoice(UpdateInvoiceBean updateInvoiceBean) throws InvoiceNotFoundException,
			UserNotFoundException, ClientNotFoundException, InvoiceItemNotFoundException, ItemNotFoundException,
			RecurringInvoiceNotFoundException, RegularInvoiceNotFoundException {
		/* finding the current invoice with the help of invoice id */
		Invoice invoice = invoiceRepository.findById(updateInvoiceBean.getId())
				.orElseThrow(() -> new InvoiceNotFoundException(
						CommonConstants.INVOICE_NOT_FOUND + updateInvoiceBean.getId()));
		/*
		 * checking the current Invoice containing user is same or different with the
		 * help of user id
		 */
		if (invoice.getUser().getId() != updateInvoiceBean.getUserId()) {
			/* if the user is different */
			/* finding the user with the help of given user id */
			User user = userService.findById(updateInvoiceBean.getUserId());
			/* setting the new user in current invoice */
			invoice.setUser(user);
		}
		/*
		 * checking the current Invoice containing client is same or different with the
		 * help of client id
		 */
		if (invoice.getClient().getId() != updateInvoiceBean.getClientId()) {
			/*
			 * if the client is different finding the client with the help of given client
			 * id
			 */
			Client client = clientService.findById(updateInvoiceBean.getClientId());
			/* setting the new client in current invoice */
			invoice.setClient(client);
		}
		/* changing the date placed of current invoice to today's date */
		invoice.setDatePlaced(LocalDateTime.now());
		/*
		 * changing the currentInvoice {isInvoiceLocked} property to invoiceBean
		 * {isInvoiceIsLocked} property
		 */
		invoice.setLocked(updateInvoiceBean.isLocked());
		/* extracting the invoice item list with the help of given invoice id */
		List<InvoiceItem> invoiceItems = invoiceItemService.findByInvoiceId(invoice.getId());
		/* iterating over the invoiceItemList */
		for (InvoiceItem invoiceItem : invoiceItems) {
			/*
			 * deleting all the entries from the invoice item table of given invoice id so
			 * that the new can be saved
			 */
			invoiceItemService.deleteById(invoiceItem.getId());
		}
		double amount = 0;
		/* iterating over the invoiceBean {InvoiceItem} Map */
		for (Map.Entry<Long, Long> entry : updateInvoiceBean.getItems().entrySet()) {
			/* extracting the item with the help of given item id */
			Item item = itemService.findById(entry.getKey());
			/* extracting the quantity and rate from the entry */
			long quantity = entry.getValue();
			float rate = item.getRate();
			/* calculating the amount */
			amount += rate * quantity;
			/* adding the entry in the invoice item table of current invoice id item's */
			invoiceItemService.createInvoiceItem(new InvoiceItem(invoice, item, quantity, rate * quantity));
		}
		/* setting the current invoice amount */
		invoice.setAmount(amount);
		if (invoice.isRecurring()) {
			/* if current invoice is recurring ----> isInvoiceIsRecurring = true */
			List<RecurringInvoice> recurringInvoices = recurringInvoiceService.findByInvoice(invoice);
			/*
			 * deleting all the entries from the recurringInvoice table of given invoice id
			 * so that the new can be saved
			 */
			for (RecurringInvoice recurringInvoice : recurringInvoices) {
				recurringInvoiceService.deleteById(recurringInvoice.getId());
			}
		} else if (!invoice.isRecurring()) {
			/* if current invoice is regular ----> isInvoiceIsRecurring = false */
			List<RegularInvoice> regularInvoices = regularInvoiceService.findByInvoice(invoice);
			/*
			 * deleting all the entries from the regularInvoice table of given invoice id so
			 * that the new can be saved
			 */
			for (RegularInvoice regularInvoice : regularInvoices) {
				regularInvoiceService.deleteById(regularInvoice.getId());
			}
		}
		/*
		 * set the isInvoiceIsRecurring property from invoiceBean to current invoice
		 */
		invoice.setRecurring(updateInvoiceBean.isRecurring());
		if (invoice.isRecurring()) {
			/* if current invoice is recurring ----> isInvoiceIsRecurring = true */
			/* create recurring invoice from current invoice */
			recurringInvoiceService.createRecurringInvoice(invoice, updateInvoiceBean.getPaymentStatus(),
					updateInvoiceBean.getDueDate(), updateInvoiceBean.getRenewDate(),
					updateInvoiceBean.getRecurringPeriod() - 1);
		} else {
			/* if current invoice is regular ----> isInvoiceIsRecurring = false */
			/* create regular invoice from current invoice */
			regularInvoiceService.createRegularInvoice(invoice, updateInvoiceBean.getPaymentStatus(),
					updateInvoiceBean.getDueDate());
		}
		return invoice;
	}

	@Override
	public String updatePaymentStatus(long invoiceId, PaymentStatus paymentStatus)
			throws InvoiceNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserOrganizationNotFoundException, IOException, DocumentException {
		/* finding the invoice with the help of given invoice id */
		Invoice invoice = invoiceRepository.findById(invoiceId)
				.orElseThrow(() -> new InvoiceNotFoundException("Invoice not found :: " + invoiceId));
		boolean payment = false;
		if (invoice.isRecurring()) {
			/* if invoice is recurring ----> isInvoiceIsRecurring = true */
			payment = recurringInvoiceService.updatePaymentStatus(invoice, paymentStatus);
		} else if (!invoice.isRecurring()) {
			/* if invoice is regular ----> isInvoiceIsRecurring = false */
			payment = regularInvoiceService.updatePaymentStatus(invoice, paymentStatus);
		}
		/* entry in the audit table */
		auditService.createAudit(new Audit(invoice, LocalDateTime.now(), paymentStatus,
				"Invoice Number :" + invoice.getId() + " Payment status updated"));
		/* checking the payment is true or not */
		if (payment) {
			boolean organizationThankYouMail = false;
			/* if the payment status is changed to PAID */
			if (paymentStatus == PaymentStatus.PAYMENT_PAID) {
				/* sending the thank you mail from the organization */
				organizationThankYouMail = organizationService.findById(
						userOrganizationService.findByUserId(invoice.getUser().getId()).getOrganization().getId())
						.isThankYouEMail();
				if (organizationThankYouMail) {
					mailService.sendThankyouMail(invoice);
					return "Payment Status Updated Succesfully and Thank you mail sent";
				}
			}
			return "Payment Status Updated Succesfully";
		}
		return "Payment Status Update Failed";
	}

	@Override
	public List<Invoice> findAll() throws SQLException {
		List<Invoice> invoices = invoiceRepository.findAll();
		invoices.removeIf(Invoice::isLocked);
		return invoices;
	}

	@Override
	public Invoice findById(Long id) throws InvoiceNotFoundException {
		Invoice invoice = invoiceRepository.findById(id)
				.orElseThrow(() -> new InvoiceNotFoundException(CommonConstants.INVOICE_NOT_FOUND + id));
		if (invoice.isLocked()) {
			throw new InvoiceNotFoundException(CommonConstants.INVOICE_NOT_FOUND + id);
		}
		return invoice;
	}

	@Override
	public List<Invoice> findByUserId(long userId) throws UserNotFoundException {
		User user = userService.findById(userId);
		List<Invoice> invoices = invoiceRepository.findByUser(user);
		invoices.removeIf(Invoice::isLocked);
		return invoices;
	}

	@Override
	public List<Invoice> findByClientId(long clientId) throws ClientNotFoundException {
		Client client = clientService.findById(clientId);
		return invoiceRepository.findByClient(client);
	}

	@Override
	public List<Invoice> findByOrganizationId(long organizationId) throws OrganizationNotFoundException {
		List<InvoiceOrganization> invoiceOrganizations = invoiceOrganizationService
				.findByOrganizationId(organizationId);
		ArrayList<Invoice> invoices = new ArrayList<>();
		for (InvoiceOrganization invoiceOrganization : invoiceOrganizations) {
			invoices.add(invoiceOrganization.getInvoice());
		}
		invoices.removeIf(Invoice::isLocked);
		return invoices;
	}

	@Override
	public List<Invoice> findRecurringInvoicesByUserId(long userId) throws UserNotFoundException {
		User user = userService.findById(userId);
		/* list of invoice of given user id */
		List<Invoice> invoices = invoiceRepository.findByUser(user);
		/*
		 * removing the invoice from list which are regular and then, list will only
		 * contain the invoice which are recurring
		 */
		invoices.removeIf((Invoice invoice) -> !invoice.isRecurring());
		/*
		 * removing the invoice from list which are locked and then, list will only
		 * contain existing invoice
		 */
		invoices.removeIf(Invoice::isLocked);
		return invoices;
	}

	@Override
	public List<Invoice> findRegularInvoicesByUserId(long userId) throws UserNotFoundException {
		User user = userService.findById(userId);
		/* list of invoice of given user id */
		List<Invoice> invoices = invoiceRepository.findByUser(user);
		/*
		 * removing the invoice from list which are recurring and then, list will only
		 * contain the invoice which are regular
		 */
		invoices.removeIf(Invoice::isRecurring);
		/*
		 * removing the invoice from list which are locked and then, list will only
		 * contain existing invoices
		 */
		invoices.removeIf(Invoice::isLocked);
		return invoices;
	}

	@Override
	public List<Invoice> findRecurringInvoicesByOrganizationId(long organizationId)
			throws OrganizationNotFoundException {
		/* list of invoice organization of given organizationId */
		List<InvoiceOrganization> invoiceOrganizations = invoiceOrganizationService
				.findByOrganizationId(organizationId);
		ArrayList<Invoice> invoices = new ArrayList<>();
		/* extracting the invoice */
		for (InvoiceOrganization invoiceOrganization : invoiceOrganizations) {
			invoices.add(invoiceOrganization.getInvoice());
		}
		/*
		 * removing the invoice from list which are regular and then, list will only
		 * contain the invoice which are recurring
		 */
		invoices.removeIf((Invoice invoice) -> !invoice.isRecurring());
		/*
		 * removing the invoice from list which are locked and then, list will only
		 * contain existing invoice
		 */
		invoices.removeIf(Invoice::isLocked);
		return invoices;
	}

	@Override
	public List<Invoice> findRegularInvoicesByOrganizationId(long organizationId) throws OrganizationNotFoundException {
		/* list of invoice organization of given organizationId */
		List<InvoiceOrganization> invoiceOrganizations = invoiceOrganizationService
				.findByOrganizationId(organizationId);
		ArrayList<Invoice> invoices = new ArrayList<>();
		/* extracting the invoice */
		for (InvoiceOrganization invoiceOrganization : invoiceOrganizations) {
			invoices.add(invoiceOrganization.getInvoice());
		}
		/*
		 * removing the invoice from list which are recurring and then, list will only
		 * contain the invoice which are regular
		 */
		invoices.removeIf(Invoice::isRecurring);
		/*
		 * removing the invoice from list which are locked and then, list will only
		 * contain existing invoice
		 */
		invoices.removeIf(Invoice::isLocked);
		return invoices;
	}

	@Override
	public List<Invoice> findTop3InvoicesByUserId(long userId) throws UserNotFoundException {
		/* extracting the user from given user id */
		User user = userService.findById(userId);
		/*
		 * getting the top 3 recent invoices as per the date placed in descending order
		 * of given user id
		 */
		List<Invoice> invoices = invoiceRepository.findTop3ByUserOrderByDatePlacedDesc(user);
		/*
		 * removing the invoice from list which are locked and then, list will only
		 * contain existing invoice
		 */
		invoices.removeIf(Invoice::isLocked);
		return invoices;
	}

	@Override
	public List<Invoice> findDueRecurringInvoicesByUserId(long userId)
			throws UserNotFoundException, InvoiceNotFoundException {
		/* list of due invoice */
		ArrayList<Invoice> dueInvoices = new ArrayList<>();

		/* extracting the user from given user id */
		User user = userService.findById(userId);

		/* list of invoice for given user */
		List<Invoice> invoices = invoiceRepository.findByUser(user);

		int count = 0;
		for (Invoice invoice : invoices) {
			/* if invoice is recurring ----> isInvoiceIsRecurring = true */
			if (invoice.isRecurring()) {

				/* list of recurring invoice for given invoice */
				List<RecurringInvoice> recurringInvoices = recurringInvoiceService
						.findByInvoiceSortByDueDateDesc(invoice);

				/* removing the recurring invoice for which payment status is not due */
				recurringInvoices.removeIf((RecurringInvoice recurringInvoice) -> recurringInvoice
						.getPaymentStatus() != PaymentStatus.PAYMENT_DUE);

				/* iterating the recurringInvoiceList */
				for (RecurringInvoice recurringInvoice : recurringInvoices) {
					/* if the count is greater than or equal to 3, break the loop */
					if (count >= 3) {
						break;
					}
					/*
					 * extracting the invoice for which payment status is due and is of recurring
					 * type
					 */
					dueInvoices.add(invoiceRepository.findById(recurringInvoice.getInvoice().getId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + recurringInvoice.getInvoice().getId())));

					/* increasing the count by 1 */
					count++;
				}
			}
		}
		/*
		 * removing the invoice from list which are locked and then, list will only
		 * contain existing invoice
		 */
		dueInvoices.removeIf(Invoice::isLocked);
		return dueInvoices;
	}

	@Override
	public List<Invoice> findDueRegularInvoicesByUserId(long userId)
			throws UserNotFoundException, InvoiceNotFoundException {
		/* list of due invoice */
		ArrayList<Invoice> dueInvoices = new ArrayList<>();

		/* extracting the user from given user id */
		User user = userService.findById(userId);

		/* list of invoice for given user */
		List<Invoice> invoices = invoiceRepository.findByUser(user);

		ArrayList<RegularInvoice> regularInvoices = new ArrayList<>();
		int count = 0;
		for (Invoice invoice : invoices) {
			/* if invoice is regular ----> isInvoiceIsRecurring = false */
			if (!invoice.isRecurring()) {
				/* list of regular invoice for given invoice */
				List<RegularInvoice> regularInvoicesByDueDate = regularInvoiceService
						.findByInvoiceSortByDueDateDesc(invoice);
				/* removing the regular invoice for which payment status is not due */
				regularInvoicesByDueDate.removeIf((RegularInvoice regularInvoice) -> regularInvoice
						.getPaymentStatus() != PaymentStatus.PAYMENT_DUE);
				/*
				 * adding the regularInvoice from regularInvoiceList to sortedRegularInvoiceist
				 */
				for (RegularInvoice regularInvoice : regularInvoicesByDueDate) {
					regularInvoices.add(regularInvoice);
				}
			}
		}
		Collections.sort(regularInvoices, new Comparator<RegularInvoice>() {
			@Override
			public int compare(RegularInvoice r1, RegularInvoice r2) {
				return r1.getDueDate().compareTo(r2.getDueDate());
			}
		});
		/* iterating the sortedRegularInvoiceist */
		for (RegularInvoice regularInvoice : regularInvoices) {
			/* if the count is greater than or equal to 3, break the loop */
			if (count >= 3) {
				break;
			}
			/*
			 * extracting the invoice for which payment status is due and is of regular type
			 */
			dueInvoices.add(invoiceRepository.findById(regularInvoice.getInvoice().getId()).orElseThrow(
					() -> new InvoiceNotFoundException("Invoice not found :: " + regularInvoice.getInvoice().getId())));
			/* increasing the count by 1 */
			count++;
		}
		/*
		 * removing the invoice from list which are locked and then, list will only
		 * contain existing invoice s
		 */
		dueInvoices.removeIf(Invoice::isLocked);
		return dueInvoices;
	}

	@Override
	public List<Invoice> getDueInvoicesForTomorrow() throws InvoiceNotFoundException {
		/* list of invoice one whose due date is tomorrow */
		ArrayList<Invoice> dueInvoices = new ArrayList<>();
		/* list of invoice */
		List<Invoice> invoices = invoiceRepository.findAll();
		for (Invoice invoice : invoices) {
			/* if invoice is recurring ----> isInvoiceIsRecurring = true */
			if (invoice.isRecurring()) {
				/* list of recurring invoice for given invoice */
				List<RecurringInvoice> recurringInvoices = recurringInvoiceService.findByInvoice(invoice);
				/*
				 * removing the recurring invoice from the list for which due date is not
				 * tomorrow
				 */
				recurringInvoices.removeIf((RecurringInvoice recurringInvoice) -> LocalDate.now().plusDays(1)
						.compareTo(recurringInvoice.getDueDate().toLocalDate()) != 0);
				/* iterating over the recurringInvoiceList */
				for (RecurringInvoice recurringInvoice : recurringInvoices) {
					/* adding the invoice to final dueInvoiceTomorrowList */
					dueInvoices.add(invoiceRepository.findById(recurringInvoice.getInvoice().getId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + recurringInvoice.getInvoice().getId())));
				}
			}
			/* if invoice is regular ----> isInvoiceIsRecurring = false */
			if (!invoice.isRecurring()) {
				/* list of regular invoice for given invoice */
				List<RegularInvoice> regularInvoices = regularInvoiceService.findByInvoice(invoice);
				/*
				 * removing the regular invoice from the list for which due date is not tomorrow
				 */
				regularInvoices.removeIf((RegularInvoice regularInvoice) -> LocalDate.now().plusDays(1)
						.compareTo(regularInvoice.getDueDate().toLocalDate()) != 0);
				/* iterating over the regularInvoiceList */
				for (RegularInvoice regularInvoice : regularInvoices) {

					/* adding the invoice to final dueInvoiceTomorrowList */
					dueInvoices.add(invoiceRepository.findById(regularInvoice.getInvoice().getId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + regularInvoice.getInvoice().getId())));
				}
			}
		}
		/*
		 * removing the invoice from list which are locked and then, list will only
		 * contain existing invoice s
		 */
		dueInvoices.removeIf(Invoice::isLocked);
		return dueInvoices;
	}

	@Override
	public List<Invoice> getDueInvoicesForToday() throws InvoiceNotFoundException {
		/* list of invoice one whose due date is today */
		ArrayList<Invoice> dueInvoices = new ArrayList<>();
		/* list of invoice */
		List<Invoice> invoices = invoiceRepository.findAll();
		for (Invoice invoice : invoices) {
			/* if invoice is recurring ----> isInvoiceIsRecurring = true */
			if (invoice.isRecurring()) {
				/* list of recurring invoice for given invoice */
				List<RecurringInvoice> recurringInvoices = recurringInvoiceService.findByInvoice(invoice);
				/*
				 * removing the recurring invoice from the list for which due date is not today
				 */
				recurringInvoices.removeIf((RecurringInvoice recurringInvoice) -> LocalDate.now()
						.compareTo(recurringInvoice.getDueDate().toLocalDate()) != 0);
				/* iterating over the recurringInvoiceList */
				for (RecurringInvoice recurringInvoice : recurringInvoices) {
					/* adding the invoice to final dueInvoiceTodayList */
					dueInvoices.add(invoiceRepository.findById(recurringInvoice.getInvoice().getId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + recurringInvoice.getInvoice().getId())));
				}
			}
			/* if invoice is regular ----> isInvoiceIsRecurring = false */
			if (!invoice.isRecurring()) {
				/* list of regular invoice for given invoice */
				List<RegularInvoice> regularInvoices = regularInvoiceService.findByInvoice(invoice);
				/*
				 * removing the regular invoice from the list for which due date is not today
				 */
				regularInvoices.removeIf((RegularInvoice regularInvoice) -> LocalDate.now()
						.compareTo(regularInvoice.getDueDate().toLocalDate()) != 0);
				/* iterating over the regularInvoiceList */
				for (RegularInvoice regularInvoice : regularInvoices) {
					/* adding the invoice to final dueInvoiceTodayList */
					dueInvoices.add(invoiceRepository.findById(regularInvoice.getInvoice().getId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + regularInvoice.getInvoice().getId())));
				}
			}
		}
		/*
		 * removing the invoice from list which are locked and then, list will only
		 * contain existing invoice s
		 */
		dueInvoices.removeIf(Invoice::isLocked);
		return dueInvoices;
	}

	@Override
	public List<Invoice> getDueInvoicesForYesterday() throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {
		/* list of invoice one whose due date was yesterday */
		ArrayList<Invoice> dueInvoices = new ArrayList<>();

		/* list of invoice */
		List<Invoice> invoices = invoiceRepository.findAll();

		/* iterating over the invoice list */
		for (Invoice invoice : invoices) {
			/* if invoice is recurring ----> isInvoiceIsRecurring = true */
			if (invoice.isRecurring()) {
				/* list of recurring invoice for given invoice */
				List<RecurringInvoice> recurringInvoices = recurringInvoiceService.findByInvoice(invoice);
				/*
				 * removing the recurring invoice from the list for which due date was not
				 * yesterday
				 */
				recurringInvoices.removeIf((RecurringInvoice recurringInvoice) -> LocalDate.now().minusDays(1)
						.compareTo(recurringInvoice.getDueDate().toLocalDate()) != 0);
				/* iterating over the recurring invoice list */
				for (RecurringInvoice recurringInvoice : recurringInvoices) {
					/* adding the invoice in dueInvoiceYesterdayList */
					dueInvoices.add(invoiceRepository.findById(recurringInvoice.getInvoice().getId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + recurringInvoice.getInvoice().getId())));
				}
			}
			/* if invoice is regular ----> isInvoiceIsRecurring = false */
			if (!invoice.isRecurring()) {
				/* list of regular invoice for given invoice */
				List<RegularInvoice> regularInvoices = regularInvoiceService.findByInvoice(invoice);
				/*
				 * removing the regular invoice from the list for which due date was not
				 * yesterday
				 */
				regularInvoices.removeIf((RegularInvoice regularInvoice) -> LocalDate.now().minusDays(1)
						.compareTo(regularInvoice.getDueDate().toLocalDate()) != 0);
				/* iterating over the regular invoice list */
				for (RegularInvoice regularInvoice : regularInvoices) {
					/* adding the invoice in dueInvoiceYesterdayList */
					dueInvoices.add(invoiceRepository.findById(regularInvoice.getInvoice().getId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + regularInvoice.getInvoice().getId())));
				}
			}
			/*
			 * changing the payment status of invoice to DUE as the due date was yesterday
			 */
			updatePaymentStatus(invoice.getId(), PaymentStatus.PAYMENT_DUE);
		}
		/*
		 * removing the invoice from list which are locked and then, list will only
		 * contain existing invoice s
		 */
		dueInvoices.removeIf(Invoice::isLocked);
		return dueInvoices;
	}

	@Override
	public List<Invoice> getDueInvoicesForPastweek() throws InvoiceNotFoundException {
		/* list of invoice one whose due date is today */
		ArrayList<Invoice> dueInvoices = new ArrayList<>();
		/* list of invoice */
		List<Invoice> invoices = invoiceRepository.findAll();
		for (Invoice invoice : invoices) {
			/* if invoice is recurring ----> isInvoiceIsRecurring = true */
			if (invoice.isRecurring()) {

				/* list of recurring invoice for given invoice */
				List<RecurringInvoice> recurringInvoices = recurringInvoiceService.findByInvoice(invoice);
				/*
				 * removing the recurring invoice from the list for which due date is not past
				 * week
				 */
				recurringInvoices.removeIf((RecurringInvoice recurringInvoice) -> LocalDate.now().minusDays(7)
						.compareTo(recurringInvoice.getDueDate().toLocalDate()) != 0);
				/* iterating over the recurringInvoiceList */
				for (RecurringInvoice recurringInvoice : recurringInvoices) {
					/* adding the invoice to final dueInvoicePastWeekList */
					dueInvoices.add(invoiceRepository.findById(recurringInvoice.getInvoice().getId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + recurringInvoice.getInvoice().getId())));
				}
			}
			/* if invoice is regular ----> isInvoiceIsRecurring = false */
			if (!invoice.isRecurring()) {
				/* list of regular invoice for given invoice */
				List<RegularInvoice> regularInvoices = regularInvoiceService.findByInvoice(invoice);
				/*
				 * removing the regular invoice from the list for which due date is not past
				 * week
				 */
				regularInvoices.removeIf((RegularInvoice regularInvoice) -> LocalDate.now().minusDays(7)
						.compareTo(regularInvoice.getDueDate().toLocalDate()) != 0);
				/* iterating over the regularInvoiceList */
				for (RegularInvoice regularInvoice : regularInvoices) {
					/* adding the invoice to final dueInvoicePastWeekList */
					dueInvoices.add(invoiceRepository.findById(regularInvoice.getInvoice().getId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + regularInvoice.getInvoice().getId())));
				}
			}
		}
		/*
		 * removing the invoice from list which are locked and then, list will only
		 * contain existing invoice
		 */
		dueInvoices.removeIf(Invoice::isLocked);
		return dueInvoices;
	}

	@Override
	public ByteArrayInputStream generatePDF(long invoiceId) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {
		/* extracting the invoice with the help of given invoice id */
		Invoice invoice = invoiceRepository.findById(invoiceId)
				.orElseThrow(() -> new InvoiceNotFoundException("Invoice not found :: " + invoiceId));
		/* extracting the items of invoice with the help of given invoice id */
		List<InvoiceItem> invoiceItems = invoiceItemService.findByInvoiceId(invoiceId);

		/* if invoice is recurring ----> isInvoiceIsRecurring = true */
		LocalDate dueDate = null;
		if (invoice.isRecurring()) {
			/* list of recurring invoice for given user */
			List<RecurringInvoice> recurringInvoices = recurringInvoiceService.findByInvoiceSortByDueDateDesc(invoice);
			/* extracting the due date from the recurring invoice list */
			dueDate = recurringInvoices.get(0).getDueDate().toLocalDate();
		} else {
			/* if invoice is regular ----> isInvoiceIsRecurring = false */
			/* extracting the due date from the regular invoice */
			dueDate = regularInvoiceService.findByInvoice(invoice).get(0).getDueDate().toLocalDate();
		}
		/*
		 * extracting the properties specified in organization of user given in invoice
		 */
		String organizationName = organizationService
				.findById(userOrganizationService.findByUserId(invoice.getUser().getId()).getOrganization().getId())
				.getName();
		String organizationImageName = organizationService
				.findById(userOrganizationService.findByUserId(invoice.getUser().getId()).getOrganization().getId())
				.getLogo();
		String organizationDateFormat = organizationService
				.findById(userOrganizationService.findByUserId(invoice.getUser().getId()).getOrganization().getId())
				.getDateFormat();
		String organizationCurrency = organizationService
				.findById(userOrganizationService.findByUserId(invoice.getUser().getId()).getOrganization().getId())
				.getCurrency();
		File organizationImage = null;
		try {
			organizationImage = fileStorageService.loadFileAsResource(organizationImageName).getFile();
		} catch (Exception exception) {
			organizationImageName = "defaultLogo.png";
			organizationImage = fileStorageService.loadFileAsResource(organizationImageName).getFile();
		}
		return PdfGeneration.generatePDF(invoice, invoiceItems, organizationName, dueDate, organizationImage,
				organizationDateFormat, organizationCurrency);
	}

	@Override
	public String sentInvoiceInMail(long invoiceId) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {
		/* extracting the invoice with the help of given invoice id */
		Invoice invoice = findById(invoiceId);
		/* sending the mail of invoice */
		boolean mailFlag = mailService.sendInvoiceMail(invoice);
		/* if the mail is successfully sent */
		String result = null;
		if (mailFlag) {
			/* update the invoice payment status to SENT */
			updatePaymentStatus(invoiceId, PaymentStatus.PAYMENT_SENT);
			result = "Invoice send succesfully";
		}
		return result;
	}

	@Override
	public InvoiceModel convertToInvoiceModel(Invoice invoice, String dateFormat) {
		return new InvoiceModel(invoice, dateFormat);
	}

	@Override
	public List<InvoiceModel> convertToInvoiceModels(List<Invoice> invoices, String dateFormat) {
		List<InvoiceModel> invoiceModels = new ArrayList<>();
		for (Invoice invoice : invoices) {
			invoiceModels.add(new InvoiceModel(invoice, dateFormat));
		}
		return invoiceModels;
	}
}