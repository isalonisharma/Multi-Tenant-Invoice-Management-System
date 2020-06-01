package com.caseStudy.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caseStudy.dao.Clientdao;
import com.caseStudy.exception.ClientNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.ClientModel;
import com.caseStudy.model.GroupModel;
import com.caseStudy.service.ClientService;

@Service("clientService")
public class ClientServiceImpl implements ClientService {
	@Autowired
	private Clientdao clientDao;

	@Override
	public List<ClientModel> getAllClientModel() {
		return clientDao.getAllClientModel();
	}

	@Override
	public ClientModel getClientModelById(Long clientId) throws ClientNotFoundException {
		return clientDao.getClientModelById(clientId);
	}

	@Override
	public ClientModel createClientModel(ClientModel client, long organizationId)
			throws ClientNotFoundException, OrganizationNotFoundException {
		return clientDao.createClientModel(client, organizationId);
	}

	@Override
	public ClientModel deleteClientModel(Long clientId) throws ClientNotFoundException {
		return clientDao.deleteClientModel(clientId);
	}

	@Override
	public ClientModel updateClientModel(Long clientId, ClientModel clientDetails) throws ClientNotFoundException {
		return clientDao.updateClientModel(clientId, clientDetails);
	}

	@Override
	public GroupModel createClientModelsFromXML(File xmlfile) throws IOException {
		return clientDao.createClientModelsFromXML(xmlfile);
	}

	@Override
	public List<ClientModel> getAllClientModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException {
		return clientDao.getAllClientModelByOrganizationId(organizationId);
	}
}
