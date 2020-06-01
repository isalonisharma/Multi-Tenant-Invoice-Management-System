package com.caseStudy.service;

import java.util.List;
import com.caseStudy.bean.CreateClientOrganizationBean;
import com.caseStudy.exception.ClientNotFoundException;
import com.caseStudy.exception.ClientOrganizationNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.ClientOrganizationModel;

/**
 * Class: ClientOrganizationService
 * 
 * @author saloni.sharma
 */
public interface ClientOrganizationService {

	/**
	 * Function Name: createClientOrganizationModel
	 * 
	 * @param clientOrganizationBean
	 * 
	 * @return ClientOrganizationModel
	 * 
	 * @throws ClientNotFoundException
	 * @throws OrganizationNotFoundException
	 */
	ClientOrganizationModel createClientOrganizationModel(CreateClientOrganizationBean clientOrganizationBean)
			throws ClientNotFoundException, OrganizationNotFoundException;

	/**
	 * Function Name: getClientOrganizationModelByOrganizationId
	 * 
	 * @param organizationId
	 * 
	 * @return List of ClientOrganizationModel
	 * 
	 * @throws OrganizationNotFoundException
	 */
	List<ClientOrganizationModel> getClientOrganizationModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException;

	/**
	 * Function Name: getClientOrganizationModelByClientId
	 * 
	 * @param clientId
	 * 
	 * @return ClientOrganizationModel
	 * 
	 * @throws ClientOrganizationNotFoundException
	 * @throws ClientNotFoundException
	 */
	ClientOrganizationModel getClientOrganizationModelByClientId(Long clientId)
			throws ClientOrganizationNotFoundException, ClientNotFoundException;
}
