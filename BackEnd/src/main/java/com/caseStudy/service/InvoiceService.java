package com.caseStudy.service;

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

/**
 * Class: InvoiceService
 * 
 * @author saloni.sharma
 */
public interface InvoiceService {
	/**
	 * Function Name: getAllInvoiceModels
	 * 
	 * @return list of invoice model
	 * 
	 * @throws SQLException
	 */
	List<InvoiceModel> getAllInvoiceModels() throws SQLException;

	/**
	 * Function Name: getInvoiceModelById
	 * 
	 * @param invoiceId
	 * 
	 * @return
	 * 
	 * @throws InvoiceNotFoundException
	 */
	InvoiceModel getInvoiceModelById(Long invoiceId) throws InvoiceNotFoundException;

	/**
	 * Function Name: getInvoiceModelsByUserId
	 * 
	 * @param userId
	 * 
	 * @return list of invoice model
	 * 
	 * @throws UserNotFoundException
	 */
	List<InvoiceModel> getInvoiceModelsByUserId(long userId) throws UserNotFoundException;

	/**
	 * Function Name: getInvoiceModelsByClientId
	 * 
	 * @param clientId
	 * 
	 * @return list of invoice model
	 * 
	 * @throws ClientNotFoundException
	 */
	List<InvoiceModel> getInvoiceModelsByClientId(long clientId) throws ClientNotFoundException;

	/**
	 * Function Name: getInvoiceModelsRecurringByUserId
	 * 
	 * @param userId
	 * 
	 * @return list of invoice model
	 * 
	 * @throws UserNotFoundException
	 */
	List<InvoiceModel> getInvoiceModelsRecurringByUserId(long userId) throws UserNotFoundException;

	/**
	 * Function Name: getInvoiceModelsRegularByUserId
	 * 
	 * @param userId
	 * 
	 * @return list of invoice model
	 * 
	 * @throws UserNotFoundException
	 */
	List<InvoiceModel> getInvoiceModelsRegularByUserId(long userId) throws UserNotFoundException;

	/**
	 * Function Name: getAllInvoiceModelsRecurring
	 * 
	 * @param organizationId
	 * 
	 * @return list of invoice model
	 * 
	 * @throws OrganizationNotFoundException
	 */
	List<InvoiceModel> getAllInvoiceModelsRecurring(long organizationId) throws OrganizationNotFoundException;

	/**
	 * Function Name: getAllInvoiceModelsRegular
	 * 
	 * @param organizationId
	 * 
	 * @return list of invoice model
	 * 
	 * @throws OrganizationNotFoundException
	 */
	List<InvoiceModel> getAllInvoiceModelsRegular(long organizationId) throws OrganizationNotFoundException;

	/**
	 * Function Name: createInvoiceModel
	 * 
	 * @param invoice
	 * 
	 * @return invoice model
	 * 
	 * @throws UserNotFoundException
	 * @throws ClientNotFoundException
	 * @throws ItemNotFoundException
	 * @throws InvoiceNotFoundException
	 * @throws OrganizationNotFoundException
	 * @throws UserOrganizationNotFoundException
	 * @throws IOException
	 * @throws DocumentException
	 */
	InvoiceModel createInvoiceModel(CreateInvoiceBean createInvoiceBean)
			throws UserNotFoundException, ClientNotFoundException, ItemNotFoundException, InvoiceNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException;

	/**
	 * Function Name: updateInvoiceModel
	 * 
	 * @param invoiceBean
	 * 
	 * @return invoice model
	 * 
	 * @throws InvoiceNotFoundException
	 * @throws UserNotFoundException
	 * @throws ClientNotFoundException
	 * @throws InvoiceItemNotFoundException
	 * @throws ItemNotFoundException
	 * @throws RecurringInvoiceNotFoundException
	 * @throws RegularInvoiceNotFoundException
	 */
	InvoiceModel updateInvoiceModel(UpdateInvoiceBean updateInvoiceBean) throws InvoiceNotFoundException,
			UserNotFoundException, ClientNotFoundException, InvoiceItemNotFoundException, ItemNotFoundException,
			RecurringInvoiceNotFoundException, RegularInvoiceNotFoundException;

	/**
	 * Function Name: deleteInvoiceModel
	 * 
	 * @param invoiceId
	 * 
	 * @return invoice model
	 * 
	 * @throws InvoiceNotFoundException
	 */
	InvoiceModel deleteInvoiceModel(Long invoiceId) throws InvoiceNotFoundException;

	/**
	 * Function Name: getRecentInvoiceModels
	 * 
	 * @param userId
	 * 
	 * @return list of invoice model
	 * 
	 * @throws UserNotFoundException
	 */
	List<InvoiceModel> getRecentInvoiceModels(long userId) throws UserNotFoundException;

