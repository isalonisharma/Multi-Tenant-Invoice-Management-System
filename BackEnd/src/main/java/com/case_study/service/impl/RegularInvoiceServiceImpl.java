package com.case_study.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.case_study.entity.Invoice;
import com.case_study.entity.RegularInvoice;
import com.case_study.enumeration.PaymentStatus;
import com.case_study.exception.RegularInvoiceNotFoundException;
import com.case_study.model.RegularInvoiceModel;
import com.case_study.repository.RegularInvoiceRepository;
import com.case_study.service.RegularInvoiceService;
import com.case_study.utility.CommonConstants;

@Service("regularInvoiceService")
public class RegularInvoiceServiceImpl implements RegularInvoiceService {
	@Autowired
	private RegularInvoiceRepository regularInvoiceRepository;

	@Override
	public RegularInvoice createRegularInvoice(Invoice invoice, PaymentStatus paymentStatus, LocalDateTime dueDate) {
		return regularInvoiceRepository.save(new RegularInvoice(invoice, dueDate, paymentStatus));
	}

	@Override
	public RegularInvoice deleteById(Long id) throws RegularInvoiceNotFoundException {
		RegularInvoice regularInvoice = regularInvoiceRepository.findById(id)
				.orElseThrow(() -> new RegularInvoiceNotFoundException(CommonConstants.REGULAR_INVOICE_NOT_FOUND + id));
		regularInvoiceRepository.delete(regularInvoice);
		return regularInvoice;
	}

	@Override
	public RegularInvoice updateById(Long id, RegularInvoiceModel regularInvoiceModel)
			throws RegularInvoiceNotFoundException {
		RegularInvoice regularInvoice = regularInvoiceRepository.findById(id)
				.orElseThrow(() -> new RegularInvoiceNotFoundException(CommonConstants.REGULAR_INVOICE_NOT_FOUND + id));
		regularInvoice.setDueDate(regularInvoiceModel.getDueDate());
		regularInvoice.setPaymentStatus(regularInvoiceModel.getPaymentStatus());
		return regularInvoiceRepository.save(regularInvoice);
	}

	@Override
	public boolean updatePaymentStatus(Invoice invoice, PaymentStatus paymentStatus) {
		for (RegularInvoice regularInvoice : regularInvoiceRepository.findByInvoice(invoice)) {
			regularInvoice.setPaymentStatus(paymentStatus);
			regularInvoiceRepository.save(regularInvoice);
		}
		return true;
	}

	@Override
	public List<RegularInvoice> findByInvoiceSortByDueDateDesc(Invoice invoice) {
		return regularInvoiceRepository.findByInvoiceOrderByDueDateDesc(invoice);
	}

	@Override
	public List<RegularInvoice> findByInvoice(Invoice invoice) {
		return regularInvoiceRepository.findByInvoice(invoice);
	}

	@Override
	public RegularInvoiceModel convertToModel(RegularInvoice regularInvoice, String dateFormat) {
		return new RegularInvoiceModel(regularInvoice, dateFormat);
	}
}