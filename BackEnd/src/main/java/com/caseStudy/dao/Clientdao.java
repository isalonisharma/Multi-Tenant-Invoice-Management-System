package com.caseStudy.dao;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.caseStudy.exception.ClientNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.model.ClientModel;
import com.caseStudy.model.GroupModel;

public interface Clientdao {
	
	//CRUD Operations
	
	// 1. Create Operation
	ClientModel createClientModel(ClientModel clientModel, long organizationId)
			throws ClientNotFoundException, OrganizationNotFoundException;
	
	// 2. Read Operation
	ClientModel getClientModelById(Long clientId) throws ClientNotFoundException;
	
	// 3. Update Operation
	ClientModel updateClientModel(Long clientId, ClientModel clientDetails) throws ClientNotFoundException;
	
	// 4. Delete Operation
	ClientModel deleteClientModel(Long clientId) throws ClientNotFoundException;
	
	// Other Operations
	
	// Create Client Model From XML
	GroupModel createClientModelsFromXML(File xmlfile) throws IOException;
	
	// Getting all entries from client table
	List<ClientModel> getAllClientModel();

	// Getting the entries from client table of given organization id
	List<ClientModel> getAllClientModelByOrganizationId(long organizationId) throws OrganizationNotFoundException;
}
