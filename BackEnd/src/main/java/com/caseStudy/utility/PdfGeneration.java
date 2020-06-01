package com.caseStudy.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.caseStudy.model.InvoiceItemModel;
import com.caseStudy.model.InvoiceModel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Class Name: PdfGeneration Description: PDF generation from model
 * 
 * @author saloni.sharma
 */
public class PdfGeneration {

	static final Logger logger = Logger.getLogger(PdfGeneration.class);

	/**
	 * Function Name: invoicePdf Feature: PDF of the invoice Description: It
	 * generate the PDF from the invoice
	 * 
	 * @param invoiceModel
	 * @param invoiceItemList
	 * @param organizationName
	 * @param dueDate
	 * @param organizationImage
	 * @param organizationDateFormat
	 * @param organizationCurrency
	 * 
	 * @return ByteArrayInputStream
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws DocumentException 
	 */
	public static ByteArrayInputStream invoicePdf(InvoiceModel invoiceModel, List<InvoiceItemModel> invoiceItemList,
			String organizationName, LocalDate dueDate, File organizationImage, String organizationDateFormat,
			String organizationCurrency) throws MalformedURLException, IOException, DocumentException {

		logger.info("PdfGeneration--->>invoicePdf--->>Start");

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		PdfWriter.getInstance(document, out);
		document.open();

		Image image = null;
		try {

			image = Image.getInstance(organizationImage.getAbsolutePath().toString());
		} catch (IOException e) {
			logger.info("PdfGeneration--->>invoicePdf--->>Exception Occured: " + e.getMessage() + e);

			logger.info("PdfGeneration--->>invoicePdf--->>Using Default Logo");

			image = Image.getInstance("//uploads//defaultLogo.png");

		}

		image.scaleAbsolute(25f, 25f);
		image.scalePercent(50);
		image.setAbsolutePosition(20, 730);

		// image added successfully in the document
		document.add(image);

		Font font = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.BLACK);

		Paragraph headingInvoice = new Paragraph("INVOICE", font);

		headingInvoice.setAlignment(Element.ALIGN_RIGHT);

