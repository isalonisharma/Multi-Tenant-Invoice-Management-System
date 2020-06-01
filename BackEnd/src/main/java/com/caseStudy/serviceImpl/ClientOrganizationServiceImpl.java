package com.caseStudy.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caseStudy.bean.CreateClientOrganizationBean;
import com.caseStudy.dao.ClientOrganizationdao;
import com.caseStudy.exception.ClientNotFoundException;
import com.caseStudy.exception.ClientOrganizationNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.ClientOrganizationModel;
import com.caseStudy.service.ClientOrganizationService;

@Service("clientOrganizationService")
public class ClientOrganizationServiceImpl implements ClientOrganizationService {

	@Autowired
	ClientOrganizationdao clientOrganizationdao;

	@Override
	public ClientOrganizationModel createClientOrganizationModel(CreateClientOrganizationBean clientOrganizationBean)
			throws ClientNotFoundException, OrganizationNotFoundException {

		return clientOrganizationdao.createClientOrganizationModel(clientOrganizationBean);
	}

	@Override
	public List<ClientOrganizationModel> getClientOrganizationModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException {

		return clientOrganizationdao.getClientOrganizationModelByOrganizationId(organizationId);
	}

	@Override
	public ClientOrganizationModel getClientOrganizationModelByClientId(Long clientId)
			throws ClientOrganizationNotFoundException, ClientNotFoundException {

		return clientOrganizationdao.getClientOrganizationModelByClientId(clientId);
	}
}
