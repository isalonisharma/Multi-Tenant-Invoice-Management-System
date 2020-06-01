package com.caseStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caseStudy.model.ItemModel;

@Repository
public interface ItemRepository extends JpaRepository<ItemModel, Long> {
	ItemModel findByitemId(long itemId);
}