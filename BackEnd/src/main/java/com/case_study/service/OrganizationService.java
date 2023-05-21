package com.case_study.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.case_study.entity.Organization;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.model.OrganizationModel;
import com.itextpdf.text.DocumentException;;

public interface OrganizationService {

	Organization createOrganization(OrganizationModel organizationModel);

	Organization deleteById(Long id) throws OrganizationNotFoundException;

	Organization updateById(Long id, OrganizationModel organizationModel) throws OrganizationNotFoundException;

	List<Organization> getActiveOrganizations();

	Organization findById(Long id) throws OrganizationNotFoundException;

	Organization findByName(String name);

	File getLogoById(Long id) throws OrganizationNotFoundException, UserOrganizationNotFoundException,
			UserNotFoundException, IOException, DocumentException;	
}