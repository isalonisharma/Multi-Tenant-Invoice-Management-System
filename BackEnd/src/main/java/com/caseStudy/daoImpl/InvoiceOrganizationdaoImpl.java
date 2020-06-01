package com.caseStudy.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caseStudy.bean.CreateInvoiceOrganizationBean;
import com.caseStudy.dao.InvoiceOrganizationdao;
import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.InvoiceOrganizationNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.InvoiceModel;
import com.caseStudy.model.InvoiceOrganizationModel;
import com.caseStudy.model.OrganizationModel;
import com.caseStudy.repository.InvoiceOrganizationRepository;
import com.caseStudy.service.InvoiceService;
import com.caseStudy.service.OrganizationService;

@Repository
public class InvoiceOrganizationdaoImpl implements InvoiceOrganizationdao {
	static final Logger logger = Logger.getLogger(InvoiceOrganizationdaoImpl.class);
	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private InvoiceOrganizationRepository invoiceOrganizationRepository;

	@Autowired
	private OrganizationService organizationService;

	@Override
	public InvoiceOrganizationModel createInvoiceOrganizationModel(
			CreateInvoiceOrganizationBean invoiceOrganizationBean)
			throws InvoiceNotFoundException, OrganizationNotFoundException {
		logger.info("InvoiceOrganizationdaoImpl--->>createInvoiceOrganizationModel--->>Start");

		InvoiceOrganizationModel invoiceOrganizationModel = new InvoiceOrganizationModel();

		InvoiceModel invoiceModel = invoiceService.getInvoiceModelById(invoiceOrganizationBean.getInvoiceId());

		OrganizationModel organizationModel = organizationService
				.getOrganizationModelById(invoiceOrganizationBean.getOrganizationId());

		invoiceOrganizationModel.setInvoiceModel(invoiceModel);
		invoiceOrganizationModel.setOrganizationModel(organizationModel);

		invoiceOrganizationRepository.save(invoiceOrganizationModel);

		logger.info("InvoiceOrganizationdaoImpl--->>createInvoiceOrganizationModel--->>End");

		return invoiceOrganizationModel;
	}

	@Override
	public InvoiceOrganizationModel getInvoiceOrganizationModelByInvoiceId(Long invoiceId)
			throws InvoiceOrganizationNotFoundException, InvoiceNotFoundException {
		logger.info("InvoiceOrganizationdaoImpl--->>getInvoiceOrganizationModelByInvoiceId--->>Start");

		InvoiceModel invoiceModel = invoiceService.getInvoiceModelById(invoiceId);

		InvoiceOrganizationModel invoiceOrganizationModel = invoiceOrganizationRepository
				.findByinvoiceModel(invoiceModel);

		logger.info("InvoiceOrganizationdaoImpl--->>getInvoiceOrganizationModelByInvoiceId--->>End");

		return invoiceOrganizationModel;
	}

	@Override
	public List<InvoiceOrganizationModel> getAllInvoiceOrganizationModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException {
		logger.info("InvoiceOrganizationdaoImpl--->>getAllInvoiceOrganizationModelByOrganizationId--->>Start");

		OrganizationModel organizationModel = organizationService.getOrganizationModelById(organizationId);

		logger.info("InvoiceOrganizationdaoImpl--->>getAllInvoiceOrganizationModelByOrganizationId--->>End");

		return invoiceOrganizationRepository.findByorganizationModel(organizationModel);
	}

}
