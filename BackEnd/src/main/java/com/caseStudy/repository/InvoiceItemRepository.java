package com.caseStudy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caseStudy.model.InvoiceItemModel;
import com.caseStudy.model.InvoiceModel;
import com.caseStudy.model.ItemModel;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItemModel, Long> {
	List<InvoiceItemModel> findByitemModel(ItemModel itemId);

	List<InvoiceItemModel> findByinvoiceModel(InvoiceModel invoiceId);
}