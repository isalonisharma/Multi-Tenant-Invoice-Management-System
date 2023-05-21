package com.case_study.service;

import java.util.List;

import com.case_study.bean.CreateInvoiceOrganizationBean;
import com.case_study.entity.InvoiceOrganization;
import com.case_study.exception.InvoiceNotFoundException;
import com.case_study.exception.InvoiceOrganizationNotFoundException;
import com.case_study.exception.OrganizationNotFoundException;

public interface InvoiceOrganizationService {

	InvoiceOrganization createInvoiceOrganization(CreateInvoiceOrganizationBean createInvoiceOrganizationBean)
			throws InvoiceNotFoundException, OrganizationNotFoundException;

	InvoiceOrganization findByInvoiceId(Long invoiceId)
			throws InvoiceOrganizationNotFoundException, InvoiceNotFoundException;

	List<InvoiceOrganization> findByOrganizationId(long organizationId) throws OrganizationNotFoundException;
}