package com.case_study.service;

import java.util.List;

import com.case_study.entity.InvoiceItem;
import com.case_study.exception.InvoiceItemNotFoundException;
import com.case_study.exception.InvoiceNotFoundException;

public interface InvoiceItemService {

	InvoiceItem createInvoiceItem(InvoiceItem invoiceItem);

	InvoiceItem deleteById(long id) throws InvoiceItemNotFoundException, InvoiceNotFoundException;

	List<InvoiceItem> findByInvoiceId(long invoiceId) throws InvoiceNotFoundException;
}