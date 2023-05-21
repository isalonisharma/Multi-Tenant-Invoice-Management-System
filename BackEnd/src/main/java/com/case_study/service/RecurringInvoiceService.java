package com.case_study.service;

import java.time.LocalDateTime;
import java.util.List;

import com.case_study.entity.Invoice;
import com.case_study.entity.RecurringInvoice;
import com.case_study.enumeration.PaymentStatus;
import com.case_study.exception.RecurringInvoiceNotFoundException;
import com.case_study.model.RecurringInvoiceModel;

public interface RecurringInvoiceService {

	RecurringInvoice createRecurringInvoice(Invoice invoice, PaymentStatus paymentStatus, LocalDateTime dueDate,
			LocalDateTime renewDate, long recurringPeriod);

	RecurringInvoice deleteById(Long id) throws RecurringInvoiceNotFoundException;

	RecurringInvoice updateById(Long id, RecurringInvoiceModel recurringInvoiceModel)
			throws RecurringInvoiceNotFoundException;

	boolean updatePaymentStatus(Invoice invoice, PaymentStatus paymentStatus);

	List<RecurringInvoice> findByInvoiceSortByDueDateDesc(Invoice invoice);

	List<RecurringInvoice> findByInvoice(Invoice invoice);

	RecurringInvoiceModel convertToModel(RecurringInvoice recurringInvoice, String dateFormat);
}