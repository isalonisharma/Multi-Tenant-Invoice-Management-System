package com.case_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.case_study.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
	Organization findById(long id);

	Organization findByName(String name);
}