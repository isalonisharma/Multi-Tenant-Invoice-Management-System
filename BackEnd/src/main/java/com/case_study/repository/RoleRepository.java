package com.case_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.case_study.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {	
	Role findByDesignation(String designation);
}