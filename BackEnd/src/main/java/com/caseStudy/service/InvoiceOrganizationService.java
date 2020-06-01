package com.caseStudy.service;

import java.util.List;

import com.caseStudy.bean.CreateInvoiceOrganizationBean;
import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.InvoiceOrganizationNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.InvoiceOrganizationModel;

/**
 * Class: InvoiceOrganizationService
 * 
 * @author saloni.sharma
 */
public interface InvoiceOrganizationService {
	/**
	 * Function Name: createInvoiceOrganizationModel
	 * 
	 * @param invoiceOrganizationBean
	 * 
	 * @return InvoiceOrganizationModel
	 * 
	 * @throws InvoiceNotFoundException
	 * @throws OrganizationNotFoundException
	 */
	InvoiceOrganizationModel createInvoiceOrganizationModel(CreateInvoiceOrganizationBean invoiceOrganizationBean)
			throws InvoiceNotFoundException, OrganizationNotFoundException;

	/**
	 * Function Name: getInvoiceOrganizationModelByInvoiceId
	 * 
	 * @param invoiceId
	 * 
	 * @return InvoiceOrganizationModel
	 * 
	 * @throws InvoiceOrganizationNotFoundException
	 * @throws InvoiceNotFoundException
	 */
	InvoiceOrganizationModel getInvoiceOrganizationModelByInvoiceId(Long invoiceId)
			throws InvoiceOrganizationNotFoundException, InvoiceNotFoundException;

	/**
	 * Function Name: getAllInvoiceOrganizationModelByOrganizationId
	 * 
	 * @param organizationId
	 * 
	 * @return list of InvoiceOrganizationModel
	 * 
	 * @throws OrganizationNotFoundException
	 */
	List<InvoiceOrganizationModel> getAllInvoiceOrganizationModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException;
}
