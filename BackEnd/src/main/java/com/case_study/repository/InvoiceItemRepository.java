package com.case_study.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.case_study.entity.Invoice;
import com.case_study.entity.InvoiceItem;
import com.case_study.entity.Item;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
	List<InvoiceItem> findByItem(Item item);

	List<InvoiceItem> findByInvoice(Invoice invoice);
}