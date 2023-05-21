package com.case_study.utility;

import java.io.PrintWriter;
import java.util.List;

import com.case_study.entity.Audit;

/**
 * Class Name: CsvGeneration
 * 
 * Use: To generate the CSV from the list with the help of print writer.
 * 
 * @author saloni.sharma
 */
public class CsvGeneration {
	/**
	 * Function Name: csvExport Used in
	 * 
	 * Feature: Export the summary of invoices generated in a
	 * week/month/quarter/year in an CSV format
	 * 
	 * Description: It generate the CSV from the audit table list
	 * 
	 * @param audits
	 * @param writer
	 */
	public static void generateCSV(List<Audit> audits, PrintWriter writer) {
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
		for (Audit audit : audits) {
			writer.write(Integer.toString(counter));
			writer.write(",");

			writer.write(Long.toString(audit.getInvoice().getId()));
			writer.write(",");

			writer.write(audit.getEntryDate().toString());
			writer.write(",");

			writer.write(audit.getPaymentStatus().name());
			writer.write(",");

			writer.write(audit.getDescription());
			writer.write(",");

			writer.write("\n");
			counter++;
		}
		writer.flush();
		writer.close();
	}
}