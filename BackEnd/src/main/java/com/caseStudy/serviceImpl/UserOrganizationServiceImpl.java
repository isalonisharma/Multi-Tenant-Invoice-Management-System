package com.caseStudy.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caseStudy.bean.CreateUserOrganizationBean;
import com.caseStudy.dao.UserOrganizationdao;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.UserOrganizationModel;
import com.caseStudy.service.UserOrganizationService;

@Service("userOrganizationService")
public class UserOrganizationServiceImpl implements UserOrganizationService {
	@Autowired
	private UserOrganizationdao userOrganizationdao;

	@Override
	public List<UserOrganizationModel> getAllUserOrganizationModel() {
		return userOrganizationdao.getAllUserOrganizationModel();
	}

	@Override
	public UserOrganizationModel getUserOrganizationModelByUserId(Long userId)
			throws UserOrganizationNotFoundException, UserNotFoundException {
		return userOrganizationdao.getUserOrganizationModelByUserId(userId);
	}

	@Override
	public UserOrganizationModel createUserOrganizationModel(CreateUserOrganizationBean userOrganizationBean)
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException {
		return userOrganizationdao.createUserOrganizationModel(userOrganizationBean);
	}

	@Override
	public UserOrganizationModel deleteUserOrganizationModel(Long userOrganizationId)
			throws UserOrganizationNotFoundException {
		return userOrganizationdao.deleteUserOrganizationModel(userOrganizationId);
	}

	@Override
	public UserOrganizationModel updateUserOrganizationModel(Long userOrganizationId,
			UserOrganizationModel userOrganizationDetails) throws UserOrganizationNotFoundException {
		return userOrganizationdao.updateUserOrganizationModel(userOrganizationId, userOrganizationDetails);
	}

	@Override
	public List<UserOrganizationModel> getUserOrganizationModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException {
		return userOrganizationdao.getUserOrganizationModelByOrganizationId(organizationId);
	}
}
