package com.case_study.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.case_study.entity.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
	List<Audit> findByEntryDateBetween(LocalDateTime startingDate, LocalDateTime endingDate);
}