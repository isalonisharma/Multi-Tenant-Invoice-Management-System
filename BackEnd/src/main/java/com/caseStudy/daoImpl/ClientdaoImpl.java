package com.caseStudy.daoImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caseStudy.bean.CreateClientOrganizationBean;
import com.caseStudy.dao.Clientdao;
import com.caseStudy.exception.ClientNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.ClientModel;
import com.caseStudy.model.ClientOrganizationModel;
import com.caseStudy.model.GroupModel;
import com.caseStudy.repository.ClientRepository;
import com.caseStudy.service.ClientOrganizationService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Repository
public class ClientdaoImpl implements Clientdao {
	static final Logger logger = Logger.getLogger(ClientdaoImpl.class);

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientOrganizationService clientOrganizationService;

	@Override
	public List<ClientModel> getAllClientModel() {
		logger.info("ClientdaoImpl--->>getAllClientModel--->>Start");

		List<ClientModel> clientModelList = clientRepository.findAll();
		
		clientModelList.removeIf((ClientModel clientModel) -> clientModel.isClientIsLocked());

		logger.info("ClientdaoImpl--->>getAllClientModel--->>End");

		return clientModelList;
	}

	@Override
	public ClientModel getClientModelById(Long clientId) throws ClientNotFoundException {
		logger.info("ClientdaoImpl--->>getClientModelById--->>Start");

		ClientModel clientModel = clientRepository.findById(clientId)
				.orElseThrow(() -> new ClientNotFoundException("Client not found :: " + clientId));

		if (clientModel.isClientIsLocked()) {
			throw new ClientNotFoundException("Client not found :: " + clientId);
		}

		logger.info("ClientdaoImpl--->>getClientModelById--->>End");

		return clientModel;
	}

	@Override
	public ClientModel createClientModel(ClientModel client, long organizationId)
			throws ClientNotFoundException, OrganizationNotFoundException {
		logger.info("ClientdaoImpl--->>createClientModel--->>Start");

		ClientModel clientModel = clientRepository.save(client);

		clientOrganizationService.createClientOrganizationModel(
				new CreateClientOrganizationBean(clientModel.getClientId(), organizationId));

		logger.info("ClientdaoImpl--->>createClientModel--->>End");
		return clientModel;
	}

	@Override
	public ClientModel deleteClientModel(Long clientId) throws ClientNotFoundException {
		logger.info("ClientdaoImpl--->>deleteClientModel--->>Start");

		ClientModel clientModel = clientRepository.findById(clientId)
				.orElseThrow(() -> new ClientNotFoundException("Client not found :: " + clientId));

		clientModel.setClientIsLocked(true);

		final ClientModel deletedClientModel = clientRepository.save(clientModel);

		logger.info("ClientdaoImpl--->>deleteClientModel--->>End");

		return deletedClientModel;
	}

	@Override
	public ClientModel updateClientModel(Long clientId, ClientModel clientDetails) throws ClientNotFoundException {
		logger.info("ClientdaoImpl--->>updateClientModel--->>Start");

		ClientModel clientModel = clientRepository.findById(clientId)
				.orElseThrow(() -> new ClientNotFoundException("Client not found :: " + clientId));

		if (clientModel.isClientIsLocked()) {
			throw new ClientNotFoundException("Client not found :: " + clientId);
		}

		clientModel.setFirstName(clientDetails.getFirstName());
		clientModel.setLastName(clientDetails.getLastName());
		clientModel.setMobileNumber(clientDetails.getMobileNumber());
		clientModel.setOrganization(clientDetails.getOrganization());
		clientModel.setEmailId(clientDetails.getEmailId());

		final ClientModel updatedClientModel = clientRepository.save(clientModel);

		logger.info("ClientdaoImpl--->>updateClientModel--->>End");

		return updatedClientModel;
	}

	@Override
	public GroupModel createClientModelsFromXML(File xmlfile) throws IOException {
		logger.info("ClientdaoImpl--->>createClientModelsFromXML--->>Start");

		XmlMapper xmlMapper = new XmlMapper();

		GroupModel groupModel = xmlMapper.readValue(xmlfile, GroupModel.class);

		List<ClientModel> clientModelList = groupModel.getClientModel();

		for (ClientModel clientModel : clientModelList) {
			clientRepository.save(clientModel);
		}

		logger.info("ClientdaoImpl--->>createClientModelsFromXML--->>End");

		return groupModel;
	}

	@Override
	public List<ClientModel> getAllClientModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException {
		logger.info("ClientdaoImpl--->>getAllClientModelByOrganizationId--->>Start");

		List<ClientModel> clientModelList = new ArrayList<ClientModel>();

		List<ClientOrganizationModel> clientOrganizationModelList = clientOrganizationService
				.getClientOrganizationModelByOrganizationId(organizationId);

		for (ClientOrganizationModel clientOrganizationModel : clientOrganizationModelList) {
			clientModelList.add(clientOrganizationModel.getClientModel());
		}
		
		clientModelList.removeIf((ClientModel clientModel) -> clientModel.isClientIsLocked());

		logger.info("ClientdaoImpl--->>getAllClientModelByOrganizationId--->>End");
		
		return clientModelList;
	}
}