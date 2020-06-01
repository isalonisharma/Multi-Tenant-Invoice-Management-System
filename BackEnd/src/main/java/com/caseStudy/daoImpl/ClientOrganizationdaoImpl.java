package com.caseStudy.daoImpl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caseStudy.bean.CreateClientOrganizationBean;
import com.caseStudy.dao.ClientOrganizationdao;
import com.caseStudy.exception.ClientNotFoundException;
import com.caseStudy.exception.ClientOrganizationNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.ClientModel;
import com.caseStudy.model.ClientOrganizationModel;
import com.caseStudy.model.OrganizationModel;
import com.caseStudy.repository.ClientOrganizationRepository;
import com.caseStudy.service.ClientService;
import com.caseStudy.service.OrganizationService;

@Repository
public class ClientOrganizationdaoImpl implements ClientOrganizationdao {
	static final Logger logger = Logger.getLogger(ClientOrganizationdaoImpl.class);

	@Autowired
	private ClientOrganizationRepository clientOrganizationRepository;

	@Autowired
	private ClientService clientService;

	@Autowired
	private OrganizationService organizationService;

	@Override
	public ClientOrganizationModel createClientOrganizationModel(CreateClientOrganizationBean clientOrganizationBean)
			throws ClientNotFoundException, OrganizationNotFoundException {

		logger.info("ClientOrganizationdaoImpl--->>createClientOrganizationModel--->>Start");
		
		ClientOrganizationModel clientOrganizationModel = new ClientOrganizationModel();

		ClientModel clientModel = clientService.getClientModelById(clientOrganizationBean.getClientId());

		OrganizationModel organizationModel = organizationService
				.getOrganizationModelById(clientOrganizationBean.getOrganizationId());

		clientOrganizationModel.setClientModel(clientModel);
		clientOrganizationModel.setOrganizationModel(organizationModel);

		clientOrganizationRepository.save(clientOrganizationModel);

		logger.info("ClientOrganizationdaoImpl--->>createClientOrganizationModel--->>End");
		
		return clientOrganizationModel;
	}

	@Override
	public List<ClientOrganizationModel> getClientOrganizationModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException {
		logger.info("ClientOrganizationdaoImpl--->>getClientOrganizationModelByOrganizationId--->>Start");

		OrganizationModel organizationModel = organizationService.getOrganizationModelById(organizationId);

		logger.info("ClientOrganizationdaoImpl--->>getClientOrganizationModelByOrganizationId--->>End");
		
		return clientOrganizationRepository.findByorganizationModel(organizationModel);
	}

	@Override
	public ClientOrganizationModel getClientOrganizationModelByClientId(Long clientId)
			throws ClientOrganizationNotFoundException, ClientNotFoundException {

		logger.info("ClientOrganizationdaoImpl--->>getClientOrganizationModelByClientId--->>Start");

		ClientModel clientModel = clientService.getClientModelById(clientId);

		ClientOrganizationModel clientOrganizationModel = clientOrganizationRepository.findByclientModel(clientModel);

		logger.info("ClientOrganizationdaoImpl--->>getClientOrganizationModelByClientId--->>End");
		
		return clientOrganizationModel;
	}
}
