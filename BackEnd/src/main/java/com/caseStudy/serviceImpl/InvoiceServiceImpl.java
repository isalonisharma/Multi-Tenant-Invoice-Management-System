package com.caseStudy.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.caseStudy.model.InvoiceModel;
import com.caseStudy.service.InvoiceService;
import com.itextpdf.text.DocumentException;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {
	@Autowired
	private Invoicedao invoiceDao;

	@Override
	public List<InvoiceModel> getAllInvoiceModels() throws SQLException {
		return invoiceDao.getAllInvoiceModels();
	}

	@Override
	public InvoiceModel getInvoiceModelById(Long invoiceId) throws InvoiceNotFoundException {
		return invoiceDao.getInvoiceModelById(invoiceId);
	}

	@Override
	public List<InvoiceModel> getInvoiceModelsByUserId(long userId) throws UserNotFoundException {
		return invoiceDao.getInvoiceModelsByUserId(userId);
	}

	@Override
	public List<InvoiceModel> getInvoiceModelsByClientId(long clientId) throws ClientNotFoundException {
		return invoiceDao.getInvoiceModelsByClientId(clientId);
	}

	@Override
	public InvoiceModel createInvoiceModel(CreateInvoiceBean invoice)
			throws UserNotFoundException, ClientNotFoundException, ItemNotFoundException, InvoiceNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {
		return invoiceDao.createInvoiceModel(invoice);
	}

	@Override
	public List<InvoiceModel> getInvoiceModelsRecurringByUserId(long userId) throws UserNotFoundException {
		return invoiceDao.getInvoiceModelsRecurringByUserId(userId);
	}

	@Override
	public List<InvoiceModel> getInvoiceModelsRegularByUserId(long userId) throws UserNotFoundException {
		return invoiceDao.getInvoiceModelsRegularByUserId(userId);
	}

	@Override
	public List<InvoiceModel> getAllInvoiceModelsRecurring(long organizationId) throws OrganizationNotFoundException {
		return invoiceDao.getAllInvoiceModelsRecurring(organizationId);
	}

	@Override
	public List<InvoiceModel> getAllInvoiceModelsRegular(long organizationId) throws OrganizationNotFoundException {
		return invoiceDao.getAllInvoiceModelsRegular(organizationId);
	}

	@Override
	public InvoiceModel deleteInvoiceModel(Long invoiceId) throws InvoiceNotFoundException {
		return invoiceDao.deleteInvoiceModel(invoiceId);
	}

	@Override
	public List<InvoiceModel> getRecentInvoiceModels(long userId) throws UserNotFoundException {
		return invoiceDao.getRecentInvoiceModels(userId);
	}

	@Override
	public List<InvoiceModel> getRecurringDueInvoiceModels(long userId)
			throws UserNotFoundException, InvoiceNotFoundException {
		return invoiceDao.getRecurringDueInvoiceModels(userId);
	}

	@Override
	public List<InvoiceModel> getRegularDueInvoiceModels(long userId)
			throws UserNotFoundException, InvoiceNotFoundException {
		return invoiceDao.getRegularDueInvoiceModels(userId);
	}

	@Override
	public List<InvoiceDTO> convertToInvoiceDTOList(List<InvoiceModel> invoiceModelList, String dateFormat) {
		return invoiceDao.convertToInvoiceDTOList(invoiceModelList, dateFormat);
	}

	@Override
	public List<InvoiceModel> getDueInvoiceModelsTomorrow() throws InvoiceNotFoundException {
		return invoiceDao.getDueInvoiceModelsTomorrow();
	}

	@Override
	public List<InvoiceModel> getDueInvoiceModelsToday() throws InvoiceNotFoundException {
		return invoiceDao.getDueInvoiceModelsToday();
	}

	@Override
	public List<InvoiceModel> getDueInvoiceModelsPastweek() throws InvoiceNotFoundException {
		return invoiceDao.getDueInvoiceModelsPastweek();
	}

	@Override
	public String updatePaymentStatus(long invoiceId, PaymentStatus paymentStatus)
			throws InvoiceNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserOrganizationNotFoundException, IOException, DocumentException {
		return invoiceDao.updatePaymentStatus(invoiceId, paymentStatus);
	}

	@Override
	public InvoiceModel updateInvoiceModel(UpdateInvoiceBean invoiceBean) throws InvoiceNotFoundException,
			UserNotFoundException, ClientNotFoundException, InvoiceItemNotFoundException, ItemNotFoundException,
			RecurringInvoiceNotFoundException, RegularInvoiceNotFoundException {
		return invoiceDao.updateInvoiceModel(invoiceBean);
	}

	@Override
	public ByteArrayInputStream pdfGeneration(long invoiceId) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {
		return invoiceDao.pdfGeneration(invoiceId);
	}

	@Override
	public String sentInvoice(long invoiceId) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {
		return invoiceDao.sentInvoice(invoiceId);
	}

	@Override
	public List<InvoiceModel> getDueInvoiceModelsYesterday() throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {
		return invoiceDao.getDueInvoiceModelsYesterday();
	}

	@Override
	public List<InvoiceModel> getAllInvoiceModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException {
		return invoiceDao.getAllInvoiceModelByOrganizationId(organizationId);
	}

	@Override
	public InvoiceDTO InvoiceModelToInvoiceDTO(InvoiceModel invoiceModel, String dateFormat) {
		return invoiceDao.InvoiceModelToInvoiceDTO(invoiceModel, dateFormat);
	}

}