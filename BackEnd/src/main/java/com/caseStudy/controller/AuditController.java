package com.caseStudy.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.InvoiceOrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.service.AuditService;

@RestController
public class AuditController {

	static final Logger logger = Logger.getLogger(AuditController.class);

	@Autowired
	AuditService auditService;

	@PreAuthorize("hasAnyRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/csvgeneration/{startDate}/{endDate}/summaryofinvoices.csv")
	void csvGeneration(@PathVariable(value = "startDate") String startDate,
			@PathVariable(value = "endDate") String endDate, HttpServletResponse response) throws IOException {

		logger.info("AuditController--->>csvGeneration--->>Start");

		response.setContentType("text/csv;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment: file=summaryofinvoices.csv");

		auditService.csvGeneration(LocalDate.parse(startDate), LocalDate.parse(endDate), response.getWriter());

		logger.info("AuditController--->>csvGeneration--->>Ended");
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/amountfrompaidinvoices/{id}")
	ResponseEntity<HashMap<String, Double>> AmountFromPaidInvoices(@PathVariable(value = "id") long userId)
			throws UserNotFoundException, UserOrganizationNotFoundException, InvoiceOrganizationNotFoundException,
			InvoiceNotFoundException {
		logger.info("AuditController--->>AmountFromPaidInvoices--->>Start");
		
		HashMap<String, Double> paidAmount = auditService.AmountFromPaidInvoices(userId);
		
		logger.info("AuditController--->>AmountFromPaidInvoices--->>End");
		
		return ResponseEntity.ok().body(paidAmount);
	}

}
