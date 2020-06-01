package com.caseStudy.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.caseStudy.bean.CreateInvoiceBean;
import com.caseStudy.bean.UpdateInvoiceBean;
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
import com.caseStudy.model.InvoiceModel;
import com.itextpdf.text.DocumentException;

public interface Invoicedao {
	// CRUD Operations

	// 1. Create Operation
	InvoiceModel createInvoiceModel(CreateInvoiceBean invoice)
			throws UserNotFoundException, ClientNotFoundException, ItemNotFoundException, InvoiceNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException;

	// 2. Read Operation
	InvoiceModel getInvoiceModelById(Long invoiceId) throws InvoiceNotFoundException;

	// 3. Update Operation
	InvoiceModel updateInvoiceModel(UpdateInvoiceBean invoiceBean) throws InvoiceNotFoundException,
			UserNotFoundException, ClientNotFoundException, InvoiceItemNotFoundException, ItemNotFoundException,
			RecurringInvoiceNotFoundException, RegularInvoiceNotFoundException;

	// 4. Delete Operation
	InvoiceModel deleteInvoiceModel(Long invoiceId) throws InvoiceNotFoundException;

	// Other Operations

	// Getting all entries from invoice table
	List<InvoiceModel> getAllInvoiceModels() throws SQLException;

	// Getting the entries from invoice table which are having payment status is due
	// and due date of past week
	List<InvoiceModel> getDueInvoiceModelsPastweek() throws InvoiceNotFoundException;

	// Getting the entries from invoice table which are having payment status is due
	// and due date of yesterday
	List<InvoiceModel> getDueInvoiceModelsYesterday() throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException;

	// Getting the entries from invoice table which are having payment status is due
	// and due date of tomorrow
	List<InvoiceModel> getDueInvoiceModelsTomorrow() throws InvoiceNotFoundException;

	// Getting the entries from invoice table which are having payment status is due
	// and due date of today
	List<InvoiceModel> getDueInvoiceModelsToday() throws InvoiceNotFoundException;

	// Getting the entries from invoice table of given user id
	List<InvoiceModel> getInvoiceModelsByUserId(long userId) throws UserNotFoundException;

	// Getting the entries from invoice table of given user id which are regular in
	// nature
	List<InvoiceModel> getInvoiceModelsRegularByUserId(long userId) throws UserNotFoundException;

	// Getting the entries from invoice table of given user id which are recurring
	// in nature
	List<InvoiceModel> getInvoiceModelsRecurringByUserId(long userId) throws UserNotFoundException;

	// Getting the entries from invoice table of given user id which are regular in
	// nature and their payment status is due
	List<InvoiceModel> getRegularDueInvoiceModels(long userId) throws UserNotFoundException, InvoiceNotFoundException;

	// Getting the entries from invoice table of given user id which are recurring
	// in nature and their payment status is due
	List<InvoiceModel> getRecurringDueInvoiceModels(long userId) throws UserNotFoundException, InvoiceNotFoundException;

	// Getting the entries from invoice table of given user id which are recently
	// created
	List<InvoiceModel> getRecentInvoiceModels(long userId) throws UserNotFoundException;

	// Getting the entries from invoice table of given client id
	List<InvoiceModel> getInvoiceModelsByClientId(long clientId) throws ClientNotFoundException;

	// Getting the entries from invoice table of given organization id
	List<InvoiceModel> getAllInvoiceModelByOrganizationId(long organizationId) throws OrganizationNotFoundException;

	// Getting the entries from invoice table of given organization id which are
	// regular in nature
	List<InvoiceModel> getAllInvoiceModelsRegular(long organizationId) throws OrganizationNotFoundException;

	// Getting the entries from invoice table of given organization id which are
	// recurring in nature
	List<InvoiceModel> getAllInvoiceModelsRecurring(long organizationId) throws OrganizationNotFoundException;

	// Sending the invoice details via mail to client
	String sentInvoice(long invoiceId) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException;

	// creating the invoice PDF of given invoice id
	ByteArrayInputStream pdfGeneration(long invoiceId) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException;

	// updating the payment status of specific entry in invoice table with the help
	// of given invoice id
	String updatePaymentStatus(long invoiceId, PaymentStatus paymentStatus)
			throws InvoiceNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserOrganizationNotFoundException, IOException, DocumentException;

	// Converting the invoice table entry into date transfer object (DTO)
	InvoiceDTO InvoiceModelToInvoiceDTO(InvoiceModel invoiceModel, String dateFormat);

	// Converting the invoice table entries into date transfer objects (DTO)
	List<InvoiceDTO> convertToInvoiceDTOList(List<InvoiceModel> invoiceModelList, String dateFormat);
}