package com.caseStudy.dao;

import java.util.List;

import com.caseStudy.bean.CreateClientOrganizationBean;
import com.caseStudy.exception.ClientNotFoundException;
import com.caseStudy.exception.ClientOrganizationNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.ClientOrganizationModel;

public interface ClientOrganizationdao {

	// Create Operation
	ClientOrganizationModel createClientOrganizationModel(CreateClientOrganizationBean clientOrganizationBean)
			throws ClientNotFoundException, OrganizationNotFoundException;

	// Getting the entries from client organization table of given organization id
	List<ClientOrganizationModel> getClientOrganizationModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException;

	// Getting the entries from client organization table of given client id
	ClientOrganizationModel getClientOrganizationModelByClientId(Long clientId)
			throws ClientOrganizationNotFoundException, ClientNotFoundException;
}
