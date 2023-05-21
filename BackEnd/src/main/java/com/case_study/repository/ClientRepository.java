package com.case_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.case_study.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	Client findById(long id);
}