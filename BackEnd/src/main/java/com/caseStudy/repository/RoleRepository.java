package com.caseStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caseStudy.model.RoleModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
	
	RoleModel findByRole(String role);

}
