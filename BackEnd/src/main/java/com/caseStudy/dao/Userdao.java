package com.caseStudy.dao;

import java.util.List;

import com.caseStudy.bean.CreateUserBean;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.RoleNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.exception.UserRoleNotFoundException;
import com.caseStudy.model.UserModel;

public interface Userdao {

	// CRUD Operations

	// 1. Create Operation
	UserModel createUserModel(CreateUserBean createUserBean, long organizationId, long roleId)
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserRoleNotFoundException, RoleNotFoundException;

	// 2. Read Operation
	UserModel getUserModelById(Long userId) throws UserNotFoundException;

	// 3. Update Operation
	UserModel updateUserModel(Long userId, UserModel userDetails) throws UserNotFoundException;

	// 4. Delete Operation
	UserModel deleteUserModel(Long userId) throws UserNotFoundException;

	// Other Operations

	// Getting all entries from user table
	List<UserModel> getAllUserModels();

	// Getting the entries from user table of given organization id
	List<UserModel> getAllUserModelsByOrganizationId(long organizationId) throws OrganizationNotFoundException;

	// Getting the specific entry of user table with the help of given unique user
	// name
	UserModel findByUsername(String username) throws UserNotFoundException;

	// Getting the specific entry of user table with the help of given unique user
	// name & password
	UserModel findByUsernameAndPassword(String username, String password) throws UserNotFoundException;

	// Getting the name of organization of given user name
	String findOrganizationByUsername(String username) throws UserOrganizationNotFoundException, UserNotFoundException;
}