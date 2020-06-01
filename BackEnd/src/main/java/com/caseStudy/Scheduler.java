package com.caseStudy;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.caseStudy.enumeration.PaymentStatus;
import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.InvoiceModel;
import com.caseStudy.service.InvoiceService;
import com.caseStudy.service.MailService;
import com.caseStudy.service.OrganizationService;
import com.caseStudy.service.UserOrganizationService;
import com.itextpdf.text.DocumentException;

/**
 * Class Name: Scheduler
 * 
 * @author saloni.sharma
 */
@Component
public class Scheduler {
	private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);

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

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	/**
	 * FunctionName: scheduleTaskWithCronExpression
	 * 
	 * @throws UserNotFoundException
	 * @throws InvoiceNotFoundException
	 * @throws OrganizationNotFoundException
	 * @throws UserOrganizationNotFoundException
	 * @throws IOException
	 *
	 * The @Scheduled annotation is used to trigger the scheduler for a
	 * specific time period.
	 * @throws DocumentException 
	 * 
	 * @Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of
	 *                 week] [Year]")
	 * 
	 * Java cron expressions are used to configure the instances of
	 * CronTrigger, a subclass of org.quartz.Trigger, it uses
	 * cron-like expressions to determine when to execute the
	 * method.
	 * 
	 * Here now it is set to fire at 12 PM every day
	 */
	@Scheduled(cron = "0 0 12 * * ?")
	public void scheduleTaskWithCronExpression() throws UserNotFoundException, InvoiceNotFoundException,
			OrganizationNotFoundException, UserOrganizationNotFoundException, IOException, DocumentException {
		logger.info("Scheduler--->>scheduleTaskWithCronExpression--->>Start");

		logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));

		boolean organizationSendMailFlag = false;

		logger.info("Scheduler--->>scheduleTaskWithCronExpression--->>dueInvoiceTomorrowList--->>Start");

		List<InvoiceModel> dueInvoiceTomorrowList = invoiceService.getDueInvoiceModelsTomorrow();

		for (InvoiceModel invoiceModel : dueInvoiceTomorrowList) {
			organizationSendMailFlag = false;

			organizationSendMailFlag = organizationService.getOrganizationModelById(
					userOrganizationService.getUserOrganizationModelByUserId(invoiceModel.getUserModel().getUserId())
							.getOrganizationModel().getOrganizationId())
					.isOrganizationThankYouMail();

			if (organizationSendMailFlag == true) {
				mailService.sendDueMail(invoiceModel, "Tommorow");
			}
		}
		logger.info("Scheduler--->>scheduleTaskWithCronExpression--->>dueInvoiceTomorrowList--->>Ended");

		logger.info("Scheduler--->>scheduleTaskWithCronExpression--->>dueInvoiceTodayList--->>Start");

		List<InvoiceModel> dueInvoiceTodayList = invoiceService.getDueInvoiceModelsToday();

		for (InvoiceModel invoiceModel : dueInvoiceTodayList) {
			organizationSendMailFlag = false;

			organizationSendMailFlag = organizationService.getOrganizationModelById(
					userOrganizationService.getUserOrganizationModelByUserId(invoiceModel.getUserModel().getUserId())
							.getOrganizationModel().getOrganizationId())
					.isOrganizationThankYouMail();

			if (organizationSendMailFlag == true) {
				mailService.sendDueMail(invoiceModel, "Today");
			}
			invoiceService.updatePaymentStatus(invoiceModel.getInvoiceId(), PaymentStatus.PAYMENT_DUE);
		}
		logger.info("Scheduler--->>scheduleTaskWithCronExpression--->>dueInvoiceTodayList--->>Ended");

		logger.info("Scheduler--->>scheduleTaskWithCronExpression--->>dueInvoicePastWeekList--->>Start");

		List<InvoiceModel> dueInvoicePastWeekList = invoiceService.getDueInvoiceModelsPastweek();

		for (InvoiceModel invoiceModel : dueInvoicePastWeekList) {
			organizationSendMailFlag = false;

			organizationSendMailFlag = organizationService.getOrganizationModelById(
					userOrganizationService.getUserOrganizationModelByUserId(invoiceModel.getUserModel().getUserId())
							.getOrganizationModel().getOrganizationId())
					.isOrganizationThankYouMail();

			if (organizationSendMailFlag == true) {
				mailService.sendDueMail(invoiceModel, "PastWeek");
			}
		}
		logger.info("Scheduler--->>scheduleTaskWithCronExpression--->>dueInvoicePastWeekList--->>Ended");

		logger.info("Scheduler--->>scheduleTaskWithCronExpression--->>dueInvoiceYesterdayList--->>Start");

		List<InvoiceModel> dueInvoiceYesterdayList = invoiceService.getDueInvoiceModelsYesterday();

		for (InvoiceModel invoiceModel : dueInvoiceYesterdayList) {
			organizationSendMailFlag = false;

			organizationSendMailFlag = organizationService.getOrganizationModelById(
					userOrganizationService.getUserOrganizationModelByUserId(invoiceModel.getUserModel().getUserId())
							.getOrganizationModel().getOrganizationId())
					.isOrganizationThankYouMail();

			if (organizationSendMailFlag == true) {
				mailService.sendDueMail(invoiceModel, "Yesterday");
			}
		}
		logger.info("Scheduler--->>scheduleTaskWithCronExpression--->>dueInvoiceYesterdayList--->>Ended");

		logger.info("Scheduler--->>scheduleTaskWithCronExpression--->>Ended");
	}
}