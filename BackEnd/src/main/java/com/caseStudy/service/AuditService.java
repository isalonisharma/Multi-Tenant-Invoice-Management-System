package com.caseStudy.service;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.InvoiceOrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.AuditModel;

/**
 * Class: AuditService
 * 
 * Use: CSV Generation Feature
 * 
 * @author saloni.sharma
 */
public interface AuditService {

	/**
	 * Function Name: getAllAuditModel
	 * 
	 * @return List of Audit Model
	 */
	List<AuditModel> getAllAuditModel();

	/**
	 * Function Name: createAuditModel
	 * 
	 * @param auditModel
	 * 
	 * @return AuditModel
	 */
	AuditModel createAuditModel(AuditModel auditModel);

	/**
	 * Function Name: csvGeneration
	 * 
	 * @param startDate
	 * @param endDate
	 * @param writer
	 */
	void csvGeneration(LocalDate startDate, LocalDate endDate, PrintWriter writer);

	/**
	 * Function Name: getAuditModelBetweenDates
	 * 
	 * @param startDate
	 * @param endDate
	 */
	List<AuditModel> getAuditModelBetweenDates(LocalDate startDate, LocalDate endDate);

	/**
	 * Function Name: AmountFromPaidInvoices
	 * 
	 * Description: Getting the amount paid in weekly, monthly and yearly from audit
	 * table entries using user id
	 * 
	 * @param userId
	 * 
	 * @return HashMap<String, Double>
	 * 
	 * @throws UserNotFoundException
	 * @throws UserOrganizationNotFoundException
	 * @throws InvoiceOrganizationNotFoundException
	 * @throws InvoiceNotFoundException
	 */
	HashMap<String, Double> AmountFromPaidInvoices(long userId) throws UserNotFoundException,
			UserOrganizationNotFoundException, InvoiceOrganizationNotFoundException, InvoiceNotFoundException;
}