		document.add(headingInvoice);
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);

		// creating new table with the help of PdfTable of itext

		PdfPTable tableheader = new PdfPTable(3);

		Stream.of("Bill By", "Bill To", "Invoice Id : " + invoiceModel.getInvoiceId()).forEach(headerTitletop -> {
			PdfPCell tableHeadertop = new PdfPCell();

			Font headFontTop = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			tableHeadertop.setBorder(Rectangle.NO_BORDER);
			tableHeadertop.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHeadertop.setPhrase(new Phrase(headerTitletop, headFontTop));

			tableheader.addCell(tableHeadertop);
		});

		PdfPCell userNameCell = new PdfPCell(
				new Phrase(invoiceModel.getUserModel().getFirstName() + " " + invoiceModel.getUserModel().getLastName(),
						FontFactory.getFont(FontFactory.HELVETICA, 11)));

		userNameCell.setPaddingLeft(4);
		userNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		userNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		userNameCell.setBorder(Rectangle.NO_BORDER);

		tableheader.addCell(userNameCell);

		PdfPCell clientNameCell = new PdfPCell(new Phrase(
				invoiceModel.getClientModel().getFirstName() + " " + invoiceModel.getClientModel().getLastName(),
				FontFactory.getFont(FontFactory.HELVETICA, 11)));

		clientNameCell.setPaddingLeft(4);
		clientNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		clientNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		clientNameCell.setBorder(Rectangle.NO_BORDER);

		tableheader.addCell(clientNameCell);

		// Change the date format according to the Organization Date Policy

		PdfPCell dateCell = new PdfPCell(new Phrase("Date Placed : " + invoiceModel.getDatePlaced().toLocalDate(),
				FontFactory.getFont(FontFactory.HELVETICA, 11)));

		dateCell.setPaddingLeft(4);
		dateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		dateCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		dateCell.setBorder(Rectangle.NO_BORDER);

		tableheader.addCell(dateCell);

		PdfPCell userEmailCell = new PdfPCell(
				new Phrase(invoiceModel.getUserModel().getUsername(), FontFactory.getFont(FontFactory.HELVETICA, 11)));

		userEmailCell.setPaddingLeft(4);
		userEmailCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		userEmailCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		userEmailCell.setBorder(Rectangle.NO_BORDER);

		tableheader.addCell(userEmailCell);

		PdfPCell clientEmailCell = new PdfPCell(
				new Phrase(invoiceModel.getClientModel().getEmailId(), FontFactory.getFont(FontFactory.HELVETICA, 11)));

		clientEmailCell.setPaddingLeft(4);
		clientEmailCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		clientEmailCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		clientEmailCell.setBorder(Rectangle.NO_BORDER);

		tableheader.addCell(clientEmailCell);

		// Change the date format according to the Organization Date Policy

		PdfPCell dueDateCell = new PdfPCell(
				new Phrase("Due Date : " + dueDate, FontFactory.getFont(FontFactory.HELVETICA, 11)));

		dueDateCell.setPaddingLeft(4);
		dueDateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		dueDateCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		dueDateCell.setBorder(Rectangle.NO_BORDER);

		tableheader.addCell(dueDateCell);

		PdfPCell userOrgCell = new PdfPCell(
				new Phrase(organizationName, FontFactory.getFont(FontFactory.HELVETICA, 11)));

		userOrgCell.setPaddingLeft(4);
		userOrgCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		userOrgCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		userOrgCell.setBorder(Rectangle.NO_BORDER);

		tableheader.addCell(userOrgCell);

		PdfPCell clientOrgCell = new PdfPCell(new Phrase(invoiceModel.getClientModel().getOrganization(),
				FontFactory.getFont(FontFactory.HELVETICA, 11)));

		clientOrgCell.setPaddingLeft(4);
		clientOrgCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		clientOrgCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		clientOrgCell.setBorder(Rectangle.NO_BORDER);

		tableheader.addCell(clientOrgCell);

		String invoiceType = "Regular";
		if (invoiceModel.isInvoiceIsRecurring())
			invoiceType = "Recurring";

		PdfPCell invoiceTypeCell = new PdfPCell(
				new Phrase("Invoice Type : " + invoiceType, FontFactory.getFont(FontFactory.HELVETICA, 11)));

		invoiceTypeCell.setPaddingLeft(4);
		invoiceTypeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		invoiceTypeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		invoiceTypeCell.setBorder(Rectangle.NO_BORDER);

		tableheader.addCell(invoiceTypeCell);

		document.add(tableheader);

		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);

		PdfPTable table = new PdfPTable(5);
		// Add PDF Table Header ->
		Stream.of("Serial No.", "Item Name", "Quantity", "Unit Price", "Amount").forEach(headerTitle -> {
			PdfPCell header = new PdfPCell();

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setHorizontalAlignment(Element.ALIGN_CENTER);
			header.setBorderWidth(2);
			header.setPhrase(new Phrase(headerTitle, headFont));

			table.addCell(header);
		});

		int count = 1;
		for (InvoiceItemModel invoiceItem : invoiceItemList) {
			PdfPCell snoCell = new PdfPCell(new Phrase(Integer.toString(count)));
			count++;
			snoCell.setPaddingLeft(4);
			snoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			snoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(snoCell);

			PdfPCell itemNameCell = new PdfPCell(new Phrase(invoiceItem.getItemModel().getItemName()));
			itemNameCell.setPaddingLeft(4);
			itemNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			itemNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(itemNameCell);

			PdfPCell qtyCell = new PdfPCell(new Phrase(Long.toString(invoiceItem.getQuantity())));
			qtyCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			qtyCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			qtyCell.setPaddingRight(4);
			table.addCell(qtyCell);

			PdfPCell unitPriceCell = new PdfPCell(new Phrase(Float.toString(invoiceItem.getItemModel().getItemRate())));
			unitPriceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			unitPriceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			unitPriceCell.setPaddingRight(4);
			table.addCell(unitPriceCell);

			PdfPCell amountCell = new PdfPCell(
					new Phrase(Float.toString(invoiceItem.getItemModel().getItemRate() * invoiceItem.getQuantity())));
			amountCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			amountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			amountCell.setPaddingRight(4);
			table.addCell(amountCell);
		}

		PdfPCell empty1Cell = new PdfPCell(new Phrase(" "));
		empty1Cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(empty1Cell);

		PdfPCell empty2Cell = new PdfPCell(new Phrase(" "));
		empty2Cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(empty2Cell);

		PdfPCell empty3Cell = new PdfPCell(new Phrase(" "));
		empty3Cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(empty3Cell);

		PdfPCell totalNameCell = new PdfPCell(new Phrase("Total", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
		totalNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		totalNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalNameCell.setPaddingRight(4);
		table.addCell(totalNameCell);

		PdfPCell totalCell = new PdfPCell(
				new Phrase(organizationCurrency + " " + Double.toString(invoiceModel.getAmount()),
						FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
		totalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalCell.setPaddingRight(4);
		table.addCell(totalCell);

		document.add(table);
		document.close();

		logger.info("PdfGeneration--->>invoicePdf--->>End");

		return new ByteArrayInputStream(out.toByteArray());
	}
}
