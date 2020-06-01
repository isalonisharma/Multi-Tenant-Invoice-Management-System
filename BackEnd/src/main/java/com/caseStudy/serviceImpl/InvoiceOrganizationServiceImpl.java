package com.caseStudy.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caseStudy.bean.CreateInvoiceOrganizationBean;
import com.caseStudy.dao.InvoiceOrganizationdao;
import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.InvoiceOrganizationNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.InvoiceOrganizationModel;
import com.caseStudy.service.InvoiceOrganizationService;

@Service("invoiceOrganizationService")
public class InvoiceOrganizationServiceImpl implements InvoiceOrganizationService {
	@Autowired
	private InvoiceOrganizationdao invoiceOrganizationdao;

	@Override
	public InvoiceOrganizationModel createInvoiceOrganizationModel(
			CreateInvoiceOrganizationBean invoiceOrganizationBean)
			throws InvoiceNotFoundException, OrganizationNotFoundException {
		return invoiceOrganizationdao.createInvoiceOrganizationModel(invoiceOrganizationBean);
	}

	@Override
	public InvoiceOrganizationModel getInvoiceOrganizationModelByInvoiceId(Long invoiceId)
			throws InvoiceOrganizationNotFoundException, InvoiceNotFoundException {
		return invoiceOrganizationdao.getInvoiceOrganizationModelByInvoiceId(invoiceId);
	}

	@Override
	public List<InvoiceOrganizationModel> getAllInvoiceOrganizationModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException {
		return invoiceOrganizationdao.getAllInvoiceOrganizationModelByOrganizationId(organizationId);
	}

}
