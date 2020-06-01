package com.caseStudy.dao;

import java.util.List;

import com.caseStudy.bean.CreateUserOrganizationBean;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.UserOrganizationModel;

public interface UserOrganizationdao {

	// Create Operation
	UserOrganizationModel createUserOrganizationModel(CreateUserOrganizationBean userOrganizationBean)
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException;
	
	// Update Operation
	UserOrganizationModel updateUserOrganizationModel(Long userOrganizationId,
			UserOrganizationModel userOrganizationDetails) throws UserOrganizationNotFoundException;
	
	// Delete Operation
	UserOrganizationModel deleteUserOrganizationModel(Long userOrganizationId) throws UserOrganizationNotFoundException;
	
	// Getting all entries from user organization table
	List<UserOrganizationModel> getAllUserOrganizationModel();

	// Getting the entries from user organization table of given user id
	UserOrganizationModel getUserOrganizationModelByUserId(Long userId)
			throws UserOrganizationNotFoundException, UserNotFoundException;

	// Getting the entries from user organization table of given organization id
	List<UserOrganizationModel> getUserOrganizationModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException;
}