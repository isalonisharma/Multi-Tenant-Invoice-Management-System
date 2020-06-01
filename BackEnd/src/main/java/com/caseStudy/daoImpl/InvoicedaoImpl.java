package com.caseStudy.daoImpl;

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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caseStudy.bean.CreateInvoiceBean;
import com.caseStudy.bean.UpdateInvoiceBean;
import com.caseStudy.dao.Invoicedao;
import com.caseStudy.dto.InvoiceDTO;
import com.caseStudy.enumeration.PaymentStatus;
import com.caseStudy.exception.ClientNotFoundException;
import com.caseStudy.exception.InvoiceItemNotFoundException;
import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.ItemNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.RecurringInvoiceNotFoundException;
import com.caseStudy.exception.RegularInvoiceNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.AuditModel;
import com.caseStudy.model.ClientModel;
import com.caseStudy.model.InvoiceItemModel;
import com.caseStudy.model.InvoiceModel;
import com.caseStudy.model.InvoiceOrganizationModel;
import com.caseStudy.model.ItemModel;
import com.caseStudy.model.RecurringInvoiceModel;
import com.caseStudy.model.RegularInvoiceModel;
import com.caseStudy.model.UserModel;
import com.caseStudy.repository.InvoiceRepository;
import com.caseStudy.service.AuditService;
import com.caseStudy.service.ClientService;
import com.caseStudy.service.FileStorageService;
import com.caseStudy.service.InvoiceItemService;
import com.caseStudy.service.InvoiceOrganizationService;
import com.caseStudy.service.ItemService;
import com.caseStudy.service.MailService;
import com.caseStudy.service.OrganizationService;
import com.caseStudy.service.RecurringInvoiceService;
import com.caseStudy.service.RegularInvoiceService;
import com.caseStudy.service.UserOrganizationService;
import com.caseStudy.service.UserService;
import com.caseStudy.utility.PdfGeneration;
import com.itextpdf.text.DocumentException;

@Repository
public class InvoicedaoImpl implements Invoicedao {
	static final Logger logger = Logger.getLogger(InvoicedaoImpl.class);

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
	public List<InvoiceModel> getAllInvoiceModels() throws SQLException {
		logger.info("InvoicedaoImpl--->>getAllInvoiceModels--->>Start");

		List<InvoiceModel> invoiceModelList = invoiceRepository.findAll();

		invoiceModelList.removeIf((InvoiceModel invoiceModel) -> invoiceModel.isInvoiceIsLocked());

		logger.info("InvoicedaoImpl--->>getAllInvoiceModels--->>End");

		return invoiceModelList;
	}

	@Override
	public List<InvoiceModel> getAllInvoiceModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException {
		logger.info("InvoicedaoImpl--->>getAllInvoiceModelByOrganizationId--->>Start");

		List<InvoiceOrganizationModel> invoiceOrganizationModelList = invoiceOrganizationService
				.getAllInvoiceOrganizationModelByOrganizationId(organizationId);

		ArrayList<InvoiceModel> invoiceModelList = new ArrayList<InvoiceModel>();

		for (InvoiceOrganizationModel invoiceOrganizationModel : invoiceOrganizationModelList) {
			invoiceModelList.add(invoiceOrganizationModel.getInvoiceModel());
		}

		invoiceModelList.removeIf((InvoiceModel invoiceModel) -> invoiceModel.isInvoiceIsLocked());

		logger.info("InvoicedaoImpl--->>getAllInvoiceModelByOrganizationId--->>End");

		return invoiceModelList;
	}

	@Override
	public InvoiceModel getInvoiceModelById(Long invoiceId) throws InvoiceNotFoundException {
		logger.info("InvoicedaoImpl--->>getInvoiceModelById--->>Start");

		InvoiceModel invoiceModel = invoiceRepository.findById(invoiceId)
				.orElseThrow(() -> new InvoiceNotFoundException("Invoice not found :: " + invoiceId));

		if (invoiceModel.isInvoiceIsLocked()) {
			throw new InvoiceNotFoundException("Invoice not found :: " + invoiceId);
		}

		logger.info("InvoicedaoImpl--->>getInvoiceModelById--->>End");

		return invoiceModel;
	}

	@Override
	public List<InvoiceModel> getInvoiceModelsByUserId(long userId) throws UserNotFoundException {
		logger.info("InvoicedaoImpl--->>getInvoiceModelsByUserId--->>Start");

		UserModel userModel = userService.getUserModelById(userId);

		List<InvoiceModel> invoiceModelList = invoiceRepository.findByuserModel(userModel);

		invoiceModelList.removeIf((InvoiceModel invoiceModel) -> invoiceModel.isInvoiceIsLocked());

		logger.info("InvoicedaoImpl--->>getInvoiceModelsByUserId--->>End");

		return invoiceModelList;
	}

	@Override
	public List<InvoiceModel> getInvoiceModelsByClientId(long clientId) throws ClientNotFoundException {
		logger.info("InvoicedaoImpl--->>getInvoiceModelsByClientId--->>Start");

		ClientModel clientModel = clientService.getClientModelById(clientId);

		List<InvoiceModel> invoiceModelList = invoiceRepository.findByclientModel(clientModel);

		logger.info("InvoicedaoImpl--->>getInvoiceModelsByClientId--->>End");

		return invoiceModelList;
	}

