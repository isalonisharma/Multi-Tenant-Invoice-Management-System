package com.caseStudy.daoImpl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caseStudy.bean.CreateInvoiceItemBean;
import com.caseStudy.dao.InvoiceItemdao;
import com.caseStudy.exception.InvoiceItemNotFoundException;
import com.caseStudy.exception.InvoiceNotFoundException;
import com.caseStudy.exception.ItemNotFoundException;
import com.caseStudy.model.InvoiceItemModel;
import com.caseStudy.model.InvoiceModel;
import com.caseStudy.model.ItemModel;
import com.caseStudy.repository.InvoiceItemRepository;
import com.caseStudy.service.InvoiceService;
import com.caseStudy.service.ItemService;

@Repository
public class InvoiceItemdaoImpl implements InvoiceItemdao {
	static final Logger logger = Logger.getLogger(InvoiceItemdaoImpl.class);

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private InvoiceItemRepository invoiceItemRepository;

	@Override
	public List<InvoiceItemModel> findByinvoiceId(long invoiceId) throws InvoiceNotFoundException {
		logger.info("InvoiceItemdaoImpl--->>findByinvoiceId--->>Start");

		InvoiceModel invoiceModel = invoiceService.getInvoiceModelById(invoiceId);

		List<InvoiceItemModel> invoiceItemList = invoiceItemRepository.findByinvoiceModel(invoiceModel);

		logger.info("InvoiceItemdaoImpl--->>findByinvoiceId--->>End");

		return invoiceItemList;
	}

	@Override
	public InvoiceItemModel createInvoiceItemModel(InvoiceItemModel invoiceItem) {
		logger.info("InvoiceItemdaoImpl--->>createInvoiceItemModel--->>Start");

		InvoiceItemModel invoiceItemModel = invoiceItemRepository.save(invoiceItem);

		logger.info("InvoiceItemdaoImpl--->>createInvoiceItemModel--->>End");

		return invoiceItemModel;
	}

	@Override
	public InvoiceItemModel deleteInvoiceItemModel(long invoiceItemId) throws InvoiceItemNotFoundException {
		logger.info("InvoiceItemdaoImpl--->>deleteInvoiceItemModel--->>Start");

		InvoiceItemModel invoiceItemModel = invoiceItemRepository.findById(invoiceItemId)
				.orElseThrow(() -> new InvoiceItemNotFoundException("Invoice Item not found :: " + invoiceItemId));

		invoiceItemRepository.delete(invoiceItemModel);

		logger.info("InvoiceItemdaoImpl--->>deleteInvoiceItemModel--->>End");

		return invoiceItemModel;
	}

	@Override
	public InvoiceItemModel updateInvoiceItemModel(CreateInvoiceItemBean invoiceItemBean)
			throws InvoiceItemNotFoundException, InvoiceNotFoundException, ItemNotFoundException {
		logger.info("InvoiceItemdaoImpl--->>updateInvoiceItemModel--->>Start");

		InvoiceItemModel invoiceItemModel = invoiceItemRepository.findById(invoiceItemBean.getInvoiceItemId())
				.orElseThrow(() -> new InvoiceItemNotFoundException(
						"Invoice Item not found :: " + invoiceItemBean.getInvoiceItemId()));

		if (invoiceItemModel.getInvoiceModel().getInvoiceId() != invoiceItemBean.getInvoiceId()) {

			InvoiceModel invoiceModel = invoiceService.getInvoiceModelById(invoiceItemBean.getInvoiceId());

			invoiceItemModel.setInvoiceModel(invoiceModel);
		}

		if (invoiceItemModel.getItemModel().getItemId() != invoiceItemBean.getItemId()) {

			ItemModel itemModel = itemService.getItemModelById(invoiceItemBean.getItemId());

			invoiceItemModel.setItemModel(itemModel);
		}

		if (invoiceItemModel.getQuantity() != invoiceItemBean.getQuantity()) {

			invoiceItemModel.setQuantity(invoiceItemBean.getQuantity());
		}

		invoiceItemModel
				.setItemAmountTotal(invoiceItemModel.getItemModel().getItemRate() * invoiceItemModel.getQuantity());

		logger.info("InvoiceItemdaoImpl--->>updateInvoiceItemModel--->>End");

		return invoiceItemModel;
	}
}