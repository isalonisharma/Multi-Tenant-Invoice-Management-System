package com.caseStudy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caseStudy.model.InvoiceModel;
import com.caseStudy.model.RecurringInvoiceModel;

@Repository
public interface RecurringInvoiceRepository extends JpaRepository<RecurringInvoiceModel, Long> {
	List<RecurringInvoiceModel> findByInvoiceModel(InvoiceModel invoiceModel);

	List<RecurringInvoiceModel> findByInvoiceModelOrderByDueDateDesc(InvoiceModel invoiceModel);

	List<RecurringInvoiceModel> findByInvoiceModelOrderByDueDateAsc(InvoiceModel invoiceModel);
}
