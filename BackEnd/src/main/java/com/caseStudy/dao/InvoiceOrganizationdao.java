package com.caseStudy.dao;

import java.util.List;

import com.caseStudy.bean.CreateInvoiceOrganizationBean;
import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.InvoiceOrganizationNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.InvoiceOrganizationModel;

public interface InvoiceOrganizationdao {
	
	// Create Operation
	InvoiceOrganizationModel createInvoiceOrganizationModel(CreateInvoiceOrganizationBean invoiceOrganizationBean)
			throws InvoiceNotFoundException, OrganizationNotFoundException;

	// Getting the specific entry from invoice organization table of given invoice id
	InvoiceOrganizationModel getInvoiceOrganizationModelByInvoiceId(Long invoiceId)
			throws InvoiceOrganizationNotFoundException, InvoiceNotFoundException;

	// Getting the entries from invoice organization table of given organization id
	List<InvoiceOrganizationModel> getAllInvoiceOrganizationModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException;
}