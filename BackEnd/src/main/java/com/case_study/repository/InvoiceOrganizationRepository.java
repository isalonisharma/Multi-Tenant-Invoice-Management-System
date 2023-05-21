package com.case_study.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.case_study.entity.Invoice;
import com.case_study.entity.InvoiceOrganization;
import com.case_study.entity.Organization;

@Repository
public interface InvoiceOrganizationRepository extends JpaRepository<InvoiceOrganization, Long> {
	List<InvoiceOrganization> findByOrganization(Organization organization);

	InvoiceOrganization findByInvoice(Invoice invoice);
}