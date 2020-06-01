package com.caseStudy.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.caseStudy.dto.RecurringInvoiceDTO;
import com.caseStudy.enumeration.PaymentStatus;
import com.caseStudy.exception.RecurringInvoiceNotFoundException;
import com.caseStudy.model.InvoiceModel;
import com.caseStudy.model.RecurringInvoiceModel;

public interface RecurringInvoicedao {
	
	// Create Operation
	boolean createRecurringInvoiceModel(InvoiceModel invoice, PaymentStatus paymentStatus, LocalDateTime dueDate,
			LocalDateTime renewDate, long recurringPeriod);

	// Update Operation
	RecurringInvoiceModel updateRecurringInvoiceModel(Long recurringInvoiceId, RecurringInvoiceModel recurringInvoiceDetails)
			throws RecurringInvoiceNotFoundException;

	// Delete Operation
	RecurringInvoiceModel deleteRecurringInvoiceModel(Long recurringInvoiceId) throws RecurringInvoiceNotFoundException;

	// updating the payment status of specific entry in recurring invoice table with the help
	// of given invoice model
	boolean updatePaymentStatus(InvoiceModel invoiceModel, PaymentStatus paymentStatus);

	// Getting the entries from recurring invoice table of given organization id
	List<RecurringInvoiceModel> findByInvoiceModelSortByDueDateDesc(InvoiceModel invoiceModel);

	// Getting the entries from recurring invoice table of given organization id
	List<RecurringInvoiceModel> findByInvoiceModel(InvoiceModel invoiceModel);

	// Converting the recurring table entry into date transfer object (DTO)
	RecurringInvoiceDTO RecurringInvoiceModelToRecurringInvoiceDTO(RecurringInvoiceModel recurringInvoiceModel,
			String dateFormat);
}
