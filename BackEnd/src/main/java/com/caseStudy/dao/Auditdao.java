package com.caseStudy.dao;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.InvoiceOrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.AuditModel;

public interface Auditdao {
	// Create Operation
	AuditModel createAuditModel(AuditModel audit);

	// Getting all the entries from audit table
	List<AuditModel> getAllAuditModel();

	// CSV generation of the specific entries of audit table with the help of given
	// start date and end date
	void csvGeneration(LocalDate startDate, LocalDate endDate, PrintWriter writer);

	// Get Audit Model List between specific dates
	List<AuditModel> getAuditModelBetweenDates(LocalDate startDate, LocalDate endDate);

	// Getting the amount paid in weekly, monthly and yearly from audit table
	// entries using user id
	HashMap<String, Double> AmountFromPaidInvoices(long userId) throws UserNotFoundException,
			UserOrganizationNotFoundException, InvoiceOrganizationNotFoundException, InvoiceNotFoundException;
}
