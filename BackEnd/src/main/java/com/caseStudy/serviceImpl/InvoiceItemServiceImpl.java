package com.caseStudy.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caseStudy.dao.InvoiceItemdao;
import com.caseStudy.exception.InvoiceItemNotFoundException;
import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.model.InvoiceItemModel;
import com.caseStudy.service.InvoiceItemService;

@Service("invoiceItemService")
public class InvoiceItemServiceImpl implements InvoiceItemService {
	@Autowired
	private InvoiceItemdao invoiceItemDao;

	@Override
	public List<InvoiceItemModel> findByinvoiceId(long invoiceId) throws InvoiceNotFoundException {
		return invoiceItemDao.findByinvoiceId(invoiceId);
	}

	@Override
	public InvoiceItemModel createInvoiceItemModel(InvoiceItemModel invoiceItemObject) {
		return invoiceItemDao.createInvoiceItemModel(invoiceItemObject);
	}

	@Override
	public InvoiceItemModel deleteInvoiceItemModel(long invoiceItemId)
			throws InvoiceItemNotFoundException, InvoiceNotFoundException {
		return invoiceItemDao.deleteInvoiceItemModel(invoiceItemId);
	}
}