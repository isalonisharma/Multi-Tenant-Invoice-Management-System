package com.case_study.service.impl;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.case_study.entity.Audit;
import com.case_study.enumeration.PaymentStatus;
import com.case_study.exception.InvoiceNotFoundException;
import com.case_study.exception.InvoiceOrganizationNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.repository.AuditRepository;
import com.case_study.service.AuditService;
import com.case_study.service.InvoiceOrganizationService;
import com.case_study.service.UserOrganizationService;
import com.case_study.utility.CsvGeneration;

@Service("auditService")
public class AuditServiceImpl implements AuditService {
	@Autowired
	private AuditRepository auditRepository;

	@Autowired
	private UserOrganizationService userOrganizationService;

	@Autowired
	private InvoiceOrganizationService invoiceOrganizationService;

	@Override
	public Audit createAudit(Audit audit) {
		return auditRepository.save(audit);
	}

	@Override
	public List<Audit> findAll() {
		return auditRepository.findAll();
	}

	@Override
	public void generateCSV(LocalDate startDate, LocalDate endDate, PrintWriter writer) {
		List<Audit> audits = auditRepository.findByEntryDateBetween(startDate.atStartOfDay(),
				endDate.atStartOfDay());
		CsvGeneration.generateCSV(audits, writer);
	}

	@Override
	public List<Audit> findAuditsBetweenStartAndEndDate(LocalDate startDate, LocalDate endDate) {
		return auditRepository.findByEntryDateBetween(startDate.atStartOfDay(), endDate.atStartOfDay());
	}

	@Override
	public HashMap<String, Double> getAmountFromPaidInvoicesByUserId(long userId) throws UserNotFoundException,
			UserOrganizationNotFoundException, InvoiceOrganizationNotFoundException, InvoiceNotFoundException {
		long organizationIdofRequestedUser = userOrganizationService.findByUserId(userId)
				.getOrganization().getId();
		HashMap<String, Double> paidAmount = new HashMap<>();
		LocalDate today = LocalDate.now();

		double weeklyAmount = 0;
		List<Audit> weeklyAudits = auditRepository.findByEntryDateBetween(today.minusDays(7).atStartOfDay(),
				today.atStartOfDay());
		weeklyAudits
				.removeIf((Audit audit) -> audit.getPaymentStatus() != PaymentStatus.PAYMENT_PAID);
		for (Audit audit : weeklyAudits) {
			long invoiceId = audit.getInvoice().getId();
			long organizationIdofInvoiceId = invoiceOrganizationService
					.findByInvoiceId(invoiceId).getOrganization().getId();
			if (organizationIdofInvoiceId == organizationIdofRequestedUser) {
				weeklyAmount = weeklyAmount + audit.getInvoice().getAmount();
			}
		}

		double monthlyAmount = 0;
		List<Audit> monthlyAudits = auditRepository.findByEntryDateBetween(today.minusDays(7).atStartOfDay(),
				today.atStartOfDay());
		monthlyAudits
				.removeIf((Audit audit) -> audit.getPaymentStatus() != PaymentStatus.PAYMENT_PAID);
		for (Audit audit : monthlyAudits) {
			long invoiceId = audit.getInvoice().getId();
			long organizationIdofInvoiceId = invoiceOrganizationService
					.findByInvoiceId(invoiceId).getOrganization().getId();
			if (organizationIdofInvoiceId == organizationIdofRequestedUser) {
				monthlyAmount = monthlyAmount + audit.getInvoice().getAmount();
			}
		}

		double yearlyAmount = 0;
		List<Audit> yearlyAudits = auditRepository.findByEntryDateBetween(today.minusDays(7).atStartOfDay(),
				today.atStartOfDay());
		yearlyAudits
				.removeIf((Audit audit) -> audit.getPaymentStatus() != PaymentStatus.PAYMENT_PAID);
		for (Audit audit : yearlyAudits) {
			long invoiceId = audit.getInvoice().getId();
			long organizationIdofInvoiceId = invoiceOrganizationService
					.findByInvoiceId(invoiceId).getOrganization().getId();
			if (organizationIdofInvoiceId == organizationIdofRequestedUser) {
				yearlyAmount = yearlyAmount + audit.getInvoice().getAmount();
			}
		}
		paidAmount.put("week", weeklyAmount);
		paidAmount.put("month", monthlyAmount);
		paidAmount.put("year", yearlyAmount);
		return paidAmount;
	}
}