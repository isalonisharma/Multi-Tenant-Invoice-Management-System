package com.caseStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caseStudy.model.ClientModel;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Long> {
	ClientModel findByclientId(long clientId);
}
