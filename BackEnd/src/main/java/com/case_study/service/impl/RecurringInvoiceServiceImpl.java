package com.case_study.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.case_study.entity.Invoice;
import com.case_study.entity.RecurringInvoice;
import com.case_study.enumeration.PaymentStatus;
import com.case_study.exception.RecurringInvoiceNotFoundException;
import com.case_study.model.RecurringInvoiceModel;
import com.case_study.repository.RecurringInvoiceRepository;
import com.case_study.service.RecurringInvoiceService;
import com.case_study.utility.CommonConstants;

@Service("recurrringInvoiceService")
public class RecurringInvoiceServiceImpl implements RecurringInvoiceService {
	@Autowired
	private RecurringInvoiceRepository recurringInvoiceRepository;

	@Override
	public RecurringInvoice createRecurringInvoice(Invoice invoice, PaymentStatus paymentStatus, LocalDateTime dueDate,
			LocalDateTime renewDate, long recurringPeriod) {
		return recurringInvoiceRepository
				.save(new RecurringInvoice(invoice, dueDate, renewDate, recurringPeriod, paymentStatus));
	}

	@Override
	public RecurringInvoice deleteById(Long id) throws RecurringInvoiceNotFoundException {
		RecurringInvoice recurringInvoice = recurringInvoiceRepository.findById(id)
				.orElseThrow(() -> new RecurringInvoiceNotFoundException(CommonConstants.INVOICE_ITEM_NOT_FOUND + id));
		recurringInvoiceRepository.delete(recurringInvoice);
		return recurringInvoice;
	}

	@Override
	public RecurringInvoice updateById(Long id, RecurringInvoiceModel recurringInvoiceModel)
			throws RecurringInvoiceNotFoundException {
		RecurringInvoice recurringInvoice = recurringInvoiceRepository.findById(id)
				.orElseThrow(() -> new RecurringInvoiceNotFoundException(CommonConstants.RECURRING_INVOICE_NOT_FOUND + id));
		recurringInvoice.setDueDate(recurringInvoiceModel.getDueDate());
		recurringInvoice.setPaymentStatus(recurringInvoiceModel.getPaymentStatus());
		recurringInvoice.setPeriod(recurringInvoiceModel.getPeriod());
		recurringInvoice.setRenewDate(recurringInvoiceModel.getRenewDate());
		return recurringInvoiceRepository.save(recurringInvoice);
	}

	@Override
	public boolean updatePaymentStatus(Invoice invoice, PaymentStatus paymentStatus) {
		for (RecurringInvoice recurringInvoice : recurringInvoiceRepository
				.findByInvoiceOrderByDueDateAsc(invoice)) {
			if (recurringInvoice.getPaymentStatus() != PaymentStatus.PAYMENT_PAID) {
				recurringInvoice.setPaymentStatus(paymentStatus);
				recurringInvoiceRepository.save(recurringInvoice);
				if (paymentStatus == PaymentStatus.PAYMENT_PAID && recurringInvoice.getPeriod() != 0) {
					recurringInvoiceRepository.save(new RecurringInvoice(recurringInvoice.getInvoice(),
							recurringInvoice.getDueDate().plusMonths(1),
							recurringInvoice.getRenewDate().plusMonths(1), recurringInvoice.getPeriod() - 1,
							PaymentStatus.PAYMENT_DRAFT));
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public List<RecurringInvoice> findByInvoiceSortByDueDateDesc(Invoice invoice) {
		return recurringInvoiceRepository.findByInvoiceOrderByDueDateDesc(invoice);
	}

	@Override
	public List<RecurringInvoice> findByInvoice(Invoice invoice) {
		return recurringInvoiceRepository.findByInvoice(invoice);
	}

	@Override
	public RecurringInvoiceModel convertToModel(RecurringInvoice recurringInvoice, String dateFormat) {
		return new RecurringInvoiceModel(recurringInvoice, dateFormat);
	}
}