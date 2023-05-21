package com.case_study;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.case_study.entity.Invoice;
import com.case_study.enumeration.PaymentStatus;
import com.case_study.exception.InvoiceNotFoundException;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.service.InvoiceService;
import com.case_study.service.MailService;
import com.case_study.service.OrganizationService;
import com.case_study.service.UserOrganizationService;
import com.itextpdf.text.DocumentException;

/**
 * Class Name: Scheduler
 * 
 * @author saloni.sharma
 */
@Component
public class Scheduler {
	@Autowired
	@Qualifier("mailService")
	private MailService mailService;

	@Autowired
	@Qualifier("invoiceService")
	private InvoiceService invoiceService;

	@Autowired
	@Qualifier("organizationService")
	private OrganizationService organizationService;

	@Autowired
	@Qualifier("userOrganizationService")
	private UserOrganizationService userOrganizationService;

	/**
	 * FunctionName: scheduleTaskWithCronExpression
	 * 
	 * @throws UserNotFoundException
	 * @throws InvoiceNotFoundException
	 * @throws OrganizationNotFoundException
	 * @throws UserOrganizationNotFoundException
	 * @throws IOException
	 *
	 *                                           The @Scheduled annotation is used
	 *                                           to trigger the scheduler for a
	 *                                           specific time period.
	 * @throws DocumentException
	 * 
	 * @Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of
	 *                 week] [Year]")
	 * 
	 *                 Java cron expressions are used to configure the instances of
	 *                 CronTrigger, a subclass of org.quartz.Trigger, it uses
	 *                 cron-like expressions to determine when to execute the
	 *                 method.
	 * 
	 *                 Here now it is set to fire at 12 PM every day
	 */
	@Scheduled(cron = "0 0 12 * * ?")
	public void scheduleTaskWithCronExpression() throws UserNotFoundException, InvoiceNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {
		boolean organizationSendMailFlag = false;
		List<Invoice> dueInvoiceTomorrowList = invoiceService.getDueInvoicesForTomorrow();
		for (Invoice invoiceModel : dueInvoiceTomorrowList) {
			organizationSendMailFlag = organizationService.findById(
					userOrganizationService.findByUserId(invoiceModel.getUser().getId())
							.getOrganization().getId())
					.isThankYouEMail();
			if (organizationSendMailFlag) {
				mailService.sendDueMail(invoiceModel, "Tommorow");
			}
		}
		List<Invoice> dueInvoiceTodayList = invoiceService.getDueInvoicesForToday();
		for (Invoice invoiceModel : dueInvoiceTodayList) {
			organizationSendMailFlag = organizationService.findById(
					userOrganizationService.findByUserId(invoiceModel.getUser().getId())
							.getOrganization().getId())
					.isThankYouEMail();
			if (organizationSendMailFlag) {
				mailService.sendDueMail(invoiceModel, "Today");
			}
			invoiceService.updatePaymentStatus(invoiceModel.getId(), PaymentStatus.PAYMENT_DUE);
		}
		List<Invoice> dueInvoicePastWeekList = invoiceService.getDueInvoicesForPastweek();
		for (Invoice invoiceModel : dueInvoicePastWeekList) {
			organizationSendMailFlag = organizationService.findById(
					userOrganizationService.findByUserId(invoiceModel.getUser().getId())
							.getOrganization().getId())
					.isThankYouEMail();
			if (organizationSendMailFlag) {
				mailService.sendDueMail(invoiceModel, "PastWeek");
			}
		}
		List<Invoice> dueInvoiceYesterdayList = invoiceService.getDueInvoicesForYesterday();
		for (Invoice invoiceModel : dueInvoiceYesterdayList) {
			organizationSendMailFlag = organizationService.findById(
					userOrganizationService.findByUserId(invoiceModel.getUser().getId())
							.getOrganization().getId())
					.isThankYouEMail();
			if (organizationSendMailFlag == true) {
				mailService.sendDueMail(invoiceModel, "Yesterday");
			}
		}
	}
}