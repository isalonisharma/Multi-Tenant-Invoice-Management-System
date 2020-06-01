package com.caseStudy.service;

import java.util.List;

import com.caseStudy.exception.InvoiceItemNotFoundException;
import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.model.InvoiceItemModel;

/**
 * Class: InvoiceItemService
 * 
 * @author saloni.sharma
 */
public interface InvoiceItemService {
	/**
	 * Function Name: findByinvoiceId
	 * 
	 * @param invoiceId
	 * 
	 * @return List of invoice model
	 * 
	 * @throws InvoiceNotFoundException
	 */
	List<InvoiceItemModel> findByinvoiceId(long invoiceId) throws InvoiceNotFoundException;

	/**
	 * Function Name: createInvoiceItemModel
	 * 
	 * @param invoiceItemObject
	 * 
	 * @return InvoiceItemModel
	 */
	InvoiceItemModel createInvoiceItemModel(InvoiceItemModel invoiceItemObject);

	/**
	 * Function Name: deleteInvoiceItemModel
	 * 
	 * @param invoiceItemId
	 * 
	 * @return InvoiceItemModel
	 * 
	 * @throws InvoiceItemNotFoundException
	 * @throws InvoiceNotFoundException
	 */
	InvoiceItemModel deleteInvoiceItemModel(long invoiceItemId)
			throws InvoiceItemNotFoundException, InvoiceNotFoundException;
}
