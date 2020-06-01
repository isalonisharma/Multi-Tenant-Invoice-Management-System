package com.caseStudy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caseStudy.model.ItemModel;
import com.caseStudy.model.ItemOrganizationModel;
import com.caseStudy.model.OrganizationModel;

@Repository
public interface ItemOrganizationRepository extends JpaRepository<ItemOrganizationModel, Long> {

	List<ItemOrganizationModel> findByorganizationModel(OrganizationModel organizationModel);

	ItemOrganizationModel findByitemModel(ItemModel itemModel);

}
