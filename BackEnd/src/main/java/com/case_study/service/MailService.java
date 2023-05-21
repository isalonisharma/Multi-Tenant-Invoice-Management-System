package com.case_study.service;

import java.io.IOException;

import com.case_study.entity.Invoice;
import com.case_study.exception.InvoiceNotFoundException;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.itextpdf.text.DocumentException;

public interface MailService {

	Boolean sendThankyouMail(Invoice invoice) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException;

	Boolean sendDueMail(Invoice invoice, String dueDate) throws UserNotFoundException, OrganizationNotFoundException,
			UserOrganizationNotFoundException, IOException, InvoiceNotFoundException, DocumentException;

	Boolean sendInvoiceMail(Invoice invoice) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException;
}