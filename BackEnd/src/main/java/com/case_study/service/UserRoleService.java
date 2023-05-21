package com.case_study.service;

import java.util.List;

import com.case_study.bean.CreateUserRoleBean;
import com.case_study.entity.User;
import com.case_study.entity.UserRole;
import com.case_study.exception.RoleNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserRoleNotFoundException;
import com.case_study.model.UserRoleModel;

public interface UserRoleService {

	UserRole createUserRole(CreateUserRoleBean createUserRoleBean)
			throws UserRoleNotFoundException, UserNotFoundException, RoleNotFoundException;

	UserRole deleteById(Long id) throws UserRoleNotFoundException;

	UserRole updateById(Long id, UserRoleModel userRoleModel)
			throws UserRoleNotFoundException, UserNotFoundException, RoleNotFoundException;

	List<UserRole> findAll();

	List<UserRole> findByUserId(Long id) throws UserRoleNotFoundException, UserNotFoundException;

	List<UserRole> findByUser(User user);
}