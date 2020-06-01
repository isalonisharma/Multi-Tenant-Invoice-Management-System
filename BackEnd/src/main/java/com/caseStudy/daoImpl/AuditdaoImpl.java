package com.caseStudy.daoImpl;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caseStudy.dao.Auditdao;
import com.caseStudy.enumeration.PaymentStatus;
import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.InvoiceOrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.AuditModel;
import com.caseStudy.repository.AuditRepository;
import com.caseStudy.service.InvoiceOrganizationService;
import com.caseStudy.service.UserOrganizationService;
import com.caseStudy.utility.CsvGeneration;

@Repository
public class AuditdaoImpl implements Auditdao {
	static final Logger logger = Logger.getLogger(AuditdaoImpl.class);

	@Autowired
	private AuditRepository auditRepository;

	@Autowired
	private UserOrganizationService userOrganizationService;

	@Autowired
	private InvoiceOrganizationService invoiceOrganizationService;

	@Override
	public AuditModel createAuditModel(AuditModel audit) {
		logger.info("AuditdaoImpl--->>createAuditModel--->>Start");

		AuditModel auditModel = auditRepository.save(audit);

		logger.info("AuditdaoImpl--->>createAuditModel--->>End");

		return auditModel;
	}

	@Override
	public List<AuditModel> getAllAuditModel() {
		logger.info("AuditdaoImpl--->>getAllAuditModel--->>Start");

		List<AuditModel> auditModelList = auditRepository.findAll();

		logger.info("AuditdaoImpl--->>getAllAuditModel--->>End");

		return auditModelList;
	}

	@Override
	public void csvGeneration(LocalDate startDate, LocalDate endDate, PrintWriter writer) {
		logger.info("AuditdaoImpl--->>csvGeneration--->>Start");

		List<AuditModel> auditModelList = auditRepository.findByEntryDateBetween(startDate.atStartOfDay(),
				endDate.atStartOfDay());

		CsvGeneration.csvExport(auditModelList, writer);

		logger.info("AuditdaoImpl--->>csvGeneration--->>End");
	}

	@Override
	public List<AuditModel> getAuditModelBetweenDates(LocalDate startDate, LocalDate endDate) {

		logger.info("AuditdaoImpl--->>getAuditModelBetweenDates--->>Start");

		List<AuditModel> auditModelList = auditRepository.findByEntryDateBetween(startDate.atStartOfDay(),
				endDate.atStartOfDay());

		logger.info("AuditdaoImpl--->>getAuditModelBetweenDates--->>End");
		return auditModelList;
	}

	@Override
	public HashMap<String, Double> AmountFromPaidInvoices(long userId) throws UserNotFoundException,
			UserOrganizationNotFoundException, InvoiceOrganizationNotFoundException, InvoiceNotFoundException {
		logger.info("AuditdaoImpl--->>AmountFromPaidInvoices--->>Start");

		long organizationIdofRequestedUser = userOrganizationService.getUserOrganizationModelByUserId(userId)
				.getOrganizationModel().getOrganizationId();

		HashMap<String, Double> paidAmount = new HashMap<String, Double>();

		LocalDate today = LocalDate.now();

		List<AuditModel> auditModelListWeek = auditRepository.findByEntryDateBetween(today.minusDays(7).atStartOfDay(), 
				today.atStartOfDay());

		List<AuditModel> auditModelListMonth = auditRepository.findByEntryDateBetween(today.minusDays(7).atStartOfDay(),
				today.atStartOfDay());

		List<AuditModel> auditModelListYear = auditRepository.findByEntryDateBetween(today.minusDays(7).atStartOfDay(),
				today.atStartOfDay());
		
		
		System.out.println("****yearlist************");
		System.out.println(auditModelListMonth);
		System.out.println("******************");

		auditModelListWeek
				.removeIf((AuditModel auditModel) -> auditModel.getPaymentStatus() != PaymentStatus.PAYMENT_PAID);

		auditModelListMonth
				.removeIf((AuditModel auditModel) -> auditModel.getPaymentStatus() != PaymentStatus.PAYMENT_PAID);

		auditModelListYear
				.removeIf((AuditModel auditModel) -> auditModel.getPaymentStatus() != PaymentStatus.PAYMENT_PAID);

		double weeklyAmount = 0, monthlyAmount = 0, yearlyAmount = 0;

		for (AuditModel auditModel : auditModelListWeek) {
			long invoiceId = auditModel.getInvoiceModel().getInvoiceId();
			
			long organizationIdofInvoiceId = invoiceOrganizationService
					.getInvoiceOrganizationModelByInvoiceId(invoiceId).getOrganizationModel().getOrganizationId();

			if (organizationIdofInvoiceId == organizationIdofRequestedUser) {
				weeklyAmount = weeklyAmount + auditModel.getInvoiceModel().getAmount();
			}
		}

		for (AuditModel auditModel : auditModelListMonth) {
			long invoiceId = auditModel.getInvoiceModel().getInvoiceId();
			
			long organizationIdofInvoiceId = invoiceOrganizationService
					.getInvoiceOrganizationModelByInvoiceId(invoiceId).getOrganizationModel().getOrganizationId();

			if (organizationIdofInvoiceId == organizationIdofRequestedUser) {
				monthlyAmount = monthlyAmount + auditModel.getInvoiceModel().getAmount();
			}
		}

		for (AuditModel auditModel : auditModelListYear) {
			long invoiceId = auditModel.getInvoiceModel().getInvoiceId();

			long organizationIdofInvoiceId = invoiceOrganizationService
					.getInvoiceOrganizationModelByInvoiceId(invoiceId).getOrganizationModel().getOrganizationId();

			if (organizationIdofInvoiceId == organizationIdofRequestedUser) {
				yearlyAmount = yearlyAmount + auditModel.getInvoiceModel().getAmount();
			}
		}

		paidAmount.put("week", weeklyAmount);
		paidAmount.put("month", monthlyAmount);
		paidAmount.put("year", yearlyAmount);

		logger.info("AuditdaoImpl--->>AmountFromPaidInvoices--->>End");

		return paidAmount;
	}
}