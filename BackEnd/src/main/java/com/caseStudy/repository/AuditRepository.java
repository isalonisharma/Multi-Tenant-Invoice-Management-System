package com.caseStudy.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caseStudy.model.AuditModel;

@Repository
public interface AuditRepository extends JpaRepository<AuditModel, Long> {

	List<AuditModel> findByEntryDateBetween(LocalDateTime startingDate, LocalDateTime EndingDate);

}
