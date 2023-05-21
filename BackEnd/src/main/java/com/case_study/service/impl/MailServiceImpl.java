package com.case_study.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.case_study.bean.MailBean;
import com.case_study.entity.Invoice;
import com.case_study.exception.InvoiceNotFoundException;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.service.InvoiceService;
import com.case_study.service.MailService;
import com.itextpdf.text.DocumentException;


@Service("mailService")
public class MailServiceImpl implements MailService {
	@Autowired
	JavaMailSender mailSender;

	@Autowired
	InvoiceService invoiceService;

	private static String MAILFROM;

	@Value("${spring.mail.username}")
	public void setSvnUrl(String mailFrom) {
		MAILFROM = mailFrom;
	}

	private Boolean sendEmail(MailBean mailBean) throws IOException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setSubject(mailBean.getMailSubject());
			mimeMessageHelper
					.setFrom(new InternetAddress(mailBean.getMailFrom(), "Multi-Tenant Invoice Management System"));
			mimeMessageHelper.setTo(mailBean.getMailTo());
			mimeMessageHelper.setText(mailBean.getMailContent());

			ByteArrayInputStream bis = mailBean.getAttachment();
			File file = null;

			file = File.createTempFile("temp", ".pdf");

			try (FileOutputStream outputStream = new FileOutputStream(file)) {

				int read;
				byte[] bytes = new byte[1024];

				while ((read = bis.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
			}

			mimeMessageHelper.addAttachment(mailBean.getAttachmentName(), file);
			file.deleteOnExit();

			mailSender.send(mimeMessageHelper.getMimeMessage());

		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Boolean sendThankyouMail(Invoice invoice) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {
		String name = invoice.getClient().getFirstName() + " " + invoice.getClient().getLastName();
		MailBean thankyouMailBean = new MailBean();
		thankyouMailBean.setMailTo(invoice.getClient().getEmailId());
		thankyouMailBean.setMailFrom(MAILFROM);
		thankyouMailBean.setMailSubject("Thank you for the payment of invoice number: " + invoice.getId());
		thankyouMailBean.setMailContent("Hi " + name + ",\n"
				+ "I hope you’re well. This is just a thank you for the payment of invoice number " + invoice.getId()
				+ "Thank you for your business today. It was a pleasure helping you to find what you needed for your purchase. "
				+ "Our team is always ready to help you discover new products to best suit your future needs. "
				+ "We hope to work with you again, very soon." + "\nKind Regards,\n" + "SmartInvoiceBox");
		thankyouMailBean.setAttachmentName("Invoice No. # " + invoice.getId());
		thankyouMailBean.setAttachment(invoiceService.generatePDF(invoice.getId()));
		return sendEmail(thankyouMailBean);
	}

	@Override
	public Boolean sendDueMail(Invoice invoice, String dueDate)
			throws UserNotFoundException, OrganizationNotFoundException, UserOrganizationNotFoundException, IOException,
			InvoiceNotFoundException, DocumentException {
		long invoiceId = invoice.getId();
		String name = invoice.getClient().getFirstName() + " " + invoice.getClient().getLastName();
		MailBean dueMailBean = new MailBean();
		dueMailBean.setMailTo(invoice.getClient().getEmailId());
		dueMailBean.setMailFrom(MAILFROM);
		if (dueDate.equals("Tommorow")) {
			dueMailBean.setMailSubject("Follow-up on invoice: " + invoiceId);
			dueMailBean.setMailContent(
					"Hi " + name + ",\n" + "I hope you’re well. This is just to remind you that payment on invoice "
							+ invoiceId + ", which we sent will be due tomorrow.\n"
							+ "I’m sure you’re busy, but I’d appreciate if you could take a moment and look over "
							+ "the invoice when you get a chance. Please let me know if you have any questions."
							+ "Kind Regards,\n" + "SmartInvoiceBox");
		} else if (dueDate.equals("Today")) {
			dueMailBean.setMailSubject("Invoice: " + invoiceId + "  is due today.");
			dueMailBean.setMailContent("Hi " + " " + name + ",\n"
					+ "We hope you’re well. This is just a reminder that payment on invoice " + invoiceId
					+ ", which is due today. You can make payment to the bank account specified on the invoice."
					+ "\nIf you have any questions whatsoever, please reply and We will be happy to clarify them."
					+ "Kind Regards,\n" + "SmartInvoiceBox");
		} else if (dueDate.equals("PastWeek")) {
			dueMailBean.setMailSubject("Invoice " + invoiceId + " is one week overdue");
			dueMailBean.setMailContent("Hi " + name + ",\n"
					+ "Our records show that we haven’t yet received payment for Invoice " + invoiceId
					+ ", which is overdue by one week. I would appreciate if you could check this out on your end."
					+ "Please be aware that, as per our terms, "
					+ "We may charge you additional interest on payment received more than 7 days past its due date.\n"
					+ "Again, reach out if you have any questions on this payment. "
					+ "Otherwise, please organize for settlement of this invoice immediately." + "\nKind Regards,\n"
					+ "SmartInvoiceBox");

		} else if (dueDate.equals("Yesterday")) {
			dueMailBean.setMailSubject("Invoice " + invoiceId + " was due Yesterday");
			dueMailBean.setMailContent("Hi " + name + ",\n"
					+ "Our records show that we haven’t yet received payment for Invoice " + invoiceId
					+ ", which is due from today. I would appreciate if you could check this out on your end."
					+ "Please be aware that, as per our terms, "
					+ "We may charge you additional interest on payment received more than 7 days past its due date.\n"
					+ "Again, reach out if you have any questions on this payment. "
					+ "Otherwise, please organize for settlement of this invoice immediately." + "\nKind Regards,\n"
					+ "SmartInvoiceBox");

		}
		dueMailBean.setAttachmentName("Invoice No. # " + invoiceId);
		dueMailBean.setAttachment(invoiceService.generatePDF(invoiceId));
		return sendEmail(dueMailBean);
	}

	@Override
	public Boolean sendInvoiceMail(Invoice invoice) throws InvoiceNotFoundException, UserNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {
		String name = invoice.getClient().getFirstName() + " " + invoice.getClient().getLastName();
		MailBean sentInvoiceMailBean = new MailBean();
		sentInvoiceMailBean.setMailTo(invoice.getClient().getEmailId());
		sentInvoiceMailBean.setMailFrom(MAILFROM);
		sentInvoiceMailBean.setMailSubject("Invoice has been generated of the invoice number: " + invoice.getId());
		sentInvoiceMailBean.setMailContent("Hi " + name + ",\n"
				+ "I hope you’re well. The Invoice has been generated for the  invoice number " + invoice.getId()
				+ "Thank you for your business today. It was a pleasure helping you to find what you needed for your purchase. "
				+ "Our team is always ready to help you discover new products to best suit your future needs. "
				+ "We hope to work with you again, very soon." + "\nKind Regards,\n" + "SmartInvoiceBox");
		sentInvoiceMailBean.setAttachmentName("Invoice No. # " + invoice.getId());
		sentInvoiceMailBean.setAttachment(invoiceService.generatePDF(invoice.getId()));
		return sendEmail(sentInvoiceMailBean);
	}
}