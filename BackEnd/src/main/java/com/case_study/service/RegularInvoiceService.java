package com.case_study.service;

import java.time.LocalDateTime;
import java.util.List;

import com.case_study.entity.Invoice;
import com.case_study.entity.RegularInvoice;
import com.case_study.enumeration.PaymentStatus;
import com.case_study.exception.RegularInvoiceNotFoundException;
import com.case_study.model.RegularInvoiceModel;

public interface RegularInvoiceService {

	RegularInvoice createRegularInvoice(Invoice invoice, PaymentStatus paymentStatus, LocalDateTime dueDate);

	RegularInvoice deleteById(Long id) throws RegularInvoiceNotFoundException;

	RegularInvoice updateById(Long id, RegularInvoiceModel regularInvoiceModel) throws RegularInvoiceNotFoundException;

	boolean updatePaymentStatus(Invoice invoice, PaymentStatus paymentStatus);

	List<RegularInvoice> findByInvoiceSortByDueDateDesc(Invoice invoice);

	List<RegularInvoice> findByInvoice(Invoice invoice);

	RegularInvoiceModel convertToModel(RegularInvoice regularInvoice, String dateFormat);
}