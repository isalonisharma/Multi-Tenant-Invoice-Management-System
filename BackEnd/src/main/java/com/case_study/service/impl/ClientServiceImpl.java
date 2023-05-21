package com.case_study.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.case_study.bean.CreateClientOrganizationBean;
import com.case_study.entity.Client;
import com.case_study.entity.ClientOrganization;
import com.case_study.exception.ClientNotFoundException;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.model.ClientModel;
import com.case_study.model.GroupModel;
import com.case_study.repository.ClientRepository;
import com.case_study.service.ClientOrganizationService;
import com.case_study.service.ClientService;
import com.case_study.utility.CommonConstants;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Service("clientService")
public class ClientServiceImpl implements ClientService {
	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientOrganizationService clientOrganizationService;

	@Override
	public Client createClient(ClientModel clientModel, long organizationId)
			throws ClientNotFoundException, OrganizationNotFoundException {
		Client createdClient = clientRepository.save(new Client(clientModel));
		clientOrganizationService
				.createClientOrganization(new CreateClientOrganizationBean(createdClient.getId(), organizationId));
		return createdClient;
	}

	@Override
	public GroupModel createClientsFromXML(File xmlfile) throws IOException {
		XmlMapper xmlMapper = new XmlMapper();
		GroupModel groupModel = xmlMapper.readValue(xmlfile, GroupModel.class);
		clientRepository.saveAll(groupModel.getClients());
		return groupModel;
	}

	@Override
	public Client deleteById(Long id) throws ClientNotFoundException {
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new ClientNotFoundException(CommonConstants.CLIENT_NOT_FOUND + id));
		client.setLocked(true);
		return clientRepository.save(client);
	}

	@Override
	public Client updateById(Long id, ClientModel clientModel) throws ClientNotFoundException {
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new ClientNotFoundException("Client not found :: " + id));
		if (client.isLocked()) {
			throw new ClientNotFoundException("Client not found :: " + id);
		}
		client.setFirstName(clientModel.getFirstName());
		client.setLastName(clientModel.getLastName());
		client.setMobileNumber(clientModel.getMobileNumber());
		client.setOrganization(clientModel.getOrganization());
		client.setEmailId(clientModel.getEmailId());
		return clientRepository.save(client);
	}

	@Override
	public List<Client> getActiveClients() {
		List<Client> clients = clientRepository.findAll();
		clients.removeIf(Client::isLocked);
		return clients;
	}

	@Override
	public Client findById(Long id) throws ClientNotFoundException {
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new ClientNotFoundException(CommonConstants.CLIENT_NOT_FOUND + id));
		if (client.isLocked()) {
			throw new ClientNotFoundException(CommonConstants.CLIENT_NOT_FOUND + id);
		}
		return client;
	}

	@Override
	public List<Client> findByOrganizationId(long organizationId) throws OrganizationNotFoundException {
		List<Client> clients = new ArrayList<>();
		List<ClientOrganization> clientOrganizations = clientOrganizationService.findByOrganizationId(organizationId);
		for (ClientOrganization clientOrganization : clientOrganizations) {
			clients.add(clientOrganization.getClient());
		}
		clients.removeIf(Client::isLocked);
		return clients;
	}
}