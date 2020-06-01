package com.caseStudy.service;

import java.time.LocalDateTime;
import java.util.List;

import com.caseStudy.dto.RecurringInvoiceDTO;
import com.caseStudy.enumeration.PaymentStatus;
import com.caseStudy.exception.RecurringInvoiceNotFoundException;
import com.caseStudy.model.InvoiceModel;
import com.caseStudy.model.RecurringInvoiceModel;

/**
 * Class: RecurringInvoiceService
 * 
 * @author saloni.sharma
 */
public interface RecurringInvoiceService {

	/**
	 * Function Name: createRecurringInvoiceModel
	 * 
	 * @param invoiceModel
	 * @param paymentStatus
	 * @param dueDate
	 * @param renewDate
	 * @param recurringPeriod
	 * 
	 * @return boolean value
	 */
	boolean createRecurringInvoiceModel(InvoiceModel invoiceModel, PaymentStatus paymentStatus, LocalDateTime dueDate,
			LocalDateTime renewDate, long recurringPeriod);

	/**
	 * Function Name: updateRecurringInvoiceModel
	 * 
	 * @param recurringInvoiceId
	 * @param recurringInvoiceDetails
	 * 
	 * @return RecurringInvoiceModel
	 * 
	 * @throws RecurringInvoiceNotFoundException
	 */
	RecurringInvoiceModel updateRecurringInvoiceModel(Long recurringInvoiceId,
			RecurringInvoiceModel recurringInvoiceDetails) throws RecurringInvoiceNotFoundException;

	/**
	 * Function Name: deleteRecurringInvoiceModel
	 * 
	 * @param recurringInvoiceId
	 * 
	 * @return RecurringInvoiceModel
	 * 
	 * @throws RecurringInvoiceNotFoundException
	 */
	RecurringInvoiceModel deleteRecurringInvoiceModel(Long recurringInvoiceId) throws RecurringInvoiceNotFoundException;

	/**
	 * Function Name: updatePaymentStatus
	 * 
	 * @param invoiceModel
	 * @param paymentStatus
	 * 
	 * @return boolean value
	 */
	boolean updatePaymentStatus(InvoiceModel invoiceModel, PaymentStatus paymentStatus);

	/**
	 * Function Name: findByInvoiceModelSortByDueDateDesc
	 * 
	 * @param invoiceModel
	 * 
	 * @return list of RecurringInvoiceModel
	 */
	List<RecurringInvoiceModel> findByInvoiceModelSortByDueDateDesc(InvoiceModel invoiceModel);

	/**
	 * Function Name: findByInvoiceModel
	 * 
	 * @param invoiceModel
	 * 
	 * @return list of RecurringInvoiceModel
	 */
	List<RecurringInvoiceModel> findByInvoiceModel(InvoiceModel invoiceModel);

	/**
	 * Function Name: RecurringInvoiceModelToRecurringInvoiceDTO
	 * 
	 * @param recurringInvoiceModel
	 * @param dateFormat
	 * 
	 * @return RecurringInvoiceDTO
	 */
	RecurringInvoiceDTO RecurringInvoiceModelToRecurringInvoiceDTO(RecurringInvoiceModel recurringInvoiceModel,
			String dateFormat);
}
