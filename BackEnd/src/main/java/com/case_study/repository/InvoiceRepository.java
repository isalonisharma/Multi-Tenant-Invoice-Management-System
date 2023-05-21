package com.case_study.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.case_study.entity.Client;
import com.case_study.entity.Invoice;
import com.case_study.entity.User;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
	List<Invoice> findByUser(User user);

	List<Invoice> findByClient(Client client);

	List<Invoice> findTop3ByUserOrderByDatePlacedDesc(User user);
}