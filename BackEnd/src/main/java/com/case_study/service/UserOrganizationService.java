package com.case_study.service;

import java.util.List;

import com.case_study.bean.CreateUserOrganizationBean;
import com.case_study.entity.User;
import com.case_study.entity.UserOrganization;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.model.UserOrganizationModel;

public interface UserOrganizationService {

	UserOrganization createUserOrganization(CreateUserOrganizationBean createUserOrganizationBean)
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException;

	UserOrganization deleteById(Long id) throws UserOrganizationNotFoundException;

	UserOrganization updateById(Long id, UserOrganizationModel userOrganizationModel)
			throws UserOrganizationNotFoundException, OrganizationNotFoundException;

	List<UserOrganization> findAll();

	UserOrganization findByUserId(Long userId) throws UserOrganizationNotFoundException, UserNotFoundException;

	UserOrganization findByUser(User user) throws UserOrganizationNotFoundException;

	List<UserOrganization> findByOrganizationId(long organizationId) throws OrganizationNotFoundException;
}