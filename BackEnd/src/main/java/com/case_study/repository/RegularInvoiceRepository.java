package com.case_study.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.case_study.entity.Invoice;
import com.case_study.entity.RegularInvoice;

@Repository
public interface RegularInvoiceRepository extends JpaRepository<RegularInvoice, Long> {
	List<RegularInvoice> findByInvoice(Invoice invoice);

	List<RegularInvoice> findByInvoiceOrderByDueDateDesc(Invoice invoice);
}