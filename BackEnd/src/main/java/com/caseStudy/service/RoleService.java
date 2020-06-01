package com.caseStudy.service;

import java.sql.SQLException;
import java.util.List;

import com.caseStudy.exception.RoleNotFoundException;
import com.caseStudy.model.RoleModel;

/**
 * Class: RoleService
 * 
 * @author saloni.sharma
 */
public interface RoleService {
	/**
	 * Function Name: getAllRoleModels
	 * 
	 * @return list of role models
	 * 
	 * @throws SQLException
	 */
	List<RoleModel> getAllRoleModels() throws SQLException;

	/**
	 * Function Name: getRoleModelById
	 * 
	 * @param roleId
	 * 
	 * @return RoleModel
	 * 
	 * @throws RoleNotFoundException
	 */
	RoleModel getRoleModelById(Long roleId) throws RoleNotFoundException;

	/**
	 * Function Name: createRoleModel
	 * 
	 * @param role
	 * 
	 * @return RoleModel
	 */
	RoleModel createRoleModel(RoleModel roleModel);

	/**
	 * Function Name: deleteRoleModel
	 * 
	 * @param roleId
	 * 
	 * @return RoleModel
	 * 
	 * @throws RoleNotFoundException
	 */
	RoleModel deleteRoleModel(Long roleId) throws RoleNotFoundException;

	/**
	 * Function Name: updateRoleModel
	 * 
	 * @param roleId
	 * @param roleDetails
	 * 
	 * @return RoleModel
	 * 
	 * @throws RoleNotFoundException
	 */
	RoleModel updateRoleModel(Long roleId, RoleModel roleDetails) throws RoleNotFoundException;
	
	/**
	 * Function Name: findByRole
	 * 
	 * @param role
	 * 
	 * @return RoleModel
	 */
	RoleModel findByRole(String role);
}
