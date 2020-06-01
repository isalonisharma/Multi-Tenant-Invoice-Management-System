package com.caseStudy.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.caseStudy.exception.ClientNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.ClientModel;
import com.caseStudy.model.GroupModel;

/**
 * Class: ClientService
 * 
 * @author saloni.sharma
 */
public interface ClientService {

	/**
	 * Function Name: getAllClientModel
	 * 
	 * @return List of ClientModel
	 */
	List<ClientModel> getAllClientModel();

	/**
	 * Function Name: getAllClientModelByOrganizationId
	 * 
	 * @param organizationId
	 * 
	 * @return List of ClientModel
	 * 
	 * @throws OrganizationNotFoundException
	 */
	List<ClientModel> getAllClientModelByOrganizationId(long organizationId) throws OrganizationNotFoundException;

	/**
	 * Function Name: getClientModelById
	 * 
	 * @param clientId
	 * 
	 * @return ClientModel
	 * 
	 * @throws ClientNotFoundException
	 */
	ClientModel getClientModelById(Long clientId) throws ClientNotFoundException;

	/**
	 * Function Name: createClientModel
	 * 
	 * @param client
	 * @param organizationId
	 * 
	 * @return ClientModel
	 * 
	 * @throws ClientNotFoundException
	 * @throws OrganizationNotFoundException
	 */
	ClientModel createClientModel(ClientModel client, long organizationId)
			throws ClientNotFoundException, OrganizationNotFoundException;

	/**
	 * Function Name: createClientModelsFromXML
	 * 
	 * @param xmlfile
	 * 
	 * @return GroupModel
	 * 
	 * @throws IOException
	 */
	GroupModel createClientModelsFromXML(File xmlfile) throws IOException;

	/**
	 * Function Name: deleteClientModel
	 * 
	 * @param clientId
	 * 
	 * @return ClientModel
	 * 
	 * @throws ClientNotFoundException
	 */
	ClientModel deleteClientModel(Long clientId) throws ClientNotFoundException;

	/**
	 * Function Name: updateClientModel
	 * 
	 * @param clientId
	 * @param clientDetails
	 * 
	 * @return ClientModel
	 * 
	 * @throws ClientNotFoundException
	 */
	ClientModel updateClientModel(Long clientId, ClientModel clientDetails) throws ClientNotFoundException;
}