	@Override
	public List<InvoiceModel> getInvoiceModelsRecurringByUserId(long userId) throws UserNotFoundException {
		logger.info("InvoicedaoImpl--->>getInvoiceModelsRecurringByUserId--->>Start");

		UserModel userModel = userService.getUserModelById(userId);

		// list of invoice models of given user id
		List<InvoiceModel> invoiceModelRecurringList = invoiceRepository.findByuserModel(userModel);

		// removing the invoice model from list which are regular and then,
		// list will only contain the invoice models which are recurring
		invoiceModelRecurringList.removeIf((InvoiceModel invoiceModel) -> !invoiceModel.isInvoiceIsRecurring());

		// removing the invoice model from list which are locked and then,
		// list will only contain existing invoice models
		invoiceModelRecurringList.removeIf((InvoiceModel invoiceModel) -> invoiceModel.isInvoiceIsLocked());

		logger.info("InvoicedaoImpl--->>getInvoiceModelsRecurringByUserId--->>End");

		return invoiceModelRecurringList;
	}

	@Override
	public List<InvoiceModel> getInvoiceModelsRegularByUserId(long userId) throws UserNotFoundException {
		logger.info("InvoicedaoImpl--->>getInvoiceModelsRegularByUserId--->>Start");

		UserModel userModel = userService.getUserModelById(userId);

		// list of invoice models of given user id
		List<InvoiceModel> invoiceModelRegularList = invoiceRepository.findByuserModel(userModel);

		// removing the invoice model from list which are recurring and then,
		// list will only contain the invoice models which are regular
		invoiceModelRegularList.removeIf((InvoiceModel invoiceModel) -> invoiceModel.isInvoiceIsRecurring());

		// removing the invoice model from list which are locked and then,
		// list will only contain existing invoice models
		invoiceModelRegularList.removeIf((InvoiceModel invoiceModel) -> invoiceModel.isInvoiceIsLocked());

		logger.info("InvoicedaoImpl--->>getInvoiceModelsRegularByUserId--->>End");

		return invoiceModelRegularList;
	}

	@Override
	public List<InvoiceModel> getAllInvoiceModelsRecurring(long organizationId) throws OrganizationNotFoundException {
		logger.info("InvoicedaoImpl--->>getAllInvoiceModelsRecurring--->>Start");

		// list of invoice organization models of given organizationId
		List<InvoiceOrganizationModel> invoiceOrganizationModelList = invoiceOrganizationService
				.getAllInvoiceOrganizationModelByOrganizationId(organizationId);

		ArrayList<InvoiceModel> invoiceModelRecurringList = new ArrayList<InvoiceModel>();

		// extracting the invoice models
		for (InvoiceOrganizationModel invoiceOrganizationModel : invoiceOrganizationModelList) {
			invoiceModelRecurringList.add(invoiceOrganizationModel.getInvoiceModel());
		}

		// removing the invoice model from list which are regular and then,
		// list will only contain the invoice models which are recurring
		invoiceModelRecurringList.removeIf((InvoiceModel invoiceModel) -> !invoiceModel.isInvoiceIsRecurring());

		// removing the invoice model from list which are locked and then,
		// list will only contain existing invoice models
		invoiceModelRecurringList.removeIf((InvoiceModel invoiceModel) -> invoiceModel.isInvoiceIsLocked());

		logger.info("InvoicedaoImpl--->>getAllInvoiceModelsRecurring--->>End");

		return invoiceModelRecurringList;
	}

	@Override
	public List<InvoiceModel> getAllInvoiceModelsRegular(long organizationId) throws OrganizationNotFoundException {
		logger.info("InvoicedaoImpl--->>getAllInvoiceModelsRegular--->>Start");

		// list of invoice organization models of given organizationId
		List<InvoiceOrganizationModel> invoiceOrganizationModelList = invoiceOrganizationService
				.getAllInvoiceOrganizationModelByOrganizationId(organizationId);

		ArrayList<InvoiceModel> invoiceModelRegularList = new ArrayList<InvoiceModel>();

		// extracting the invoice models
		for (InvoiceOrganizationModel invoiceOrganizationModel : invoiceOrganizationModelList) {
			invoiceModelRegularList.add(invoiceOrganizationModel.getInvoiceModel());
		}

		// removing the invoice model from list which are recurring and then,
		// list will only contain the invoice models which are regular
		invoiceModelRegularList.removeIf((InvoiceModel invoiceModel) -> invoiceModel.isInvoiceIsRecurring());

		// removing the invoice model from list which are locked and then,
		// list will only contain existing invoice models
		invoiceModelRegularList.removeIf((InvoiceModel invoiceModel) -> invoiceModel.isInvoiceIsLocked());

		logger.info("InvoicedaoImpl--->>getAllInvoiceModelsRegular--->>End");

		return invoiceModelRegularList;
	}

