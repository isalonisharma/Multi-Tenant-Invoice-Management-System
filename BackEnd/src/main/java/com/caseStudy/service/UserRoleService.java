package com.caseStudy.service;

import java.util.List;

import com.caseStudy.bean.CreateUserRoleBean;
import com.caseStudy.exception.RoleNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserRoleNotFoundException;
import com.caseStudy.model.UserModel;
import com.caseStudy.model.UserRoleModel;

/**
 * Class: UserRoleService
 * 
 * @author saloni.sharma
 */
public interface UserRoleService {
	/**
	 * Function Name: getAllUserRoleModel
	 * 
	 * @return list of user role model
	 */
	List<UserRoleModel> getAllUserRoleModel();

	/**
	 * Function Name: createUserRoleModel
	 * 
	 * @param userRole
	 * 
	 * @return UserRoleModel
	 * 
	 * @throws UserRoleNotFoundException
	 * @throws UserNotFoundException
	 * @throws RoleNotFoundException
	 */
	UserRoleModel createUserRoleModel(CreateUserRoleBean userRole)
			throws UserRoleNotFoundException, UserNotFoundException, RoleNotFoundException;

	/**
	 * Function Name: deleteUserRoleModel
	 * 
	 * @param userRoleId
	 * 
	 * @return UserRoleModel
	 * 
	 * @throws UserRoleNotFoundException
	 */
	UserRoleModel deleteUserRoleModel(Long userRoleId) throws UserRoleNotFoundException;

	/**
	 * Function Name: updateUserRoleModel
	 * 
	 * @param userRoleId
	 * @param userRoleDetails
	 * 
	 * @return UserRoleModel
	 * 
	 * @throws UserRoleNotFoundException
	 */
	UserRoleModel updateUserRoleModel(Long userRoleId, UserRoleModel userRoleDetails) throws UserRoleNotFoundException;

	/**
	 * Function Name: getUserRoleModelByUserId
	 * 
	 * @param userId
	 * 
	 * @return list of user role model
	 * 
	 * @throws UserRoleNotFoundException
	 * @throws UserNotFoundException
	 */
	List<UserRoleModel> getUserRoleModelByUserId(Long userId) throws UserRoleNotFoundException, UserNotFoundException;

	/**
	 * Function Name: findByuserModel
	 * 
	 * @param userModel
	 * 
	 * @return list of user role model
	 */
	List<UserRoleModel> findByuserModel(UserModel userModel);
}
