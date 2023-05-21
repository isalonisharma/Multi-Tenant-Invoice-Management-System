package com.case_study.service;

import java.util.List;

import com.case_study.bean.CreateClientOrganizationBean;
import com.case_study.entity.ClientOrganization;
import com.case_study.exception.ClientNotFoundException;
import com.case_study.exception.ClientOrganizationNotFoundException;
import com.case_study.exception.OrganizationNotFoundException;

public interface ClientOrganizationService {

	ClientOrganization createClientOrganization(CreateClientOrganizationBean createClientOrganizationBean)
			throws ClientNotFoundException, OrganizationNotFoundException;

	List<ClientOrganization> findByOrganizationId(long organizationId) throws OrganizationNotFoundException;

	ClientOrganization findByClientId(Long clientId)
			throws ClientOrganizationNotFoundException, ClientNotFoundException;
}