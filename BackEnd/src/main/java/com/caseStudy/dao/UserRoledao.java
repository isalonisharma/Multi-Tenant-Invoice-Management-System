package com.caseStudy.dao;

import java.util.List;

import com.caseStudy.bean.CreateUserRoleBean;
import com.caseStudy.exception.RoleNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserRoleNotFoundException;
import com.caseStudy.model.UserModel;
import com.caseStudy.model.UserRoleModel;

public interface UserRoledao {
	
	// Create Operation
	UserRoleModel createUserRoleModel(CreateUserRoleBean userRole)
			throws UserRoleNotFoundException, UserNotFoundException, RoleNotFoundException;
	
	// Update Operation
	UserRoleModel updateUserRoleModel(Long userRoleId, UserRoleModel userRoleDetails) throws UserRoleNotFoundException;
	
	// Delete Operation
	UserRoleModel deleteUserRoleModel(Long userRoleId) throws UserRoleNotFoundException;
	
	// Getting all entries from user role table
	List<UserRoleModel> getAllUserRoleModel();

	// Getting the entries from user role table of given user id
	List<UserRoleModel> getUserRoleModelByUserId(Long userId) throws UserRoleNotFoundException, UserNotFoundException;

	// Getting the entries from user role table of given user model
	List<UserRoleModel> findByuserModel(UserModel userModel);
}
