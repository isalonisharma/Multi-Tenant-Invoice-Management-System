package com.caseStudy.utility;

import java.io.PrintWriter;
import java.util.List;

import org.apache.log4j.Logger;

import com.caseStudy.model.AuditModel;

/**
 * Class Name: CsvGeneration
 * 
 * Use: To generate the csv from the list with the help of print writer.
 * 
 * @author saloni.sharma
 */
public class CsvGeneration {
	static final Logger logger = Logger.getLogger(CsvGeneration.class);
	

	/**
	 * Function Name: csvExport Used in
	 * 
	 * Feature: Export the summary of invoices generated in a
	 * week/month/quarter/year in an CSV format
	 * 
	 * Description: It generate the CSV from the audit table list
	 * 
	 * @param auditModelList
	 * @param writer
	 */
	public static void csvExport(List<AuditModel> auditModelList, PrintWriter writer) {
		
		logger.info("CsvGeneration--->>csvExport--->>Start");

		writer.write("Serial No.");
		writer.write(",");

		writer.write("Invoice Id");
		writer.write(",");

		writer.write("Entry Date-Time");
		writer.write(",");

		writer.write("Payment Status");
		writer.write(",");

		writer.write("Description");
		writer.write(",");

		writer.write("\n");

		int counter = 1;
		
		for (AuditModel i : auditModelList) {
			
			writer.write(Integer.toString(counter));
			writer.write(",");

			writer.write(Long.toString(i.getInvoiceModel().getInvoiceId()));
			writer.write(",");

			writer.write(i.getEntryDate().toString());
			writer.write(",");

			writer.write(i.getPaymentStatus().name());
			writer.write(",");

			writer.write(i.getDescription());
			writer.write(",");

			writer.write("\n");
			counter++;
			
		}
		
		writer.flush();
		writer.close();
		
		logger.info("CsvGeneration--->>csvExport--->>End");
	}
}