package com.caseStudy.service;

import java.time.LocalDateTime;
import java.util.List;

import com.caseStudy.dto.RegularInvoiceDTO;
import com.caseStudy.enumeration.PaymentStatus;
import com.caseStudy.exception.RegularInvoiceNotFoundException;
import com.caseStudy.model.InvoiceModel;
import com.caseStudy.model.RegularInvoiceModel;

/**
 * Class: RegularInvoiceService
 * 
 * @author saloni.sharma
 */
public interface RegularInvoiceService {

	/**
	 * Function Name: createRegularInvoiceModel
	 * 
	 * @param invoiceModel
	 * @param paymentStatus
	 * @param dueDate
	 * 
	 * @return boolean value
	 */
	boolean createRegularInvoiceModel(InvoiceModel invoiceModel, PaymentStatus paymentStatus, LocalDateTime dueDate);

	/**
	 * Function Name: updateRegularInvoiceModel
	 * 
	 * @param regularInvoiceId
	 * @param regularInvoiceDetails
	 * 
	 * @return RegularInvoiceModel
	 * 
	 * @throws RegularInvoiceNotFoundException
	 */
	RegularInvoiceModel updateRegularInvoiceModel(Long regularInvoiceId, RegularInvoiceModel regularInvoiceDetails)
			throws RegularInvoiceNotFoundException;

	/**
	 * Function Name: deleteRegularInvoiceModel
	 * 
	 * @param regularInvoiceId
	 * 
	 * @return RegularInvoiceModel
	 * 
	 * @throws RegularInvoiceNotFoundException
	 */
	RegularInvoiceModel deleteRegularInvoiceModel(Long regularInvoiceId) throws RegularInvoiceNotFoundException;

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
	 * @return list of RegularInvoiceModel
	 */
	List<RegularInvoiceModel> findByInvoiceModelSortByDueDateDesc(InvoiceModel invoiceModel);

	/**
	 * Function Name: findByInvoiceModel
	 * 
	 * @param invoiceModel
	 * 
	 * @return list of RegularInvoiceModel
	 */
	List<RegularInvoiceModel> findByInvoiceModel(InvoiceModel invoiceModel);

	/**
	 * Function Name: RegularInvoiceModelToRegularInvoiceDTO
	 * 
	 * @param regularInvoiceModel
	 * @param dateFormat
	 * 
	 * @return RegularInvoiceDTO
	 */
	RegularInvoiceDTO RegularInvoiceModelToRegularInvoiceDTO(RegularInvoiceModel regularInvoiceModel,
			String dateFormat);
}
