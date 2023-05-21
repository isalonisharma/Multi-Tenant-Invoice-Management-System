package com.case_study.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.case_study.entity.Client;
import com.case_study.exception.ClientNotFoundException;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.model.ClientModel;
import com.case_study.model.GroupModel;

public interface ClientService {
	
	Client createClient(ClientModel clientModel, long organizationId)
			throws ClientNotFoundException, OrganizationNotFoundException;

	GroupModel createClientsFromXML(File xmlfile) throws IOException;

	Client deleteById(Long id) throws ClientNotFoundException;

	Client updateById(Long id, ClientModel clientModel) throws ClientNotFoundException;

	List<Client> getActiveClients();

	Client findById(Long id) throws ClientNotFoundException;

	List<Client> findByOrganizationId(long organizationId) throws OrganizationNotFoundException;
}