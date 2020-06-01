package com.caseStudy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caseStudy.model.InvoiceModel;
import com.caseStudy.model.InvoiceOrganizationModel;
import com.caseStudy.model.OrganizationModel;

@Repository
public interface InvoiceOrganizationRepository extends JpaRepository<InvoiceOrganizationModel, Long> {
	List<InvoiceOrganizationModel> findByorganizationModel(OrganizationModel organizationModel);

	InvoiceOrganizationModel findByinvoiceModel(InvoiceModel invoiceModel);
}
