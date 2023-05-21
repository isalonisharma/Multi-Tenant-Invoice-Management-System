package com.case_study.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.case_study.entity.Invoice;
import com.case_study.entity.InvoiceItem;
import com.case_study.exception.InvoiceItemNotFoundException;
import com.case_study.exception.InvoiceNotFoundException;
import com.case_study.repository.InvoiceItemRepository;
import com.case_study.service.InvoiceItemService;
import com.case_study.service.InvoiceService;
import com.case_study.utility.CommonConstants;

@Service("invoiceItemService")
public class InvoiceItemServiceImpl implements InvoiceItemService {
	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private InvoiceItemRepository invoiceItemRepository;

	@Override
	public InvoiceItem createInvoiceItem(InvoiceItem invoiceItem) {
		return invoiceItemRepository.save(invoiceItem);
	}

	@Override
	public InvoiceItem deleteById(long id) throws InvoiceItemNotFoundException, InvoiceNotFoundException {
		InvoiceItem invoiceItem = invoiceItemRepository.findById(id).orElseThrow(
				() -> new InvoiceItemNotFoundException(CommonConstants.INVOICE_ITEM_NOT_FOUND + id));
		invoiceItemRepository.delete(invoiceItem);
		return invoiceItem;
	}

	@Override
	public List<InvoiceItem> findByInvoiceId(long invoiceId) throws InvoiceNotFoundException {
		Invoice invoice = invoiceService.findById(invoiceId);
		return invoiceItemRepository.findByInvoice(invoice);
	}
}