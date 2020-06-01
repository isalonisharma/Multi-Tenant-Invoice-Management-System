package com.caseStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caseStudy.model.OrganizationModel;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationModel, Long> {
	OrganizationModel findByorganizationId(long organizationId);

	OrganizationModel findByorganizationName(String organizationName);
}
