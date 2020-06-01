package com.caseStudy.service;

import java.util.List;

import com.caseStudy.bean.CreateUserBean;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.RoleNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.exception.UserRoleNotFoundException;
import com.caseStudy.model.UserModel;

/**
 * Class: UserService
 * 
 * @author saloni.sharma
 */
public interface UserService {
	/**
	 * Function Name: getAllUserModels
	 * 
	 * @return list of user model
	 */
	List<UserModel> getAllUserModels();

	/**
	 * Function Name: getAllUserModelsByOrganizationId
	 * 
	 * @param organizationId
	 * 
	 * @return list of user model
	 * 
	 * @throws OrganizationNotFoundException
	 */
	List<UserModel> getAllUserModelsByOrganizationId(long organizationId) throws OrganizationNotFoundException;

	/**
	 * Function Name: getUserModelById
	 * 
	 * @param userId
	 * @return UserModel
	 * @throws UserNotFoundException
	 */
	UserModel getUserModelById(Long userId) throws UserNotFoundException;

	/**
	 * Function Name: createUserModel
	 * 
	 * @param createUserBean
	 * @param organizationId
	 * 
	 * @return UserModel
	 * 
	 * @throws UserOrganizationNotFoundException
	 * @throws UserNotFoundException
	 * @throws OrganizationNotFoundException
	 * @throws RoleNotFoundException
	 * @throws UserRoleNotFoundException
	 */
	UserModel createUserModel(CreateUserBean createUserBean, long organizationId, long roleId)
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserRoleNotFoundException, RoleNotFoundException;

	/**
	 * Function Name: deleteUserModel
	 * 
	 * @param userId
	 * 
	 * @return UserModel
	 * 
	 * @throws UserNotFoundException
	 */
	UserModel deleteUserModel(Long userId) throws UserNotFoundException;

	/**
	 * Function Name: updateUserModel
	 * 
	 * @param userId
	 * @param userDetails
	 * 
	 * @return UserModel
	 * 
	 * @throws UserNotFoundException
	 */
	UserModel updateUserModel(Long userId, UserModel userDetails) throws UserNotFoundException;

	/**
	 * Function Name: findOrganizationByUsername
	 * 
	 * @param username
	 * 
	 * @return String
	 * 
	 * @throws UserOrganizationNotFoundException
	 * @throws UserNotFoundException
	 */
	String findOrganizationByUsername(String username) throws UserOrganizationNotFoundException, UserNotFoundException;

	/**
	 * Function Name: findByUsername
	 * 
	 * @param username
	 * 
	 * @return UserModel
	 * @throws UserNotFoundException
	 */
	UserModel findByUsername(String username) throws UserNotFoundException;

	/**
	 * Function Name: findByUsernameAndPassword
	 * 
	 * @param username
	 * @param password
	 * 
	 * @return UserModel
	 * @throws UserNotFoundException
	 */
	UserModel findByUsernameAndPassword(String username, String password) throws UserNotFoundException;
}