	@Override
	public InvoiceModel createInvoiceModel(CreateInvoiceBean invoiceBean)
			throws UserNotFoundException, ClientNotFoundException, ItemNotFoundException, InvoiceNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {
		logger.info("InvoicedaoImpl--->>createInvoiceModel--->>Start");

		// finding the user model of given user id
		UserModel userModel = userService.getUserModelById(invoiceBean.getUserId());

		// finding the client model of given client id
		ClientModel clientModel = clientService.getClientModelById(invoiceBean.getClientId());

		double totalAmount = 0;

		// taking the map of item id as key and item quantity as value from invoiceBean
		for (Map.Entry<Long, Long> itemMap : invoiceBean.getInvoiceItem().entrySet()) {

			// extracting the item model with the help of given item id
			ItemModel itemModel = itemService.getItemModelById(itemMap.getKey());

			// calculating the total amount of invoice
			totalAmount += itemModel.getItemRate() * itemMap.getValue();
		}

		// creating the invoice
		InvoiceModel createdInvoice = invoiceRepository.save(new InvoiceModel(userModel, clientModel,
				LocalDateTime.now(), invoiceBean.isInvoiceIsRecurring(), totalAmount, invoiceBean.isInvoiceIsLocked()));

		logger.info("InvoicedaoImpl--->>createInvoiceModel--->>Invoice created Successfully.");

		// entry in the invoice item table
		for (Map.Entry<Long, Long> itemMap : invoiceBean.getInvoiceItem().entrySet()) {
			ItemModel itemModel = itemService.getItemModelById(itemMap.getKey());

			// creating the invoice item table entry of specific item
			invoiceItemService.createInvoiceItemModel(new InvoiceItemModel(createdInvoice, itemModel,
					itemMap.getValue(), itemModel.getItemRate() * itemMap.getValue()));
		}

		if (createdInvoice.isInvoiceIsRecurring()) {
			// entry in the recurring invoice table
			boolean check = recurringInvoiceService.createRecurringInvoiceModel(createdInvoice,
					invoiceBean.getPaymentStatus(), invoiceBean.getDueDate(), invoiceBean.getRenewDate(),
					invoiceBean.getRecurringPeriod() - 1);

			if (check) {
				// entry in the audit table
				auditService.createAuditModel(
						new AuditModel(createdInvoice, LocalDateTime.now(), PaymentStatus.PAYMENT_DRAFT,
								"Invoice Number :" + createdInvoice.getInvoiceId() + " created succesfully."));

				logger.info("InvoicedaoImpl--->>createInvoiceModel--->>Invoice Audit entry created Successfully.");
			}
		} else {
			// entry in the regular invoice table
			boolean check = regularInvoiceService.createRegularInvoiceModel(createdInvoice,
					invoiceBean.getPaymentStatus(), invoiceBean.getDueDate());

			logger.info("InvoicedaoImpl--->>createInvoiceModel--->>Regular Invoice created Successfully.");

			if (check) {
				// entry in the audit table
				auditService.createAuditModel(
						new AuditModel(createdInvoice, LocalDateTime.now(), PaymentStatus.PAYMENT_DRAFT,
								"Invoice Number :" + createdInvoice.getInvoiceId() + " created succesfully."));

				logger.info("InvoicedaoImpl--->>createInvoiceModel--->>Invoice Audit entry created Successfully.");
			}
		}

		sentInvoice(createdInvoice.getInvoiceId());

		logger.info("InvoicedaoImpl--->>createInvoiceModel--->>End");

		return createdInvoice;
	}

	@Override
	public InvoiceModel deleteInvoiceModel(Long invoiceId) throws InvoiceNotFoundException {
		logger.info("InvoicedaoImpl--->>deleteInvoiceModel--->>Start");
		InvoiceModel invoiceModel = invoiceRepository.findById(invoiceId)
				.orElseThrow(() -> new InvoiceNotFoundException("Invoice not found :: " + invoiceId));

		// invoice is locked and getting deleted for the front end user
		invoiceModel.setInvoiceIsLocked(true);

		// saving the invoice model as locked=true in the database
		final InvoiceModel deletedInvoiceModel = invoiceRepository.save(invoiceModel);

		logger.info("InvoicedaoImpl--->>deleteInvoiceModel--->>End");

		return deletedInvoiceModel;
	}

	@Override
	public List<InvoiceModel> getRecentInvoiceModels(long userId) throws UserNotFoundException {
		logger.info("InvoicedaoImpl--->>getRecentInvoiceModels--->>Start");

		// extracting the user model from given user id
		UserModel userModel = userService.getUserModelById(userId);

		// getting the top 3 recent invoices as per the date placed in descending order
		// of given user id
		List<InvoiceModel> invoiceModelList = invoiceRepository.findTop3ByuserModelOrderByDatePlacedDesc(userModel);

		// removing the invoice model from list which are locked and then,
		// list will only contain existing invoice models
		invoiceModelList.removeIf((InvoiceModel invoiceModel) -> invoiceModel.isInvoiceIsLocked());

		logger.info("InvoicedaoImpl--->>getRecentInvoiceModels--->>End");

		return invoiceModelList;
	}

	@Override
	public List<InvoiceModel> getRecurringDueInvoiceModels(long userId)
			throws UserNotFoundException, InvoiceNotFoundException {
		logger.info("InvoicedaoImpl--->>getRecurringDueInvoiceModels--->>Start");

		// list of due invoice model
		ArrayList<InvoiceModel> dueInvoiceModelList = new ArrayList<InvoiceModel>();

		// extracting the user model from given user id
		UserModel userModel = userService.getUserModelById(userId);

		// list of invoice model for given user model
		List<InvoiceModel> invoiceModelList = invoiceRepository.findByuserModel(userModel);

		int count = 0;

		for (InvoiceModel invoiceModel : invoiceModelList) {
			// if invoice model is recurring ----> isInvoiceIsRecurring = true
			if (invoiceModel.isInvoiceIsRecurring()) {

				// list of recurring invoice models for given invoice model
				List<RecurringInvoiceModel> recurringInvoiceModelList = recurringInvoiceService
						.findByInvoiceModelSortByDueDateDesc(invoiceModel);

				// removing the recurring invoice models for which payment status is not due
				recurringInvoiceModelList
						.removeIf((RecurringInvoiceModel recurringInvoiceModel) -> recurringInvoiceModel
								.getPaymentStatus() != PaymentStatus.PAYMENT_DUE);

				// iterating the recurringInvoiceModelList
				for (RecurringInvoiceModel recurringInvoiceModel : recurringInvoiceModelList) {

					// if the count is greater than or equal to 3, break the loop
					if (count >= 3) {
						break;
					}

					// extracting the invoice model for which payment status is due and is of
					// recurring type
					dueInvoiceModelList.add(invoiceRepository
							.findById(recurringInvoiceModel.getInvoiceModel().getInvoiceId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + recurringInvoiceModel.getInvoiceModel().getInvoiceId())));

					// increasing the count by 1
					count++;
				}
			}
		}

