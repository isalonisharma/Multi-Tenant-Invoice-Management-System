package com.case_study.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.case_study.bean.CreateUserOrganizationBean;
import com.case_study.entity.Organization;
import com.case_study.entity.User;
import com.case_study.entity.UserOrganization;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.model.UserOrganizationModel;
import com.case_study.repository.UserOrganizationRepository;
import com.case_study.service.OrganizationService;
import com.case_study.service.UserOrganizationService;
import com.case_study.service.UserService;
import com.case_study.utility.CommonConstants;

@Service("userOrganizationService")
public class UserOrganizationServiceImpl implements UserOrganizationService {
	@Autowired
	private UserService userService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private UserOrganizationRepository userOrganizationRepository;

	@Override
	public UserOrganization createUserOrganization(CreateUserOrganizationBean createUserOrganizationBean)
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException {
		UserOrganization userOrganization = new UserOrganization();
		User user = userService.findById(createUserOrganizationBean.getUserId());
		Organization organization = organizationService.findById(createUserOrganizationBean.getOrganizationId());
		userOrganization.setUser(user);
		userOrganization.setOrganization(organization);
		userOrganizationRepository.save(userOrganization);
		return userOrganization;
	}

	@Override
	public UserOrganization deleteById(Long id) throws UserOrganizationNotFoundException {
		UserOrganization userOrganization = userOrganizationRepository.findById(id).orElseThrow(
				() -> new UserOrganizationNotFoundException(CommonConstants.USER_ORGANIZATION_NOT_FOUND + id));
		userOrganizationRepository.delete(userOrganization);
		return userOrganization;
	}

	@Override
	public UserOrganization updateById(Long id, UserOrganizationModel userOrganizationModel)
			throws UserOrganizationNotFoundException, OrganizationNotFoundException {
		UserOrganization userOrganization = userOrganizationRepository.findById(id).orElseThrow(
				() -> new UserOrganizationNotFoundException(CommonConstants.USER_ORGANIZATION_NOT_FOUND + id));
		userOrganization.setUser(userOrganization.getUser());
		Organization organization = organizationService.findById(userOrganizationModel.getOrganization().getId());
		userOrganization.setOrganization(organization);
		return userOrganizationRepository.save(userOrganization);
	}

	@Override
	public List<UserOrganization> findAll() {
		return userOrganizationRepository.findAll();
	}
	
	@Override
	public UserOrganization findByUserId(Long userId)
			throws UserOrganizationNotFoundException, UserNotFoundException {
		User user = userService.findById(userId);
		return findByUser(user);
	}

	@Override
	public UserOrganization findByUser(User user) throws UserOrganizationNotFoundException {
		return userOrganizationRepository.findByUser(user);
	}

	@Override
	public List<UserOrganization> findByOrganizationId(long organizationId) throws OrganizationNotFoundException {
		Organization organization = organizationService.findById(organizationId);
		return userOrganizationRepository.findByOrganization(organization);
	}
}