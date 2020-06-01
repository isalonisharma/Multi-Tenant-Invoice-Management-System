package com.caseStudy.dao;

import java.sql.SQLException;
import java.util.List;

import com.caseStudy.exception.RoleNotFoundException;
import com.caseStudy.model.RoleModel;

public interface Roledao {
	//CRUD Operations
	
	// 1. Create Operation
	RoleModel createRoleModel(RoleModel role);
	
	// 2. Read Operation
	RoleModel getRoleModelById(Long roleId) throws RoleNotFoundException;
	
	// 3. Update Operation
	RoleModel updateRoleModel(Long roleId, RoleModel roleDetails) throws RoleNotFoundException;
	
	// 4. Delete Operation
	RoleModel deleteRoleModel(Long roleId) throws RoleNotFoundException;
	
	// Other Operations
	
	// Getting all entries from role table
	List<RoleModel> getAllRoleModels() throws SQLException;
	
	// Getting the specific entry of role table with the help of given unique role
	RoleModel findByRole(String role);
}