		// removing the invoice model from list which are locked and then,
		// list will only contain existing invoice models
		dueInvoiceModelList.removeIf((InvoiceModel invoiceModel) -> invoiceModel.isInvoiceIsLocked());

		logger.info("InvoicedaoImpl--->>getRecurringDueInvoiceModels--->>End");

		return dueInvoiceModelList;
	}

	@Override
	public List<InvoiceModel> getRegularDueInvoiceModels(long userId)
			throws UserNotFoundException, InvoiceNotFoundException {
		logger.info("InvoicedaoImpl--->>getRegularDueInvoiceModels--->>Start");

		// list of due invoice model
		ArrayList<InvoiceModel> dueInvoiceModelList = new ArrayList<InvoiceModel>();

		// extracting the user model from given user id
		UserModel userModel = userService.getUserModelById(userId);

		// list of invoice model for given user model
		List<InvoiceModel> invoiceModelList = invoiceRepository.findByuserModel(userModel);

		ArrayList<RegularInvoiceModel> sortedRegularInvoiceModeList = new ArrayList<RegularInvoiceModel>();

		int count = 0;
		for (InvoiceModel invoiceModel : invoiceModelList) {

			// if invoice model is regular ----> isInvoiceIsRecurring = false
			if (invoiceModel.isInvoiceIsRecurring() == false) {

				// list of regular invoice models for given invoice model
				List<RegularInvoiceModel> regularInvoiceModelList = regularInvoiceService
						.findByInvoiceModelSortByDueDateDesc(invoiceModel);

				// removing the regular invoice models for which payment status is not due
				regularInvoiceModelList.removeIf((RegularInvoiceModel regularInvoiceModel) -> regularInvoiceModel
						.getPaymentStatus() != PaymentStatus.PAYMENT_DUE);

				// adding the regularInvoiceModel from regularInvoiceList to
				// sortedRegularInvoiceModeList
				for (RegularInvoiceModel regularInvoiceModel : regularInvoiceModelList) {
					sortedRegularInvoiceModeList.add(regularInvoiceModel);
				}
			}
		}

		Collections.sort(sortedRegularInvoiceModeList, new Comparator<RegularInvoiceModel>() {
			@Override
			public int compare(RegularInvoiceModel r1, RegularInvoiceModel r2) {
				return r1.getDueDate().compareTo(r2.getDueDate());
			}
		});

		// iterating the sortedRegularInvoiceModeList
		for (RegularInvoiceModel regularInvoiceModel : sortedRegularInvoiceModeList) {

			// if the count is greater than or equal to 3, break the loop
			if (count >= 3) {
				break;
			}

			// extracting the invoice model for which payment status is due and is of
			// regular type
			dueInvoiceModelList.add(invoiceRepository.findById(regularInvoiceModel.getInvoiceModel().getInvoiceId())
					.orElseThrow(() -> new InvoiceNotFoundException(
							"Invoice not found :: " + regularInvoiceModel.getInvoiceModel().getInvoiceId())));

			// increasing the count by 1
			count++;
		}

		// removing the invoice model from list which are locked and then,
		// list will only contain existing invoice models
		dueInvoiceModelList.removeIf((InvoiceModel invoiceModel) -> invoiceModel.isInvoiceIsLocked());

		logger.info("InvoicedaoImpl--->>getRegularDueInvoiceModels--->>End");

		return dueInvoiceModelList;
	}

	@Override
	public List<InvoiceModel> getDueInvoiceModelsTomorrow() throws InvoiceNotFoundException {
		logger.info("InvoicedaoImpl--->>getDueInvoiceModelsTomorrow--->>Start");

		// list of invoice model one whose due date is tomorrow
		ArrayList<InvoiceModel> dueInvoiceModelTomorrowList = new ArrayList<InvoiceModel>();

		// list of invoice model
		List<InvoiceModel> invoiceModelList = invoiceRepository.findAll();

		for (InvoiceModel invoiceModel : invoiceModelList) {

			// if invoice model is recurring ----> isInvoiceIsRecurring = true
			if (invoiceModel.isInvoiceIsRecurring()) {

				// list of recurring invoice models for given invoice model
				List<RecurringInvoiceModel> recurringInvoiceModelList = recurringInvoiceService
						.findByInvoiceModel(invoiceModel);

				// removing the recurring invoice model from the list for which due date is not
				// tomorrow
				recurringInvoiceModelList.removeIf((RecurringInvoiceModel recurringInvoiceModel) -> LocalDate.now()
						.plusDays(1).compareTo(recurringInvoiceModel.getDueDate().toLocalDate()) != 0);

				// iterating over the recurringInvoiceModelList
				for (RecurringInvoiceModel recurringInvoiceModel : recurringInvoiceModelList) {

					// adding the invoice model to final dueInvoiceModelTomorrowList
					dueInvoiceModelTomorrowList.add(invoiceRepository
							.findById(recurringInvoiceModel.getInvoiceModel().getInvoiceId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + recurringInvoiceModel.getInvoiceModel().getInvoiceId())));
				}
			}

			// if invoice model is regular ----> isInvoiceIsRecurring = false
			if (!invoiceModel.isInvoiceIsRecurring()) {

				// list of regular invoice models for given invoice model
				List<RegularInvoiceModel> regularInvoiceModelList = regularInvoiceService
						.findByInvoiceModel(invoiceModel);

				// removing the regular invoice model from the list for which due date is not
				// tomorrow
				regularInvoiceModelList.removeIf((RegularInvoiceModel regularInvoiceModel) -> LocalDate.now()
						.plusDays(1).compareTo(regularInvoiceModel.getDueDate().toLocalDate()) != 0);

				// iterating over the regularInvoiceModelList
				for (RegularInvoiceModel regularInvoiceModel : regularInvoiceModelList) {

					// adding the invoice model to final dueInvoiceModelTomorrowList
					dueInvoiceModelTomorrowList.add(invoiceRepository
							.findById(regularInvoiceModel.getInvoiceModel().getInvoiceId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + regularInvoiceModel.getInvoiceModel().getInvoiceId())));
				}
			}
		}

		// removing the invoice model from list which are locked and then,
		// list will only contain existing invoice models
		dueInvoiceModelTomorrowList.removeIf((InvoiceModel invoiceModel) -> invoiceModel.isInvoiceIsLocked());

		logger.info("InvoicedaoImpl--->>getDueInvoiceModelsTomorrow--->>End");

		return dueInvoiceModelTomorrowList;
	}

	@Override
	public List<InvoiceModel> getDueInvoiceModelsToday() throws InvoiceNotFoundException {
		logger.info("InvoicedaoImpl--->>getDueInvoiceModelsToday--->>Start");

		// list of invoice model one whose due date is today
		ArrayList<InvoiceModel> dueInvoiceModelTodayList = new ArrayList<InvoiceModel>();

		// list of invoice model
		List<InvoiceModel> invoiceModelList = invoiceRepository.findAll();

		for (InvoiceModel invoiceModel : invoiceModelList) {

			// if invoice model is recurring ----> isInvoiceIsRecurring = true
			if (invoiceModel.isInvoiceIsRecurring()) {

				// list of recurring invoice models for given invoice model
				List<RecurringInvoiceModel> recurringInvoiceModelList = recurringInvoiceService
						.findByInvoiceModel(invoiceModel);

				// removing the recurring invoice model from the list for which due date is not
				// today
				recurringInvoiceModelList.removeIf((RecurringInvoiceModel recurringInvoiceModel) -> LocalDate.now()
						.compareTo(recurringInvoiceModel.getDueDate().toLocalDate()) != 0);

				// iterating over the recurringInvoiceModelList
				for (RecurringInvoiceModel recurringInvoiceModel : recurringInvoiceModelList) {

					// adding the invoice model to final dueInvoiceModelTodayList
					dueInvoiceModelTodayList.add(invoiceRepository
							.findById(recurringInvoiceModel.getInvoiceModel().getInvoiceId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + recurringInvoiceModel.getInvoiceModel().getInvoiceId())));
				}
			}

			// if invoice model is regular ----> isInvoiceIsRecurring = false
			if (!invoiceModel.isInvoiceIsRecurring()) {

				// list of regular invoice models for given invoice model
				List<RegularInvoiceModel> regularInvoiceModelList = regularInvoiceService
						.findByInvoiceModel(invoiceModel);

				// removing the regular invoice model from the list for which due date is not
				// today
				regularInvoiceModelList.removeIf((RegularInvoiceModel regularInvoiceModel) -> LocalDate.now()
						.compareTo(regularInvoiceModel.getDueDate().toLocalDate()) != 0);

				// iterating over the regularInvoiceModelList
				for (RegularInvoiceModel regularInvoiceModel : regularInvoiceModelList) {

					// adding the invoice model to final dueInvoiceModelTodayList
					dueInvoiceModelTodayList.add(invoiceRepository
							.findById(regularInvoiceModel.getInvoiceModel().getInvoiceId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + regularInvoiceModel.getInvoiceModel().getInvoiceId())));
				}
			}
		}

		// removing the invoice model from list which are locked and then,
		// list will only contain existing invoice models
		dueInvoiceModelTodayList.removeIf((InvoiceModel invoiceModel) -> invoiceModel.isInvoiceIsLocked());

		logger.info("InvoicedaoImpl--->>getDueInvoiceModelsToday--->>End");

		return dueInvoiceModelTodayList;
	}

	@Override
	public List<InvoiceModel> getDueInvoiceModelsPastweek() throws InvoiceNotFoundException {
		logger.info("InvoicedaoImpl--->>getDueInvoiceModelsPastweek--->>Start");

		// list of invoice model one whose due date is today
		ArrayList<InvoiceModel> dueInvoiceModelPastWeekList = new ArrayList<InvoiceModel>();

		// list of invoice model
		List<InvoiceModel> invoiceModelList = invoiceRepository.findAll();

		for (InvoiceModel invoiceModel : invoiceModelList) {

			// if invoice model is recurring ----> isInvoiceIsRecurring = true
			if (invoiceModel.isInvoiceIsRecurring()) {

				// list of recurring invoice models for given invoice model
				List<RecurringInvoiceModel> recurringInvoiceModelList = recurringInvoiceService
						.findByInvoiceModel(invoiceModel);

				// removing the recurring invoice model from the list for which due date is not
				// past week
				recurringInvoiceModelList.removeIf((RecurringInvoiceModel recurringInvoiceModel) -> LocalDate.now()
						.minusDays(7).compareTo(recurringInvoiceModel.getDueDate().toLocalDate()) != 0);

				// iterating over the recurringInvoiceModelList
				for (RecurringInvoiceModel recurringInvoiceModel : recurringInvoiceModelList) {

					// adding the invoice model to final dueInvoiceModelPastWeekList
					dueInvoiceModelPastWeekList.add(invoiceRepository
							.findById(recurringInvoiceModel.getInvoiceModel().getInvoiceId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + recurringInvoiceModel.getInvoiceModel().getInvoiceId())));
				}
			}

			// if invoice model is regular ----> isInvoiceIsRecurring = false
			if (!invoiceModel.isInvoiceIsRecurring()) {

				// list of regular invoice models for given invoice model
				List<RegularInvoiceModel> regularInvoiceModelList = regularInvoiceService
						.findByInvoiceModel(invoiceModel);

				// removing the regular invoice model from the list for which due date is not
				// past week
				regularInvoiceModelList.removeIf((RegularInvoiceModel regularInvoiceModel) -> LocalDate.now()
						.minusDays(7).compareTo(regularInvoiceModel.getDueDate().toLocalDate()) != 0);

				// iterating over the regularInvoiceModelList
				for (RegularInvoiceModel regularInvoiceModel : regularInvoiceModelList) {

					// adding the invoice model to final dueInvoiceModelPastWeekList
					dueInvoiceModelPastWeekList.add(invoiceRepository
							.findById(regularInvoiceModel.getInvoiceModel().getInvoiceId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + regularInvoiceModel.getInvoiceModel().getInvoiceId())));
				}
			}
		}

		// removing the invoice model from list which are locked and then,
		// list will only contain existing invoice models
		dueInvoiceModelPastWeekList.removeIf((InvoiceModel invoiceModel) -> invoiceModel.isInvoiceIsLocked());

		logger.info("InvoicedaoImpl--->>getDueInvoiceModelsPastweek--->>End");

		return dueInvoiceModelPastWeekList;
	}

	@Override
	public String updatePaymentStatus(long invoiceId, PaymentStatus paymentStatus)
			throws InvoiceNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserOrganizationNotFoundException, IOException, DocumentException {
		logger.info("InvoicedaoImpl--->>updatePaymentStatus--->>Start");

		boolean payment = false, organizationThankYouMail = false;
		String result = null;

		// finding the invoice model with the help of given invoice id
		InvoiceModel invoiceModel = invoiceRepository.findById(invoiceId)
				.orElseThrow(() -> new InvoiceNotFoundException("Invoice not found :: " + invoiceId));

		if (invoiceModel.isInvoiceIsRecurring()) {

			// if invoice model is recurring ----> isInvoiceIsRecurring = true
			payment = recurringInvoiceService.updatePaymentStatus(invoiceModel, paymentStatus);
		} else if (!invoiceModel.isInvoiceIsRecurring()) {

			// if invoice model is regular ----> isInvoiceIsRecurring = false
			payment = regularInvoiceService.updatePaymentStatus(invoiceModel, paymentStatus);
		}

		// entry in the audit table
		auditService.createAuditModel(new AuditModel(invoiceModel, LocalDateTime.now(), paymentStatus,
				"Invoice Number :" + invoiceModel.getInvoiceId() + " Payment status updated"));

		// checking the payment is true or not
		if (payment) {

			// if the payment status is changed to PAID
			if (paymentStatus == PaymentStatus.PAYMENT_PAID) {

				// sending the thank you mail from the organization
				organizationThankYouMail = organizationService.getOrganizationModelById(userOrganizationService
						.getUserOrganizationModelByUserId(invoiceModel.getUserModel().getUserId())
						.getOrganizationModel().getOrganizationId()).isOrganizationThankYouMail();

				if (organizationThankYouMail) {

					mailService.sendThankyouMail(invoiceModel);
				}

				result = "Payment Status Updated Succesfully and Thank you mail sent";
			}

			result = "Payment Status Updated Succesfully";
		}

		result = "Payment Status Update Failed";

		logger.info("InvoicedaoImpl--->>updatePaymentStatus--->>End");

		// sending the response back to the calling function
		return result;
	}

	@Override
	public InvoiceModel updateInvoiceModel(UpdateInvoiceBean invoiceBean) throws InvoiceNotFoundException,
			UserNotFoundException, ClientNotFoundException, InvoiceItemNotFoundException, ItemNotFoundException,
			RecurringInvoiceNotFoundException, RegularInvoiceNotFoundException {

		logger.info("InvoicedaoImpl--->>updateInvoiceModel--->>Start");

		// finding the current invoice model with the help of invoice id
		InvoiceModel currentInvoiceModel = invoiceRepository.findById(invoiceBean.getInvoiceId())
				.orElseThrow(() -> new InvoiceNotFoundException("Invoice not found :: " + invoiceBean.getInvoiceId()));

		// checking the current Invoice Model containing user model is same or different
		// with the help of user id
		if (currentInvoiceModel.getUserModel().getUserId() != invoiceBean.getUserId()) {

			// if the user model is different
			// finding the user model with the help of given user id
			UserModel userModel = userService.getUserModelById(invoiceBean.getUserId());

			// setting the new user model in current invoice model
			currentInvoiceModel.setUserModel(userModel);

			logger.info("InvoicedaoImpl--->>updateInvoiceModel--->>User Model updated Successfully.");
		}

		// checking the current Invoice Model containing client model is same or
		// different with the help of client id
		if (currentInvoiceModel.getClientModel().getClientId() != invoiceBean.getClientId()) {

			// if the client model is different
			// finding the client model with the help of given client id
			ClientModel clientModel = clientService.getClientModelById(invoiceBean.getClientId());

			// setting the new client model in current invoice model
			currentInvoiceModel.setClientModel(clientModel);

			logger.info("InvoicedaoImpl--->>updateInvoiceModel--->>Client Model updated Successfully.");
		}

		// changing the date placed of current invoice to today's date
		currentInvoiceModel.setDatePlaced(LocalDateTime.now());

		// changing the currentInvoiceModel {isInvoiceLocked} property to invoiceBean
		// {isInvoiceIsLocked} property
		currentInvoiceModel.setInvoiceIsLocked(invoiceBean.isInvoiceIsLocked());

		// extracting the invoice item list with the help of given invoice id
		List<InvoiceItemModel> invoiceItemModelList = invoiceItemService
				.findByinvoiceId(currentInvoiceModel.getInvoiceId());

		// iterating over the invoiceItemModelList
		for (InvoiceItemModel invoiceItemModel : invoiceItemModelList) {

			// deleting all the entries from the invoice item table of given invoice id so
			// that the new can be saved
			invoiceItemService.deleteInvoiceItemModel(invoiceItemModel.getInvoiceItemId());
		}

		double amount = 0;

		// iterating over the invoiceBean {InvoiceItem} Map
		for (Map.Entry<Long, Long> entry : invoiceBean.getInvoiceItem().entrySet()) {

			// extracting the item model with the help of given item id
			ItemModel itemModel = itemService.getItemModelById(entry.getKey());

			// extracting the quantity and rate from the entry
			long quantity = entry.getValue();
			float rate = itemModel.getItemRate();

			// calculating the amount
			amount += rate * quantity;

			// adding the entry in the invoice item table of current invoice id item's
			invoiceItemService.createInvoiceItemModel(
					new InvoiceItemModel(currentInvoiceModel, itemModel, quantity, rate * quantity));
		}

		// setting the current invoice model amount
		currentInvoiceModel.setAmount(amount);

		if (currentInvoiceModel.isInvoiceIsRecurring()) {

			// if current invoice model is recurring ----> isInvoiceIsRecurring = true
			List<RecurringInvoiceModel> recurringInvoiceModelList = recurringInvoiceService
					.findByInvoiceModel(currentInvoiceModel);

			// deleting all the entries from the recurringInvoiceModel table of given
			// invoice id so
			// that the new can be saved
			for (RecurringInvoiceModel recurringInvoiceModel : recurringInvoiceModelList) {

				recurringInvoiceService.deleteRecurringInvoiceModel(recurringInvoiceModel.getRecurringInvoiceId());
			}

		} else if (!currentInvoiceModel.isInvoiceIsRecurring()) {

			// if current invoice model is regular ----> isInvoiceIsRecurring = false
			List<RegularInvoiceModel> regularInvoiceModelList = regularInvoiceService
					.findByInvoiceModel(currentInvoiceModel);

			// deleting all the entries from the regularInvoiceModel table of given invoice
			// id so
			// that the new can be saved
			for (RegularInvoiceModel regularInvoiceModel : regularInvoiceModelList) {

				regularInvoiceService.deleteRegularInvoiceModel(regularInvoiceModel.getRegularInvoiceId());
			}

		}

		// set the isInvoiceIsRecurring property from invoiceBean to current invoice
		// model
		currentInvoiceModel.setInvoiceIsRecurring(invoiceBean.isInvoiceIsRecurring());

		if (currentInvoiceModel.isInvoiceIsRecurring()) {

			// if current invoice model is recurring ----> isInvoiceIsRecurring = true
			// create recurring invoice models from current invoice model
			recurringInvoiceService.createRecurringInvoiceModel(currentInvoiceModel, invoiceBean.getPaymentStatus(),
					invoiceBean.getDueDate(), invoiceBean.getRenewDate(), invoiceBean.getRecurringPeriod() - 1);
		} else {

			// if current invoice model is regular ----> isInvoiceIsRecurring = false
			// create regular invoice model from current invoice model
			regularInvoiceService.createRegularInvoiceModel(currentInvoiceModel, invoiceBean.getPaymentStatus(),
					invoiceBean.getDueDate());
		}
		logger.info("InvoicedaoImpl--->>updateInvoiceModel--->>End");

		return currentInvoiceModel;
	}

	@Override
	public ByteArrayInputStream pdfGeneration(long invoiceId) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {
		logger.info("InvoicedaoImpl--->>pdfGeneration--->>Start");

		String organizationName = null, organizationDateFormat = null, organizationCurrency = null,
				organizationImageName = null;
		LocalDate dueDate = null;
		File organizationImage = null;

		// extracting the invoice model with the help of given invoice id
		InvoiceModel invoiceModel = invoiceRepository.findById(invoiceId)
				.orElseThrow(() -> new InvoiceNotFoundException("Invoice not found :: " + invoiceId));

		// extracting the items of invoice with the help of given invoice id
		List<InvoiceItemModel> invoiceItemModelList = invoiceItemService.findByinvoiceId(invoiceId);

		// if invoice model is recurring ----> isInvoiceIsRecurring = true
		if (invoiceModel.isInvoiceIsRecurring()) {

			// list of recurring invoice model for given user model
			List<RecurringInvoiceModel> recurringInvoiceList = recurringInvoiceService
					.findByInvoiceModelSortByDueDateDesc(invoiceModel);

			// extracting the due date from the recurring invoice list
			dueDate = recurringInvoiceList.get(0).getDueDate().toLocalDate();

		} else {

			// if invoice model is regular ----> isInvoiceIsRecurring = false
			// extracting the due date from the regular invoice
			dueDate = regularInvoiceService.findByInvoiceModel(invoiceModel).get(0).getDueDate().toLocalDate();
		}

		// extracting the properties specified in organization of user model given in
		// invoice
		organizationName = organizationService.getOrganizationModelById(
				userOrganizationService.getUserOrganizationModelByUserId(invoiceModel.getUserModel().getUserId())
						.getOrganizationModel().getOrganizationId())
				.getOrganizationName();

		organizationImageName = organizationService.getOrganizationModelById(
				userOrganizationService.getUserOrganizationModelByUserId(invoiceModel.getUserModel().getUserId())
						.getOrganizationModel().getOrganizationId())
				.getOrganizationLogo();

		organizationDateFormat = organizationService.getOrganizationModelById(
				userOrganizationService.getUserOrganizationModelByUserId(invoiceModel.getUserModel().getUserId())
						.getOrganizationModel().getOrganizationId())
				.getOrganizationDate();

		organizationCurrency = organizationService.getOrganizationModelById(
				userOrganizationService.getUserOrganizationModelByUserId(invoiceModel.getUserModel().getUserId())
						.getOrganizationModel().getOrganizationId())
				.getOrganizationCurrency();

		try {

			organizationImage = fileStorageService.loadFileAsResource(organizationImageName).getFile();
		} catch (Exception exception) {

			organizationImageName = "defaultLogo.png";

			organizationImage = fileStorageService.loadFileAsResource(organizationImageName).getFile();
		}

		logger.info("InvoicedaoImpl--->>pdfGeneration--->>End");

		return PdfGeneration.invoicePdf(invoiceModel, invoiceItemModelList, organizationName, dueDate,
				organizationImage, organizationDateFormat, organizationCurrency);
	}

	@Override
	public String sentInvoice(long invoiceId) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {
		logger.info("InvoicedaoImpl--->>sentInvoice--->>Start");

		String result = null;

		// extracting the invoice model with the help of given invoice id
		InvoiceModel invoiceModel = getInvoiceModelById(invoiceId);

		// sending the mail of invoice model
		boolean mailFlag = mailService.sendInvoiceMail(invoiceModel);

		// if the mail is successfully sent
		if (mailFlag) {

			// update the invoice payment status to SENT
			updatePaymentStatus(invoiceId, PaymentStatus.PAYMENT_SENT);

			result = "Invoice send succesfully";
		}

		logger.info("InvoicedaoImpl--->>sentInvoice--->>End");

		return result;
	}

	@Override
	public List<InvoiceModel> getDueInvoiceModelsYesterday() throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {

		logger.info("InvoicedaoImpl--->>getDueInvoiceModelsYesterday--->>Start");

		// list of invoice model one whose due date was yesterday
		ArrayList<InvoiceModel> dueInvoiceModelYesterdayList = new ArrayList<InvoiceModel>();

		// list of invoice model
		List<InvoiceModel> invoiceModelList = invoiceRepository.findAll();

		// iterating over the invoice model list
		for (InvoiceModel invoiceModel : invoiceModelList) {

			// if invoice model is recurring ----> isInvoiceIsRecurring = true
			if (invoiceModel.isInvoiceIsRecurring()) {

				// list of recurring invoice models for given invoice model
				List<RecurringInvoiceModel> recurringInvoiceModelList = recurringInvoiceService
						.findByInvoiceModel(invoiceModel);

				// removing the recurring invoice model from the list for which due date was not
				// yesterday
				recurringInvoiceModelList.removeIf((RecurringInvoiceModel recurringInvoiceModel) -> LocalDate.now()
						.minusDays(1).compareTo(recurringInvoiceModel.getDueDate().toLocalDate()) != 0);

				// iterating over the recurring invoice model list
				for (RecurringInvoiceModel recurringInvoiceModel : recurringInvoiceModelList) {

					// adding the invoice model in dueInvoiceModelYesterdayList
					dueInvoiceModelYesterdayList.add(invoiceRepository
							.findById(recurringInvoiceModel.getInvoiceModel().getInvoiceId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + recurringInvoiceModel.getInvoiceModel().getInvoiceId())));
				}
			}

			// if invoice model is regular ----> isInvoiceIsRecurring = false
			if (!invoiceModel.isInvoiceIsRecurring()) {

				// list of regular invoice models for given invoice model
				List<RegularInvoiceModel> regularInvoiceModelList = regularInvoiceService
						.findByInvoiceModel(invoiceModel);

				// removing the regular invoice model from the list for which due date was not
				// yesterday
				regularInvoiceModelList.removeIf((RegularInvoiceModel regularInvoiceModel) -> LocalDate.now()
						.minusDays(1).compareTo(regularInvoiceModel.getDueDate().toLocalDate()) != 0);

				// iterating over the regular invoice model list
				for (RegularInvoiceModel regularInvoiceModel : regularInvoiceModelList) {

					// adding the invoice model in dueInvoiceModelYesterdayList
					dueInvoiceModelYesterdayList.add(invoiceRepository
							.findById(regularInvoiceModel.getInvoiceModel().getInvoiceId())
							.orElseThrow(() -> new InvoiceNotFoundException(
									"Invoice not found :: " + regularInvoiceModel.getInvoiceModel().getInvoiceId())));
				}
			}

			// changing the payment status of invoice model to DUE as the due date was
			// yesterday
			updatePaymentStatus(invoiceModel.getInvoiceId(), PaymentStatus.PAYMENT_DUE);
		}

		// removing the invoice model from list which are locked and then,
		// list will only contain existing invoice models
		dueInvoiceModelYesterdayList.removeIf((InvoiceModel invoiceModel) -> invoiceModel.isInvoiceIsLocked());

		logger.info("InvoicedaoImpl--->>getDueInvoiceModelsYesterday--->>End");

		return dueInvoiceModelYesterdayList;
	}

	@Override
	public List<InvoiceDTO> convertToInvoiceDTOList(List<InvoiceModel> invoiceModelList, String dateFormat) {
		logger.info("InvoicedaoImpl--->>convertToInvoiceDTOList--->>Start");

		List<InvoiceDTO> invoiceDTOList = new ArrayList<InvoiceDTO>();

		for (InvoiceModel invoiceModel : invoiceModelList) {

			invoiceDTOList.add(new InvoiceDTO(invoiceModel, dateFormat));
		}
		logger.info("InvoicedaoImpl--->>convertToInvoiceDTOList--->>End");

		return invoiceDTOList;
	}

	@Override
	public InvoiceDTO InvoiceModelToInvoiceDTO(InvoiceModel invoiceModel, String dateFormat) {
		logger.info("InvoicedaoImpl--->>InvoiceModelToInvoiceDTO--->>Start");

		InvoiceDTO invoiceDTO = new InvoiceDTO(invoiceModel, dateFormat);

		logger.info("InvoicedaoImpl--->>InvoiceModelToInvoiceDTO--->>End");

		return invoiceDTO;
	}
}