package com.case_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.case_study.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	Item findById(long id);
}