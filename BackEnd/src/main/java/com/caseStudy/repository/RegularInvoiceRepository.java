package com.caseStudy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caseStudy.model.InvoiceModel;
import com.caseStudy.model.RegularInvoiceModel;

@Repository
public interface RegularInvoiceRepository extends JpaRepository<RegularInvoiceModel, Long> {
	List<RegularInvoiceModel> findByInvoiceModel(InvoiceModel invoiceModel);

	List<RegularInvoiceModel> findByInvoiceModelOrderByDueDateDesc(InvoiceModel invoiceId);

}