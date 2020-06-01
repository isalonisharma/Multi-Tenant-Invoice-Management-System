package com.caseStudy.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caseStudy.bean.CreateUserBean;
import com.caseStudy.bean.CreateUserOrganizationBean;
import com.caseStudy.bean.CreateUserRoleBean;
import com.caseStudy.dao.Userdao;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.RoleNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.exception.UserRoleNotFoundException;
import com.caseStudy.model.UserModel;
import com.caseStudy.model.UserOrganizationModel;
import com.caseStudy.repository.UserRepository;
import com.caseStudy.service.UserOrganizationService;
import com.caseStudy.service.UserRoleService;

@Repository
public class UserdaoImpl implements Userdao {
	static final Logger logger = Logger.getLogger(UserdaoImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private UserOrganizationService userOrganizationService;

	@Override
	public List<UserModel> getAllUserModels() {
		logger.info("UserdaoImpl--->>getAllUserModels--->>Start");

		List<UserModel> userModelList = userRepository.findAll();
		
		userModelList.removeIf((UserModel userModel) -> userModel.isUserIsLocked());

		logger.info("UserdaoImpl--->>getAllUserModels--->>End");

		return userModelList;
	}

	@Override
	public UserModel getUserModelById(Long userId) throws UserNotFoundException {
		logger.info("UserdaoImpl--->>getUserModelById--->>Start");

		UserModel userModel = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found :: " + userId));

		if (userModel.isUserIsLocked()) {
			throw new UserNotFoundException("User not available :: " + userId);
		}

		logger.info("UserdaoImpl--->>getUserModelById--->>End");

		return userModel;
	}

	@Override
	public UserModel createUserModel(CreateUserBean createUserBean, long organizationId, long roleId)
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserRoleNotFoundException, RoleNotFoundException {

		logger.info("UserdaoImpl--->>createUserModel--->>Start");

		UserModel userModel = userRepository.save(new UserModel(createUserBean.getUsername(),
				createUserBean.getFirstName(), createUserBean.getLastName(), createUserBean.getMobileNumber(),
				createUserBean.getPassword(), createUserBean.isUserIsLocked()));

		userRoleService.createUserRoleModel(new CreateUserRoleBean(userModel.getUserId(), roleId));

		userOrganizationService
				.createUserOrganizationModel(new CreateUserOrganizationBean(userModel.getUserId(), organizationId));

		logger.info("UserdaoImpl--->>createUserModel--->>End");

		return userModel;
	}

	@Override
	public UserModel deleteUserModel(Long userId) throws UserNotFoundException {
		logger.info("UserdaoImpl--->>deleteUserModel--->>Start");

		UserModel userModel = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found :: " + userId));

		userModel.setUserIsLocked(true);

		final UserModel deletedUserModel = userRepository.save(userModel);

		logger.info("UserdaoImpl--->>deleteUserModel--->>End");

		return deletedUserModel;
	}

	@Override
	public UserModel updateUserModel(Long userId, UserModel userDetails) throws UserNotFoundException {
		logger.info("UserdaoImpl--->>updateUserModel--->>Start");

		UserModel userModel = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found :: " + userId));

		if (userModel.isUserIsLocked()) {
			throw new UserNotFoundException("User not available :: " + userId);
		}

		userModel.setFirstName(userDetails.getFirstName());
		userModel.setLastName(userDetails.getLastName());
		userModel.setMobileNumber(userDetails.getMobileNumber());
		userModel.setPassword(userDetails.getPassword());
		userModel.setUserIsLocked(userDetails.isUserIsLocked());

		final UserModel updatedUserModel = userRepository.save(userModel);

		logger.info("UserdaoImpl--->>updateUserModel--->>End");

		return updatedUserModel;
	}

	@Override
	public String findOrganizationByUsername(String username)
			throws UserOrganizationNotFoundException, UserNotFoundException {
		logger.info("UserdaoImpl--->>findOrganizationByUsername--->>Start");

		long userId = userRepository.findByUsername(username).getUserId();

		String organizationName = userOrganizationService.getUserOrganizationModelByUserId(userId)
				.getOrganizationModel().getOrganizationName();

		logger.info("UserdaoImpl--->>findOrganizationByUsername--->>End");

		return organizationName;
	}

	@Override
	public List<UserModel> getAllUserModelsByOrganizationId(long organizationId) throws OrganizationNotFoundException {
		logger.info("UserdaoImpl--->>getAllUserModelsByOrganizationId--->>Start");

		List<UserModel> userModelList = new ArrayList<UserModel>();

		List<UserOrganizationModel> userOrganizationModelList = userOrganizationService
				.getUserOrganizationModelByOrganizationId(organizationId);

		for (UserOrganizationModel userOrganizationModel : userOrganizationModelList) {
			userModelList.add(userOrganizationModel.getUserModel());
		}
		
		
		userModelList.removeIf((UserModel userModel) -> userModel.isUserIsLocked());
		
		logger.info("UserdaoImpl--->>getAllUserModelsByOrganizationId--->>End");
		
		return userModelList;
	}

	@Override
	public UserModel findByUsername(String username) throws UserNotFoundException {
		logger.info("UserdaoImpl--->>findByUsername--->>Start");

		UserModel userModel = userRepository.findByUsername(username);
		
		if (userModel.isUserIsLocked()) {
			throw new UserNotFoundException("User not available :: " + username);
		}

		logger.info("UserdaoImpl--->>findByUsername--->>End");

		return userModel;
	}

	@Override
	public UserModel findByUsernameAndPassword(String username, String password) throws UserNotFoundException {
		logger.info("UserdaoImpl--->>findByUsernameAndPassword--->>Start");

		UserModel userModel = userRepository.findByUsernameAndPassword(username, password);
		
		if (userModel.isUserIsLocked()) {
			throw new UserNotFoundException("User not available :: " + username);
		}

		logger.info("UserdaoImpl--->>findByUsernameAndPassword--->>End");

		return userModel;
	}
}
