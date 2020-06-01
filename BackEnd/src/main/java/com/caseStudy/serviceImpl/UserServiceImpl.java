package com.caseStudy.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caseStudy.bean.CreateUserBean;
import com.caseStudy.dao.Userdao;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.RoleNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.exception.UserRoleNotFoundException;
import com.caseStudy.model.UserModel;
import com.caseStudy.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private Userdao userDao;

	@Override
	public List<UserModel> getAllUserModels() {
		return userDao.getAllUserModels();
	}

	@Override
	public UserModel getUserModelById(Long userId) throws UserNotFoundException {
		return userDao.getUserModelById(userId);
	}

	@Override
	public UserModel createUserModel(CreateUserBean createUserBean, long organizationId, long roleId)
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserRoleNotFoundException, RoleNotFoundException {
		return userDao.createUserModel(createUserBean, organizationId, roleId);
	}

	@Override
	public UserModel deleteUserModel(Long userId) throws UserNotFoundException {
		return userDao.deleteUserModel(userId);
	}

	@Override
	public UserModel updateUserModel(Long userId, UserModel userDetails) throws UserNotFoundException {
		return userDao.updateUserModel(userId, userDetails);
	}

	@Override
	public String findOrganizationByUsername(String username)
			throws UserOrganizationNotFoundException, UserNotFoundException {
		return userDao.findOrganizationByUsername(username);
	}

	@Override
	public List<UserModel> getAllUserModelsByOrganizationId(long organizationId) throws OrganizationNotFoundException {
		return userDao.getAllUserModelsByOrganizationId(organizationId);
	}

	@Override
	public UserModel findByUsername(String username) throws UserNotFoundException {
		return userDao.findByUsername(username);
	}

	@Override
	public UserModel findByUsernameAndPassword(String username, String password) throws UserNotFoundException {
		return userDao.findByUsernameAndPassword(username, password);
	}
}