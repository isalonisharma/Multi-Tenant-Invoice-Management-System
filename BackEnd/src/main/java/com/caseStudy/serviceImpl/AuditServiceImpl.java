package com.caseStudy.serviceImpl;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caseStudy.dao.Auditdao;
import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.InvoiceOrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.AuditModel;
import com.caseStudy.service.AuditService;

@Service("auditService")
public class AuditServiceImpl implements AuditService {
	@Autowired
	private Auditdao auditDao;

	@Override
	public AuditModel createAuditModel(AuditModel auditModel) {
		return auditDao.createAuditModel(auditModel);
	}

	@Override
	public List<AuditModel> getAllAuditModel() {
		return auditDao.getAllAuditModel();
	}

	@Override
	public void csvGeneration(LocalDate startDate, LocalDate endDate, PrintWriter writer) {
		auditDao.csvGeneration(startDate, endDate, writer);
	}

	@Override
	public List<AuditModel> getAuditModelBetweenDates(LocalDate startDate, LocalDate endDate) {
		return auditDao.getAuditModelBetweenDates(startDate, endDate);
		
	}

	@Override
	public HashMap<String, Double> AmountFromPaidInvoices(long userId) throws UserNotFoundException,
			UserOrganizationNotFoundException, InvoiceOrganizationNotFoundException, InvoiceNotFoundException {
		return auditDao.AmountFromPaidInvoices(userId);
	}

}
