package com.case_study.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.case_study.entity.Item;
import com.case_study.entity.ItemOrganization;
import com.case_study.entity.Organization;

@Repository
public interface ItemOrganizationRepository extends JpaRepository<ItemOrganization, Long> {
	List<ItemOrganization> findByOrganization(Organization organization);

	ItemOrganization findByItem(Item item);
}