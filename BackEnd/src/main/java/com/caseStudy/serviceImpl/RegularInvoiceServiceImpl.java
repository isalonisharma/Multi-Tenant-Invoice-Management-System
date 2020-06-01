package com.caseStudy.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caseStudy.dao.RegularInvoicedao;
import com.caseStudy.dto.RegularInvoiceDTO;
import com.caseStudy.enumeration.PaymentStatus;
import com.caseStudy.exception.RegularInvoiceNotFoundException;
import com.caseStudy.model.InvoiceModel;
import com.caseStudy.model.RegularInvoiceModel;
import com.caseStudy.service.RegularInvoiceService;

@Service("regularInvoiceService")
public class RegularInvoiceServiceImpl implements RegularInvoiceService {
	@Autowired
	private RegularInvoicedao regularInvoiceDao;

	@Override
	public RegularInvoiceModel updateRegularInvoiceModel(Long regularInvoiceId,
			RegularInvoiceModel regularInvoiceDetails) throws RegularInvoiceNotFoundException {
		return regularInvoiceDao.updateRegularInvoiceModel(regularInvoiceId, regularInvoiceDetails);
	}

	@Override
	public boolean createRegularInvoiceModel(InvoiceModel invoiceModel, PaymentStatus paymentStatus,
			LocalDateTime dueDate) {
		return regularInvoiceDao.createRegularInvoiceModel(invoiceModel, paymentStatus, dueDate);
	}

	@Override
	public boolean updatePaymentStatus(InvoiceModel invoiceModel, PaymentStatus paymentStatus) {
		return regularInvoiceDao.updatePaymentStatus(invoiceModel, paymentStatus);
	}

	@Override
	public List<RegularInvoiceModel> findByInvoiceModelSortByDueDateDesc(InvoiceModel invoiceModel) {
		return regularInvoiceDao.findByInvoiceModelSortByDueDateDesc(invoiceModel);
	}

	@Override
	public List<RegularInvoiceModel> findByInvoiceModel(InvoiceModel invoiceModel) {
		return regularInvoiceDao.findByInvoiceModel(invoiceModel);
	}

	@Override
	public RegularInvoiceModel deleteRegularInvoiceModel(Long regularInvoiceId) throws RegularInvoiceNotFoundException {
		return regularInvoiceDao.deleteRegularInvoiceModel(regularInvoiceId);
	}

	@Override
	public RegularInvoiceDTO RegularInvoiceModelToRegularInvoiceDTO(RegularInvoiceModel regularInvoiceModel,
			String dateFormat) {
		return regularInvoiceDao.RegularInvoiceModelToRegularInvoiceDTO(regularInvoiceModel, dateFormat);
	}

}