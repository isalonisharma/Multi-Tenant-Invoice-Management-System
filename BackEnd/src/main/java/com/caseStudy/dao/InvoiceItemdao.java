package com.caseStudy.dao;

import java.util.List;

import com.caseStudy.bean.CreateInvoiceItemBean;
import com.caseStudy.exception.InvoiceItemNotFoundException;
import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.ItemNotFoundException;
import com.caseStudy.model.InvoiceItemModel;

public interface InvoiceItemdao {
	// CRUD Operations

	// 1. Create Operation
	InvoiceItemModel createInvoiceItemModel(InvoiceItemModel invoiceItemModel);

	// 2. Read Operation
	List<InvoiceItemModel> findByinvoiceId(long invoiceId) throws InvoiceNotFoundException;

	// 3. Update Operation
	InvoiceItemModel updateInvoiceItemModel(CreateInvoiceItemBean invoiceItemBean)
			throws InvoiceItemNotFoundException, InvoiceNotFoundException, ItemNotFoundException;

	// 4. Delete Operation
	InvoiceItemModel deleteInvoiceItemModel(long invoiceItemId)
			throws InvoiceNotFoundException, InvoiceItemNotFoundException;
}
