package com.caseStudy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caseStudy.model.ClientModel;
import com.caseStudy.model.ClientOrganizationModel;
import com.caseStudy.model.OrganizationModel;

@Repository
public interface ClientOrganizationRepository extends JpaRepository<ClientOrganizationModel, Long> {

	List<ClientOrganizationModel> findByorganizationModel(OrganizationModel organizationModel);

	ClientOrganizationModel findByclientModel(ClientModel clientModel);
}
