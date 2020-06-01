package com.caseStudy.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.caseStudy.dto.RegularInvoiceDTO;
import com.caseStudy.enumeration.PaymentStatus;
import com.caseStudy.exception.RegularInvoiceNotFoundException;
import com.caseStudy.model.InvoiceModel;
import com.caseStudy.model.RegularInvoiceModel;

public interface RegularInvoicedao {

	// Create Operation
	boolean createRegularInvoiceModel(InvoiceModel invoice, PaymentStatus paymentStatus, LocalDateTime dueDate);

	// Update Operation
	RegularInvoiceModel updateRegularInvoiceModel(Long regularInvoiceId, RegularInvoiceModel regularInvoiceDetails)
			throws RegularInvoiceNotFoundException;

	// Delete Operation
	RegularInvoiceModel deleteRegularInvoiceModel(Long regularInvoiceId) throws RegularInvoiceNotFoundException;

	// updating the payment status of specific entry in regular invoice table with the help
	// of given invoice model
	boolean updatePaymentStatus(InvoiceModel invoiceModel, PaymentStatus paymentStatus);

	// Getting the entries from regular invoice table in descending order sorted by due date of given invoice model 
	List<RegularInvoiceModel> findByInvoiceModelSortByDueDateDesc(InvoiceModel invoiceModel);

	// Getting the entries from regular invoice table of given invoice model
	List<RegularInvoiceModel> findByInvoiceModel(InvoiceModel invoiceModel);

	// Converting the regular invoice table entry into date transfer object (DTO)
	RegularInvoiceDTO RegularInvoiceModelToRegularInvoiceDTO(RegularInvoiceModel regularInvoiceModel,
			String dateFormat);
}
