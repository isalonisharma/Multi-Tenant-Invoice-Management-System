package com.case_study.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.case_study.entity.Client;
import com.case_study.entity.ClientOrganization;
import com.case_study.entity.Organization;

@Repository
public interface ClientOrganizationRepository extends JpaRepository<ClientOrganization, Long> {
	List<ClientOrganization> findByOrganization(Organization organization);

	ClientOrganization findByClient(Client client);
}