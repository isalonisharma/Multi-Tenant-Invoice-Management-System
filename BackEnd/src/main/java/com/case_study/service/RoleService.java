package com.case_study.service;

import java.sql.SQLException;
import java.util.List;

import com.case_study.entity.Role;
import com.case_study.exception.RoleNotFoundException;
import com.case_study.model.RoleModel;

public interface RoleService {

	Role createRole(RoleModel roleModel);

	Role deleteById(Long id) throws RoleNotFoundException;

	Role updateById(Long id, RoleModel roleModel) throws RoleNotFoundException;

	List<Role> findAll() throws SQLException;

	Role findById(Long id) throws RoleNotFoundException;

	Role findByDesignation(String designation);	
}