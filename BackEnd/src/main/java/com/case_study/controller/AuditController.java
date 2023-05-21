package com.case_study.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.case_study.exception.InvoiceNotFoundException;
import com.case_study.exception.InvoiceOrganizationNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.service.AuditService;

@RestController
public class AuditController {
	@Autowired
	private AuditService auditService;

	@PreAuthorize("hasAnyRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("generate/csv/{startDate}/{endDate}/summaryofinvoices.csv")
	void generateCSV(@PathVariable(value = "startDate") String startDate,
			@PathVariable(value = "endDate") String endDate, HttpServletResponse response) throws IOException {
		response.setContentType("text/csv;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment: file=summaryofinvoices.csv");
		auditService.generateCSV(LocalDate.parse(startDate), LocalDate.parse(endDate), response.getWriter());
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("users/{id}/invoices/paid/amount")
	ResponseEntity<HashMap<String, Double>> getAmountFromPaidInvoices(@PathVariable(value = "id") long userId)
			throws UserNotFoundException, UserOrganizationNotFoundException, InvoiceOrganizationNotFoundException,
			InvoiceNotFoundException {
		return ResponseEntity.ok().body(auditService.getAmountFromPaidInvoicesByUserId(userId));
	}
}