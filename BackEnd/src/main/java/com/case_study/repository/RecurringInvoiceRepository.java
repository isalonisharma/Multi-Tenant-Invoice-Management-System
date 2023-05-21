package com.case_study.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.case_study.entity.Invoice;
import com.case_study.entity.RecurringInvoice;

@Repository
public interface RecurringInvoiceRepository extends JpaRepository<RecurringInvoice, Long> {
	List<RecurringInvoice> findByInvoice(Invoice invoice);

	List<RecurringInvoice> findByInvoiceOrderByDueDateDesc(Invoice invoice);

	List<RecurringInvoice> findByInvoiceOrderByDueDateAsc(Invoice invoice);
}