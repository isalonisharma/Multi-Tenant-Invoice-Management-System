package com.caseStudy.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.OrganizationModel;
import com.itextpdf.text.DocumentException;;

/**
 * Class: OrganizationService
 * 
 * @author saloni.sharma
 */
public interface OrganizationService {
	/**
	 * Function Name: getAllOrganizationModels
	 * 
	 * @return List of OrganizationModel
	 */
	List<OrganizationModel> getAllOrganizationModels();

	/**
	 * Function Name: createOrganizationModel
	 * 
	 * @param organizationModel
	 * 
	 * @return OrganizationModel
	 */
	OrganizationModel createOrganizationModel(OrganizationModel organizationModel);

	/**
	 * Function Name: getOrganizationModelById
	 * 
	 * @param organizationId
	 * 
	 * @return OrganizationModel
	 * 
	 * @throws OrganizationNotFoundException
	 */
	OrganizationModel getOrganizationModelById(Long organizationId) throws OrganizationNotFoundException;

	/**
	 * Function Name: getOrganizationModelByName
	 * 
	 * @param organizationName
	 * 
	 * @return OrganizationModel
	 */
	OrganizationModel getOrganizationModelByName(String organizationName);

	/**
	 * Function Name: deleteOrganizationModel
	 * 
	 * @param organizationId
	 * 
	 * @return OrganizationModel
	 * 
	 * @throws OrganizationNotFoundException
	 */
	OrganizationModel deleteOrganizationModel(Long organizationId) throws OrganizationNotFoundException;

	/**
	 * Function Name: updateOrganizationModel
	 * 
	 * @param organizationId
	 * @param organizationDetails
	 * 
	 * @return OrganizationModel
	 * 
	 * @throws OrganizationNotFoundException
	 */
	OrganizationModel updateOrganizationModel(Long organizationId, OrganizationModel organizationDetails)
			throws OrganizationNotFoundException;

	/**
	 * Function Name: getOrganizationLogo
	 * 
	 * @param organizationId
	 * 
	 * @return organizationLogo
	 * 
	 * @throws OrganizationNotFoundException
	 * @throws UserOrganizationNotFoundException
	 * @throws UserNotFoundException
	 * @throws IOException
	 * @throws DocumentException
	 */
	File getOrganizationLogo(Long organizationId) throws OrganizationNotFoundException,
			UserOrganizationNotFoundException, UserNotFoundException, IOException, DocumentException;
}
