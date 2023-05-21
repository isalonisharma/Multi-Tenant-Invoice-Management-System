package com.case_study.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.case_study.bean.CreateInvoiceOrganizationBean;
import com.case_study.entity.Invoice;
import com.case_study.entity.InvoiceOrganization;
import com.case_study.entity.Organization;
import com.case_study.exception.InvoiceNotFoundException;
import com.case_study.exception.InvoiceOrganizationNotFoundException;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.repository.InvoiceOrganizationRepository;
import com.case_study.service.InvoiceOrganizationService;
import com.case_study.service.InvoiceService;
import com.case_study.service.OrganizationService;

@Service("invoiceOrganizationService")
public class InvoiceOrganizationServiceImpl implements InvoiceOrganizationService {
	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private InvoiceOrganizationRepository invoiceOrganizationRepository;

	@Autowired
	private OrganizationService organizationService;

	@Override
	public InvoiceOrganization createInvoiceOrganization(CreateInvoiceOrganizationBean createInvoiceOrganizationBean)
			throws InvoiceNotFoundException, OrganizationNotFoundException {
		InvoiceOrganization invoiceOrganization = new InvoiceOrganization();
		Invoice invoice = invoiceService.findById(createInvoiceOrganizationBean.getInvoiceId());
		Organization organization = organizationService.findById(createInvoiceOrganizationBean.getOrganizationId());
		invoiceOrganization.setInvoice(invoice);
		invoiceOrganization.setOrganization(organization);
		invoiceOrganizationRepository.save(invoiceOrganization);
		return invoiceOrganization;
	}

	@Override
	public InvoiceOrganization findByInvoiceId(Long invoiceId)
			throws InvoiceOrganizationNotFoundException, InvoiceNotFoundException {
		Invoice invoice = invoiceService.findById(invoiceId);
		return invoiceOrganizationRepository.findByInvoice(invoice);
	}

	@Override
	public List<InvoiceOrganization> findByOrganizationId(long organizationId) throws OrganizationNotFoundException {
		Organization organization = organizationService.findById(organizationId);
		return invoiceOrganizationRepository.findByOrganization(organization);
	}
}