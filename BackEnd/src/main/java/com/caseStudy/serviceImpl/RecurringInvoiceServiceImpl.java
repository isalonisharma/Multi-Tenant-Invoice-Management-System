package com.caseStudy.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caseStudy.dao.RecurringInvoicedao;
import com.caseStudy.dto.RecurringInvoiceDTO;
import com.caseStudy.enumeration.PaymentStatus;
import com.caseStudy.exception.RecurringInvoiceNotFoundException;
import com.caseStudy.model.InvoiceModel;
import com.caseStudy.model.RecurringInvoiceModel;
import com.caseStudy.service.RecurringInvoiceService;

@Service("recurrringInvoiceService")
public class RecurringInvoiceServiceImpl implements RecurringInvoiceService {

	@Autowired
	private RecurringInvoicedao recurringInvoiceDao;

	@Override
	public boolean createRecurringInvoiceModel(InvoiceModel invoiceModel, PaymentStatus paymentStatus,
			LocalDateTime dueDate, LocalDateTime renewDate, long recurringPeriod) {
		return recurringInvoiceDao.createRecurringInvoiceModel(invoiceModel, paymentStatus, dueDate, renewDate,
				recurringPeriod);
	}

	@Override
	public RecurringInvoiceModel updateRecurringInvoiceModel(Long recurringInvoiceId,
			RecurringInvoiceModel recurringInvoiceDetails) throws RecurringInvoiceNotFoundException {
		return recurringInvoiceDao.updateRecurringInvoiceModel(recurringInvoiceId, recurringInvoiceDetails);
	}

	@Override
	public boolean updatePaymentStatus(InvoiceModel invoiceModel, PaymentStatus paymentStatus) {
		return recurringInvoiceDao.updatePaymentStatus(invoiceModel, paymentStatus);
	}

	@Override
	public List<RecurringInvoiceModel> findByInvoiceModelSortByDueDateDesc(InvoiceModel invoiceModel) {

		return recurringInvoiceDao.findByInvoiceModelSortByDueDateDesc(invoiceModel);
	}

	@Override
	public List<RecurringInvoiceModel> findByInvoiceModel(InvoiceModel invoiceModel) {
		return recurringInvoiceDao.findByInvoiceModel(invoiceModel);
	}

	@Override
	public RecurringInvoiceModel deleteRecurringInvoiceModel(Long recurringInvoiceId)
			throws RecurringInvoiceNotFoundException {
		return recurringInvoiceDao.deleteRecurringInvoiceModel(recurringInvoiceId);
	}

	@Override
	public RecurringInvoiceDTO RecurringInvoiceModelToRecurringInvoiceDTO(RecurringInvoiceModel recurringInvoiceModel,
			String dateFormat) {
		return recurringInvoiceDao.RecurringInvoiceModelToRecurringInvoiceDTO(recurringInvoiceModel, dateFormat);
	}

}
