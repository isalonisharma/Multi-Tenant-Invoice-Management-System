package com.caseStudy.dao;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.OrganizationModel;
import com.itextpdf.text.DocumentException;

public interface Organizationdao {
	// CRUD Operations

	// 1. Create Operation
	OrganizationModel createOrganizationModel(OrganizationModel organizationModel);

	// 2. Read Operation
	OrganizationModel getOrganizationModelById(Long organizationId) throws OrganizationNotFoundException;

	// 3. Update Operation
	OrganizationModel updateOrganizationModel(Long organizationId, OrganizationModel organizationDetails)
			throws OrganizationNotFoundException;

	// 4. Delete Operation
	OrganizationModel deleteOrganizationModel(Long organizationId) throws OrganizationNotFoundException;

	// Other Operations

	// Getting all entries from organization table
	List<OrganizationModel> getAllOrganizationModels();

	// Getting the specific entry of organization table with the help of given
	// organization name
	OrganizationModel getOrganizationModelByName(String organizationName);

	// Getting the organization Logo ByteArrayInputStream with the help of
	// organization id
	File getOrganizationLogo(Long organizationId) throws OrganizationNotFoundException,
			UserOrganizationNotFoundException, UserNotFoundException, IOException, DocumentException;

}
