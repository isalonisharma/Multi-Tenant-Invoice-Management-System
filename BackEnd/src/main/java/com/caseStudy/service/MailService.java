package com.caseStudy.service;

import java.io.IOException;

import com.caseStudy.bean.MailBean;
import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.InvoiceModel;
import com.itextpdf.text.DocumentException;

/**
 * Class: MailService
 * 
 * @author saloni.sharma
 */
public interface MailService {
	/**
	 * Function Name: sendEmail
	 * 
	 * @param mailBean
	 * 
	 * @return boolean value
	 * 
	 * @throws IOException
	 */
	Boolean sendEmail(MailBean mailBean) throws IOException;

	/**
	 * Function Name: sendThankyouMail
	 * 
	 * @param invoiceModel
	 * 
	 * @return boolean value
	 * 
	 * @throws InvoiceNotFoundException
	 * @throws UserNotFoundException
	 * @throws OrganizationNotFoundException
	 * @throws UserOrganizationNotFoundException
	 * @throws IOException
	 * @throws DocumentException
	 */
	Boolean sendThankyouMail(InvoiceModel invoiceModel) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException;

	/**
	 * Function Name: sendInvoiceMail
	 * 
	 * @param invoiceModel
	 * 
	 * @return boolean value
	 * 
	 * @throws InvoiceNotFoundException
	 * @throws UserNotFoundException
	 * @throws OrganizationNotFoundException
	 * @throws UserOrganizationNotFoundException
	 * @throws IOException
	 * @throws DocumentException
	 */
	Boolean sendInvoiceMail(InvoiceModel invoiceModel) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException;

	/**
	 * Function Name: sendDueMail
	 * 
	 * @param invoiceModel
	 * @param dueDate
	 * 
	 * @return boolean value
	 * 
	 * @throws UserNotFoundException
	 * @throws OrganizationNotFoundException
	 * @throws UserOrganizationNotFoundException
	 * @throws IOException
	 * @throws InvoiceNotFoundException
	 * @throws DocumentException
	 */
	Boolean sendDueMail(InvoiceModel invoiceModel, String dueDate)
			throws UserNotFoundException, OrganizationNotFoundException, UserOrganizationNotFoundException, IOException,
			InvoiceNotFoundException, DocumentException;
}