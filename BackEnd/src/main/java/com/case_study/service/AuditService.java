package com.case_study.service;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import com.case_study.entity.Audit;
import com.case_study.exception.InvoiceNotFoundException;
import com.case_study.exception.InvoiceOrganizationNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;

public interface AuditService {

	Audit createAudit(Audit audit);

	List<Audit> findAll();

	void generateCSV(LocalDate startDate, LocalDate endDate, PrintWriter writer);

	List<Audit> findAuditsBetweenStartAndEndDate(LocalDate startDate, LocalDate endDate);

	HashMap<String, Double> getAmountFromPaidInvoicesByUserId(long userId) throws UserNotFoundException,
			UserOrganizationNotFoundException, InvoiceOrganizationNotFoundException, InvoiceNotFoundException;
}