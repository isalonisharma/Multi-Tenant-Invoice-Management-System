package com.caseStudy.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caseStudy.bean.CreateUserRoleBean;
import com.caseStudy.dao.UserRoledao;
import com.caseStudy.exception.RoleNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserRoleNotFoundException;
import com.caseStudy.model.RoleModel;
import com.caseStudy.model.UserModel;
import com.caseStudy.model.UserRoleModel;
import com.caseStudy.repository.UserRoleRepository;
import com.caseStudy.service.RoleService;
import com.caseStudy.service.UserService;

@Repository
public class UserRoledaoImpl implements UserRoledao {

	static final Logger logger = Logger.getLogger(UserRoledaoImpl.class);

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Override
	public List<UserRoleModel> getAllUserRoleModel() {

		logger.info("UserRoledaoImpl--->>getAllUserRoleModel--->>Start");

		List<UserRoleModel> userRoleModelList = userRoleRepository.findAll();

		logger.info("UserRoledaoImpl--->>getAllUserRoleModel--->>End");

		return userRoleModelList;
	}

	@Override
	public List<UserRoleModel> getUserRoleModelByUserId(Long userId)
			throws UserRoleNotFoundException, UserNotFoundException {

		logger.info("UserRoledaoImpl--->>getUserRoleModelByUserId--->>Start");

		List<UserRoleModel> userRoleList = null;

		UserModel userModel = userService.getUserModelById(userId);

		userRoleList = userRoleRepository.findByuserModel(userModel);

		logger.info("UserRoledaoImpl--->>getUserRoleModelByUserId--->>End");

		return userRoleList;
	}

	@Override
	public UserRoleModel createUserRoleModel(CreateUserRoleBean userRole)
			throws UserRoleNotFoundException, UserNotFoundException, RoleNotFoundException {

		logger.info("UserRoledaoImpl--->>createUserRoleModel--->>Start");

		UserRoleModel userRoleModel = new UserRoleModel();

		UserModel userModel = userService.getUserModelById(userRole.getUserId());

		RoleModel roleModel = roleService.getRoleModelById(userRole.getRoleId());

		userRoleModel.setRoleModel(roleModel);
		userRoleModel.setUserModel(userModel);

		userRoleRepository.save(userRoleModel);

		logger.info("UserRoledaoImpl--->>createUserRoleModel--->>End");

		return userRoleModel;
	}

	@Override
	public UserRoleModel deleteUserRoleModel(Long userRoleId) throws UserRoleNotFoundException {

		logger.info("UserRoledaoImpl--->>deleteUserRoleModel--->>Start");

		UserRoleModel userRoleModel = userRoleRepository.findById(userRoleId)
				.orElseThrow(() -> new UserRoleNotFoundException("UserRole not found :: " + userRoleId));

		userRoleRepository.delete(userRoleModel);

		logger.info("UserRoledaoImpl--->>deleteUserRoleModel--->>End");

		return userRoleModel;
	}

	@Override
	public UserRoleModel updateUserRoleModel(Long userRoleId, UserRoleModel userRoleDetails)
			throws UserRoleNotFoundException {

		logger.info("UserRoledaoImpl--->>updateUserRoleModel--->>Start");

		UserRoleModel userRoleModel = userRoleRepository.findById(userRoleId)
				.orElseThrow(() -> new UserRoleNotFoundException("UserRole not found :: " + userRoleId));

		userRoleModel.setUserModel(userRoleDetails.getUserModel());

		userRoleModel.setRoleModel(userRoleDetails.getRoleModel());

		final UserRoleModel updateduserRoleModel = userRoleRepository.save(userRoleModel);

		logger.info("UserRoledaoImpl--->>updateUserRoleModel--->>End");

		return updateduserRoleModel;
	}

	@Override
	public List<UserRoleModel> findByuserModel(UserModel userModel) {

		logger.info("UserRoledaoImpl--->>findByuserModel--->>Start");

		List<UserRoleModel> userRoleModelList = userRoleRepository.findByuserModel(userModel);

		logger.info("UserRoledaoImpl--->>findByuserModel--->>End");

		return userRoleModelList;
	}
}
