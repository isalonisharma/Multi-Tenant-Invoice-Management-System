package com.case_study.service;

import java.util.List;

import com.case_study.bean.CreateUserBean;
import com.case_study.entity.User;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.exception.RoleNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.exception.UserRoleNotFoundException;
import com.case_study.model.UserRequestModel;

public interface UserService {
	User createUser(CreateUserBean createUserBean, long organizationId, long roleId)
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserRoleNotFoundException, RoleNotFoundException;

	User deleteById(Long id) throws UserNotFoundException;

	User updateById(Long id, UserRequestModel userRequestModel) throws UserNotFoundException;

	List<User> getActiveUsers();

	User findById(Long id) throws UserNotFoundException;

	User findByUsername(String username) throws UserNotFoundException;

	User findByUsernameAndPassword(String username, String password) throws UserNotFoundException;

	List<User> findByOrganizationId(long organizationId) throws OrganizationNotFoundException;

	String findOrganizationByUsername(String username) throws UserOrganizationNotFoundException, UserNotFoundException;	
}