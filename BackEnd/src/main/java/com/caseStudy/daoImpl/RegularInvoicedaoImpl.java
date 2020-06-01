package com.caseStudy.daoImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caseStudy.dao.RegularInvoicedao;
import com.caseStudy.dto.RegularInvoiceDTO;
import com.caseStudy.enumeration.PaymentStatus;
import com.caseStudy.exception.RegularInvoiceNotFoundException;
import com.caseStudy.model.InvoiceModel;
import com.caseStudy.model.RegularInvoiceModel;
import com.caseStudy.repository.RegularInvoiceRepository;

@Repository
public class RegularInvoicedaoImpl implements RegularInvoicedao {
	static final Logger logger = Logger.getLogger(RegularInvoicedaoImpl.class);

	@Autowired
	private RegularInvoiceRepository regularInvoiceRepository;

	@Override
	public boolean createRegularInvoiceModel(InvoiceModel invoice, PaymentStatus paymentStatus, LocalDateTime dueDate) {
		logger.info("RegularInvoicedaoImpl--->>createRegularInvoiceModel--->>Start");

		regularInvoiceRepository.save(new RegularInvoiceModel(invoice, dueDate, paymentStatus));

		logger.info("RegularInvoicedaoImpl--->>createRegularInvoiceModel--->>End");

		return true;
	}

	@Override
	public RegularInvoiceModel updateRegularInvoiceModel(Long regularInvoiceId,
			RegularInvoiceModel regularInvoiceDetails) throws RegularInvoiceNotFoundException {
		logger.info("RegularInvoicedaoImpl--->>updateRegularInvoiceModel--->>Start");

		RegularInvoiceModel regularInvoice = regularInvoiceRepository.findById(regularInvoiceId).orElseThrow(
				() -> new RegularInvoiceNotFoundException("RegularInvoice not found :: " + regularInvoiceId));

		regularInvoice.setDueDate(regularInvoiceDetails.getDueDate());
		regularInvoice.setPaymentStatus(regularInvoiceDetails.getPaymentStatus());

		final RegularInvoiceModel updatedRegularInvoiceModel = regularInvoiceRepository.save(regularInvoice);

		logger.info("RegularInvoicedaoImpl--->>updateRegularInvoiceModel--->>End");

		return updatedRegularInvoiceModel;
	}

	@Override
	public boolean updatePaymentStatus(InvoiceModel invoiceModel, PaymentStatus paymentStatus) {
		logger.info("RegularInvoicedaoImpl--->>updatePaymentStatus--->>Start");

		boolean flag = false;

		for (RegularInvoiceModel regularInvoiceModel : regularInvoiceRepository.findByInvoiceModel(invoiceModel)) {
			regularInvoiceModel.setPaymentStatus(paymentStatus);

			regularInvoiceRepository.save(regularInvoiceModel);
		}

		flag = true;

		logger.info("RegularInvoicedaoImpl--->>updatePaymentStatus--->>End");

		return flag;
	}

	@Override
	public List<RegularInvoiceModel> findByInvoiceModelSortByDueDateDesc(InvoiceModel invoiceModel) {
		logger.info("RegularInvoicedaoImpl--->>findByInvoiceModelSortByDueDateDesc--->>Start");

		List<RegularInvoiceModel> regularInvoiceList = regularInvoiceRepository
				.findByInvoiceModelOrderByDueDateDesc(invoiceModel);

		logger.info("RegularInvoicedaoImpl--->>findByInvoiceModelSortByDueDateDesc--->>End");

		return regularInvoiceList;
	}

	@Override
	public List<RegularInvoiceModel> findByInvoiceModel(InvoiceModel invoiceModel) {
		logger.info("RegularInvoicedaoImpl--->>findByInvoiceModel--->>Start");

		List<RegularInvoiceModel> regularInvoiceList = regularInvoiceRepository.findByInvoiceModel(invoiceModel);

		logger.info("RegularInvoicedaoImpl--->>findByInvoiceModel--->>End");

		return regularInvoiceList;
	}

	@Override
	public RegularInvoiceModel deleteRegularInvoiceModel(Long regularInvoiceId) throws RegularInvoiceNotFoundException {
		logger.info("RegularInvoicedaoImpl--->>deleteRegularInvoiceModel--->>Start");

		RegularInvoiceModel regularInvoiceModel = regularInvoiceRepository.findById(regularInvoiceId).orElseThrow(
				() -> new RegularInvoiceNotFoundException("Regular Invoice not found :: " + regularInvoiceId));

		regularInvoiceRepository.delete(regularInvoiceModel);

		logger.info("RegularInvoicedaoImpl--->>deleteRegularInvoiceModel--->>End");

		return regularInvoiceModel;
	}

	@Override
	public RegularInvoiceDTO RegularInvoiceModelToRegularInvoiceDTO(RegularInvoiceModel regularInvoiceModel,
			String dateFormat) {
		logger.info("RegularInvoicedaoImpl--->>RegularInvoiceModelToRegularInvoiceDTO--->>Start");

		RegularInvoiceDTO regularInvoiceDTO = new RegularInvoiceDTO(regularInvoiceModel, dateFormat);

		logger.info("RegularInvoicedaoImpl--->>RegularInvoiceModelToRegularInvoiceDTO--->>End");

		return regularInvoiceDTO;
	}
}
