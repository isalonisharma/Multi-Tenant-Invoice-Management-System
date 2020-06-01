package com.caseStudy.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caseStudy.bean.CreateUserRoleBean;
import com.caseStudy.dao.UserRoledao;
import com.caseStudy.exception.RoleNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserRoleNotFoundException;
import com.caseStudy.model.UserModel;
import com.caseStudy.model.UserRoleModel;
import com.caseStudy.service.UserRoleService;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	private UserRoledao userRoleDao;

	@Override
	public List<UserRoleModel> getAllUserRoleModel() {
		return userRoleDao.getAllUserRoleModel();
	}

	@Override
	public UserRoleModel createUserRoleModel(CreateUserRoleBean userRole)
			throws UserRoleNotFoundException, UserNotFoundException, RoleNotFoundException {
		return userRoleDao.createUserRoleModel(userRole);
	}

	@Override
	public UserRoleModel deleteUserRoleModel(Long userRoleId) throws UserRoleNotFoundException {
		return userRoleDao.deleteUserRoleModel(userRoleId);
	}

	@Override
	public UserRoleModel updateUserRoleModel(Long userRoleId, UserRoleModel userRoleDetails)
			throws UserRoleNotFoundException {
		return userRoleDao.updateUserRoleModel(userRoleId, userRoleDetails);
	}

	@Override
	public List<UserRoleModel> getUserRoleModelByUserId(Long userId)
			throws UserRoleNotFoundException, UserNotFoundException {
		return userRoleDao.getUserRoleModelByUserId(userId);
	}

	@Override
	public List<UserRoleModel> findByuserModel(UserModel userModel) {
		return userRoleDao.findByuserModel(userModel);
	}
}