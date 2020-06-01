package com.caseStudy.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caseStudy.bean.CreateUserOrganizationBean;
import com.caseStudy.dao.UserOrganizationdao;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.OrganizationModel;
import com.caseStudy.model.UserModel;
import com.caseStudy.model.UserOrganizationModel;
import com.caseStudy.repository.UserOrganizationRepository;
import com.caseStudy.service.OrganizationService;
import com.caseStudy.service.UserService;

@Repository
public class UserOrganizationdaoImpl implements UserOrganizationdao {
	static final Logger logger = Logger.getLogger(UserOrganizationdaoImpl.class);
	
	@Autowired
	private UserService userService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private UserOrganizationRepository userOrganizationRepository;

	@Override
	public List<UserOrganizationModel> getAllUserOrganizationModel() {
		logger.info("UserOrganizationdaoImpl--->>getAllUserOrganizationModel--->>Start");

		List<UserOrganizationModel> userOrganizationModelList = userOrganizationRepository.findAll();

		logger.info("UserOrganizationdaoImpl--->>getAllUserOrganizationModel--->>End");

		return userOrganizationModelList;
	}

	@Override
	public UserOrganizationModel getUserOrganizationModelByUserId(Long userId)
			throws UserOrganizationNotFoundException, UserNotFoundException {
		logger.info("UserOrganizationdaoImpl--->>getUserOrganizationModelByUserId--->>Start");

		UserModel userModel = userService.getUserModelById(userId);

		UserOrganizationModel userOrganizationModel = userOrganizationRepository.findByuserModel(userModel);

		logger.info("UserOrganizationdaoImpl--->>getUserOrganizationModelByUserId--->>End");

		return userOrganizationModel;
	}

	@Override
	public UserOrganizationModel createUserOrganizationModel(CreateUserOrganizationBean userOrganizationBean)
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException {
		logger.info("UserOrganizationdaoImpl--->>createUserOrganizationModel--->>Start");

		UserOrganizationModel userOrganizationModel = new UserOrganizationModel();

		UserModel userModel = userService.getUserModelById(userOrganizationBean.getUserId());

		OrganizationModel organizationModel = organizationService
				.getOrganizationModelById(userOrganizationBean.getOrganizationId());

		userOrganizationModel.setUserModel(userModel);
		userOrganizationModel.setOrganizationModel(organizationModel);

		userOrganizationRepository.save(userOrganizationModel);

		logger.info("UserOrganizationdaoImpl--->>createUserOrganizationModel--->>End");

		return userOrganizationModel;
	}

	@Override
	public UserOrganizationModel deleteUserOrganizationModel(Long userOrganizationId)
			throws UserOrganizationNotFoundException {
		logger.info("UserOrganizationdaoImpl--->>deleteUserOrganizationModel--->>Start");

		UserOrganizationModel userOrganizationModel = userOrganizationRepository.findById(userOrganizationId)
				.orElseThrow(() -> new UserOrganizationNotFoundException(
						"UserOrganization not found :: " + userOrganizationId));

		userOrganizationRepository.delete(userOrganizationModel);

		logger.info("UserOrganizationdaoImpl--->>deleteUserOrganizationModel--->>End");

		return userOrganizationModel;
	}

	@Override
	public UserOrganizationModel updateUserOrganizationModel(Long userOrganizationId,
			UserOrganizationModel userOrganizationDetails) throws UserOrganizationNotFoundException {
		logger.info("UserOrganizationdaoImpl--->>updateUserOrganizationModel--->>Start");

		UserOrganizationModel userOrganizationModel = userOrganizationRepository.findById(userOrganizationId)
				.orElseThrow(() -> new UserOrganizationNotFoundException(
						"UserOrganization not found :: " + userOrganizationId));

		userOrganizationModel.setUserModel(userOrganizationDetails.getUserModel());
		userOrganizationModel.setOrganizationModel(userOrganizationDetails.getOrganizationModel());

		final UserOrganizationModel updateduserOrganizationModel = userOrganizationRepository
				.save(userOrganizationModel);

		logger.info("UserOrganizationdaoImpl--->>updateUserOrganizationModel--->>End");

		return updateduserOrganizationModel;
	}

	@Override
	public List<UserOrganizationModel> getUserOrganizationModelByOrganizationId(long organizationId)
			throws OrganizationNotFoundException {
		logger.info("UserOrganizationdaoImpl--->>getUserOrganizationModelByOrganizationId--->>Start");

		OrganizationModel organizationModel = organizationService.getOrganizationModelById(organizationId);

		List<UserOrganizationModel> userOrganizationModelList = userOrganizationRepository
				.findByorganizationModel(organizationModel);

		logger.info("UserOrganizationdaoImpl--->>getUserOrganizationModelByOrganizationId--->>End");

		return userOrganizationModelList;
	}
}
