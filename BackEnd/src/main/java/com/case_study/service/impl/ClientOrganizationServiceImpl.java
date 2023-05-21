package com.case_study.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.case_study.bean.CreateClientOrganizationBean;
import com.case_study.entity.Client;
import com.case_study.entity.ClientOrganization;
import com.case_study.entity.Organization;
import com.case_study.exception.ClientNotFoundException;
import com.case_study.exception.ClientOrganizationNotFoundException;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.repository.ClientOrganizationRepository;
import com.case_study.service.ClientOrganizationService;
import com.case_study.service.ClientService;
import com.case_study.service.OrganizationService;

@Service("clientOrganizationService")
public class ClientOrganizationServiceImpl implements ClientOrganizationService {
	@Autowired
	private ClientOrganizationRepository clientOrganizationRepository;

	@Autowired
	private ClientService clientService;

	@Autowired
	private OrganizationService organizationService;

	@Override
	public ClientOrganization createClientOrganization(CreateClientOrganizationBean createClientOrganizationBean)
			throws ClientNotFoundException, OrganizationNotFoundException {
		ClientOrganization clientOrganization = new ClientOrganization();
		Client client = clientService.findById(createClientOrganizationBean.getClientId());
		Organization organization = organizationService.findById(createClientOrganizationBean.getOrganizationId());
		clientOrganization.setClient(client);
		clientOrganization.setOrganization(organization);
		clientOrganizationRepository.save(clientOrganization);
		return clientOrganization;
	}

	@Override
	public List<ClientOrganization> findByOrganizationId(long organizationId)
			throws OrganizationNotFoundException {
		Organization organization = organizationService.findById(organizationId);
		return clientOrganizationRepository.findByOrganization(organization);
	}

	@Override
	public ClientOrganization findByClientId(Long clientId)
			throws ClientOrganizationNotFoundException, ClientNotFoundException {
		Client client = clientService.findById(clientId);
		return clientOrganizationRepository.findByClient(client);
	}
}