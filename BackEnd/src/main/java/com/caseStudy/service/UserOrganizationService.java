package com.caseStudy.service;

import java.util.List;

import com.caseStudy.bean.CreateUserOrganizationBean;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.UserOrganizationModel;

/**
 * Class: UserOrganizationService
 * 
 * @author saloni.sharma
 */
public interface UserOrganizationService {
	/**
	 * Function Name: getAllUserOrganizationModel
	 * 
	 * @return list of UserOrganizationModel
	 */
	List<UserOrganizationModel> getAllUserOrganizationModel();

	/**
	 * Function Name: getUserOrganizationModelByUserId
	 * 
	 * @param userId
	 * 
	 * @return UserOrganizationModel
	 * 
	 * @throws UserOrganizationNotFoundException
	 * @throws UserNotFoundException
	 */
	UserOrganizationModel getUserOrganizationModelByUserId(Long userId)
			throws UserOrganizationNotFoundException, UserNotFoundException;

	/**
	 * Function Name: createUserOrganizationModel
	 * 
	 * @param userOrganizationBean
	 * 
	 * @return UserOrganizationModel
	 *
	 * @throws UserOrganizationNotFoundException
	 * @throws UserNotFoundException
	 * @throws OrganizationNotFoundException
	 */
	UserOrganizationModel createUserOrganizationModel(CreateUserOrganizationBean userOrganizationBean)
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException;

	/**
	 * Function Name: deleteUserOrganizationModel
	 * 
	 * @param userOrganizationId
	 * 
	 * @return UserOrganizationModel
	 * 
	 * @throws UserOrganizationNotFoundException
	 */
	UserOrganizationModel deleteUserOrganizationModel(Long userOrganizationId) throws UserOrganizationNotFoundException;

	/**
	 * Function Name: updateUserOrganizationModel
	 * 
	 * @param userOrganizationId
	 * @param userOrganizationDetails
	 * 
	 * @return UserOrganizationModel
	 * 
	 * @throws UserOrganizationNotFoundException
	 */
	UserOrganizationModel updateUserOrganizationModel(Long userOrganizationId,
			UserOrganizationModel userOrganizationDetails) throws UserOrganizationNotFoundException;

	/**
	 * Function Name: getUserOrganizationModelByOrganizationId
	 * 
	 * @param organizationId
	 * 
	 * @return list of UserOrganizationModel
	 * 
	 * @throws OrganizationNotFoundException
	 */
	List<UserOrganizationModel> getUserOrganizationModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException;
}