	/**
	 * Function Name: convertToInvoiceDTOList
	 * 
	 * @param invoiceModelList
	 * @param dateFormat
	 * 
	 * @return list of invoice DTO
	 */
	List<InvoiceDTO> convertToInvoiceDTOList(List<InvoiceModel> invoiceModelList, String dateFormat);

	/**
	 * Function Name: getRecurringDueInvoiceModels
	 * 
	 * @param userId
	 * 
	 * @return list of invoice model
	 * 
	 * @throws UserNotFoundException
	 * @throws InvoiceNotFoundException
	 */
	List<InvoiceModel> getRecurringDueInvoiceModels(long userId) throws UserNotFoundException, InvoiceNotFoundException;

	/**
	 * Function Name: getRegularDueInvoiceModels
	 * 
	 * @param userId
	 * 
	 * @return list of invoice model
	 * 
	 * @throws UserNotFoundException
	 * @throws InvoiceNotFoundException
	 */
	List<InvoiceModel> getRegularDueInvoiceModels(long userId) throws UserNotFoundException, InvoiceNotFoundException;

	/**
	 * Function Name: getDueInvoiceModelsTomorrow
	 * 
	 * @return list of invoice model
	 * 
	 * @throws InvoiceNotFoundException
	 */
	List<InvoiceModel> getDueInvoiceModelsTomorrow() throws InvoiceNotFoundException;

	/**
	 * Function Name: getDueInvoiceModelsToday
	 * 
	 * @return list of invoice model
	 * 
	 * @throws InvoiceNotFoundException
	 */
	List<InvoiceModel> getDueInvoiceModelsToday() throws InvoiceNotFoundException;

	/**
	 * Function Name: getDueInvoiceModelsPastweek
	 * 
	 * @return list of invoice model
	 * 
	 * @throws InvoiceNotFoundException
	 */
	List<InvoiceModel> getDueInvoiceModelsPastweek() throws InvoiceNotFoundException;

	/**
	 * Function Name: getDueInvoiceModelsYesterday
	 * 
	 * @return list of invoice model
	 * 
	 * @throws InvoiceNotFoundException
	 * @throws UserNotFoundException
	 * @throws OrganizationNotFoundException
	 * @throws UserOrganizationNotFoundException
	 * @throws IOException
	 * @throws DocumentException
	 */
	List<InvoiceModel> getDueInvoiceModelsYesterday() throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException;

	/**
	 * Function Name: updatePaymentStatus
	 * 
	 * @param invoiceId
	 * @param paymentStatus
	 * 
	 * @return String
	 * 
	 * @throws InvoiceNotFoundException
	 * @throws UserNotFoundException
	 * @throws OrganizationNotFoundException
	 * @throws UserOrganizationNotFoundException
	 * @throws IOException
	 * @throws DocumentException
	 */
	String updatePaymentStatus(long invoiceId, PaymentStatus paymentStatus)
			throws InvoiceNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserOrganizationNotFoundException, IOException, DocumentException;

	/**
	 * Function Name: pdfGeneration
	 * 
	 * @param invoiceId
	 * 
	 * @return ByteArrayInputStream
	 * 
	 * @throws InvoiceNotFoundException
	 * @throws UserNotFoundException
	 * @throws OrganizationNotFoundException
	 * @throws UserOrganizationNotFoundException
	 * @throws IOException
	 * @throws DocumentException
	 */
	ByteArrayInputStream pdfGeneration(long invoiceId) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException;

	/**
	 * Function Name: sentInvoice
	 * 
	 * @param invoiceId
	 * 
	 * @return String
	 * 
	 * @throws InvoiceNotFoundException
	 * @throws UserNotFoundException
	 * @throws OrganizationNotFoundException
	 * @throws UserOrganizationNotFoundException
	 * @throws IOException
	 * @throws DocumentException
	 */
	String sentInvoice(long invoiceId) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException;

	/**
	 * Function Name: getAllInvoiceModelByOrganizationId
	 * 
	 * @param organizationId
	 * 
	 * @return list of invoice model
	 * 
	 * @throws OrganizationNotFoundException
	 */
	List<InvoiceModel> getAllInvoiceModelByOrganizationId(long organizationId) throws OrganizationNotFoundException;

	/**
	 * Function Name: InvoiceModelToInvoiceDTO
	 * 
	 * @param invoiceModel
	 * @param dateFormat
	 * 
	 * @return invoice DTO
	 */
	InvoiceDTO InvoiceModelToInvoiceDTO(InvoiceModel invoiceModel, String dateFormat);
}