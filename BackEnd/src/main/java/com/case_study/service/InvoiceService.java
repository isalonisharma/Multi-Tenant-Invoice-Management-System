package com.case_study.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.case_study.bean.CreateInvoiceBean;
import com.case_study.bean.UpdateInvoiceBean;
import com.case_study.entity.Invoice;
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
import com.itextpdf.text.DocumentException;

public interface InvoiceService {
	Invoice createInvoice(CreateInvoiceBean createInvoiceBean)
			throws UserNotFoundException, ClientNotFoundException, ItemNotFoundException, InvoiceNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException;

	Invoice deleteById(Long id) throws InvoiceNotFoundException;

	Invoice updateInvoice(UpdateInvoiceBean updateInvoiceBean) throws InvoiceNotFoundException, UserNotFoundException,
			ClientNotFoundException, InvoiceItemNotFoundException, ItemNotFoundException,
			RecurringInvoiceNotFoundException, RegularInvoiceNotFoundException;

	String updatePaymentStatus(long invoiceId, PaymentStatus paymentStatus)
			throws InvoiceNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserOrganizationNotFoundException, IOException, DocumentException;

	List<Invoice> findAll() throws SQLException;

	Invoice findById(Long id) throws InvoiceNotFoundException;

	List<Invoice> findByUserId(long userId) throws UserNotFoundException;

	List<Invoice> findByClientId(long clientId) throws ClientNotFoundException;

	List<Invoice> findByOrganizationId(long organizationId) throws OrganizationNotFoundException;

	List<Invoice> findRecurringInvoicesByUserId(long userId) throws UserNotFoundException;

	List<Invoice> findRegularInvoicesByUserId(long userId) throws UserNotFoundException;

	List<Invoice> findRecurringInvoicesByOrganizationId(long organizationId) throws OrganizationNotFoundException;

	List<Invoice> findRegularInvoicesByOrganizationId(long organizationId) throws OrganizationNotFoundException;

	List<Invoice> findTop3InvoicesByUserId(long userId) throws UserNotFoundException;

	List<Invoice> findDueRecurringInvoicesByUserId(long userId) throws UserNotFoundException, InvoiceNotFoundException;

	List<Invoice> findDueRegularInvoicesByUserId(long userId) throws UserNotFoundException, InvoiceNotFoundException;

	List<Invoice> getDueInvoicesForTomorrow() throws InvoiceNotFoundException;

	List<Invoice> getDueInvoicesForToday() throws InvoiceNotFoundException;

	List<Invoice> getDueInvoicesForYesterday() throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException;

	List<Invoice> getDueInvoicesForPastweek() throws InvoiceNotFoundException;

	ByteArrayInputStream generatePDF(long invoiceId) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException;

	String sentInvoiceInMail(long invoiceId) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException;

	InvoiceModel convertToInvoiceModel(Invoice invoice, String dateFormat);

	List<InvoiceModel> convertToInvoiceModels(List<Invoice> invoices, String dateFormat);
}