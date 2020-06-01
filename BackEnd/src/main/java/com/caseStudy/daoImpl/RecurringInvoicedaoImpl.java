package com.caseStudy.daoImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caseStudy.dao.RecurringInvoicedao;
import com.caseStudy.dto.RecurringInvoiceDTO;
import com.caseStudy.enumeration.PaymentStatus;
import com.caseStudy.exception.RecurringInvoiceNotFoundException;
import com.caseStudy.model.InvoiceModel;
import com.caseStudy.model.RecurringInvoiceModel;
import com.caseStudy.repository.RecurringInvoiceRepository;

@Repository
public class RecurringInvoicedaoImpl implements RecurringInvoicedao {
	static final Logger logger = Logger.getLogger(RecurringInvoicedaoImpl.class);

	@Autowired
	private RecurringInvoiceRepository recurringInvoiceRepository;

	@Override
	public boolean createRecurringInvoiceModel(InvoiceModel invoice, PaymentStatus paymentStatus, LocalDateTime dueDate,
			LocalDateTime renewDate, long recurringPeriod) {
		logger.info("RecurringInvoicedaoImpl--->>createRecurringInvoiceModel--->>Start");

		recurringInvoiceRepository
				.save(new RecurringInvoiceModel(invoice, dueDate, renewDate, recurringPeriod, paymentStatus));

		logger.info("RecurringInvoicedaoImpl--->>createRecurringInvoiceModel--->>End");

		return true;
	}

	@Override
	public RecurringInvoiceModel updateRecurringInvoiceModel(Long recurringInvoiceId,
			RecurringInvoiceModel recurringInvoiceDetails) throws RecurringInvoiceNotFoundException {
		logger.info("RecurringInvoicedaoImpl--->>updateRecurringInvoiceModel--->>Start");

		RecurringInvoiceModel recurringInvoice = recurringInvoiceRepository.findById(recurringInvoiceId).orElseThrow(
				() -> new RecurringInvoiceNotFoundException("RegularInvoice not found :: " + recurringInvoiceId));

		recurringInvoice.setDueDate(recurringInvoiceDetails.getDueDate());
		recurringInvoice.setPaymentStatus(recurringInvoiceDetails.getPaymentStatus());
		recurringInvoice.setRecurringPeriod(recurringInvoiceDetails.getRecurringPeriod());
		recurringInvoice.setRenewDate(recurringInvoiceDetails.getRenewDate());

		final RecurringInvoiceModel updatedRecurringInvoice = recurringInvoiceRepository.save(recurringInvoice);

		logger.info("RecurringInvoicedaoImpl--->>updateRecurringInvoiceModel--->>End");

		return updatedRecurringInvoice;
	}

	@Override
	public boolean updatePaymentStatus(InvoiceModel invoice, PaymentStatus paymentStatus) {
		logger.info("RecurringInvoicedaoImpl--->>updatePaymentStatus--->>Start");

		boolean flag = false;

		for (RecurringInvoiceModel recurringInvoiceModel : recurringInvoiceRepository
				.findByInvoiceModelOrderByDueDateAsc(invoice)) {

			if (recurringInvoiceModel.getPaymentStatus() == PaymentStatus.PAYMENT_PAID) {

				continue;
			} else {

				recurringInvoiceModel.setPaymentStatus(paymentStatus);

				recurringInvoiceRepository.save(recurringInvoiceModel);

				if (paymentStatus == PaymentStatus.PAYMENT_PAID) {

					if (recurringInvoiceModel.getRecurringPeriod() != 0) {

						recurringInvoiceRepository
								.save(new RecurringInvoiceModel(recurringInvoiceModel.getInvoiceModel(),
										recurringInvoiceModel.getDueDate().plusMonths(1),
										recurringInvoiceModel.getRenewDate().plusMonths(1),
										recurringInvoiceModel.getRecurringPeriod() - 1, PaymentStatus.PAYMENT_DRAFT));
					}
				}

				flag = true;
				break;
			}
		}
		logger.info("RecurringInvoicedaoImpl--->>updatePaymentStatus--->>End");

		return flag;
	}

	@Override
	public List<RecurringInvoiceModel> findByInvoiceModelSortByDueDateDesc(InvoiceModel invoiceModel) {
		logger.info("RecurringInvoicedaoImpl--->>findByInvoiceModelSortByDueDateDesc--->>Start");

		List<RecurringInvoiceModel> recurringInvoiceList = recurringInvoiceRepository
				.findByInvoiceModelOrderByDueDateDesc(invoiceModel);

		logger.info("RecurringInvoicedaoImpl--->>findByInvoiceModelSortByDueDateDesc--->>End");

		return recurringInvoiceList;
	}

	@Override
	public List<RecurringInvoiceModel> findByInvoiceModel(InvoiceModel invoiceModel) {
		logger.info("RecurringInvoicedaoImpl--->>findByInvoiceModel--->>Start");

		List<RecurringInvoiceModel> recurringInvoiceList = recurringInvoiceRepository.findByInvoiceModel(invoiceModel);

		logger.info("RecurringInvoicedaoImpl--->>findByInvoiceModel--->>End");

		return recurringInvoiceList;
	}

	@Override
	public RecurringInvoiceModel deleteRecurringInvoiceModel(Long recurringInvoiceId)
			throws RecurringInvoiceNotFoundException {
		logger.info("RecurringInvoicedaoImpl--->>deleteRecurringInvoiceModel--->>Start");

		RecurringInvoiceModel recurringInvoiceModel = recurringInvoiceRepository.findById(recurringInvoiceId)
				.orElseThrow(
						() -> new RecurringInvoiceNotFoundException("Invoice Item not found :: " + recurringInvoiceId));

		recurringInvoiceRepository.delete(recurringInvoiceModel);

		logger.info("RecurringInvoicedaoImpl--->>deleteRecurringInvoiceModel--->>End");

		return recurringInvoiceModel;
	}

	@Override
	public RecurringInvoiceDTO RecurringInvoiceModelToRecurringInvoiceDTO(RecurringInvoiceModel recurringInvoiceModel,
			String dateFormat) {
		logger.info("RecurringInvoicedaoImpl--->>RecurringInvoiceModelToRecurringInvoiceDTO--->>Start");

		RecurringInvoiceDTO recurringInvoiceDTO = new RecurringInvoiceDTO(recurringInvoiceModel, dateFormat);

		logger.info("RecurringInvoicedaoImpl--->>RecurringInvoiceModelToRecurringInvoiceDTO--->>End");

		return recurringInvoiceDTO;
	}
}