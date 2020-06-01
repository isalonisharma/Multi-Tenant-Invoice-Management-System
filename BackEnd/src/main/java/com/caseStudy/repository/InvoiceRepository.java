package com.caseStudy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caseStudy.model.ClientModel;
import com.caseStudy.model.InvoiceModel;
import com.caseStudy.model.UserModel;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceModel, Long> {

	List<InvoiceModel> findByuserModel(UserModel userId);

	List<InvoiceModel> findByclientModel(ClientModel clientId);

	List<InvoiceModel> findTop3ByuserModelOrderByDatePlacedDesc(UserModel userId);
}
