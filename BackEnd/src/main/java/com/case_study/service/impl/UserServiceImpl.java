package com.case_study.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.case_study.bean.CreateUserBean;
import com.case_study.bean.CreateUserOrganizationBean;
import com.case_study.bean.CreateUserRoleBean;
import com.case_study.entity.User;
import com.case_study.entity.UserOrganization;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.exception.RoleNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.exception.UserRoleNotFoundException;
import com.case_study.model.UserRequestModel;
import com.case_study.repository.UserRepository;
import com.case_study.service.UserOrganizationService;
import com.case_study.service.UserRoleService;
import com.case_study.service.UserService;
import com.case_study.utility.CommonConstants;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private UserOrganizationService userOrganizationService;

	@Override
	public User createUser(CreateUserBean createUserBean, long organizationId, long roleId)
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException,
			UserRoleNotFoundException, RoleNotFoundException {
		User user = userRepository.save(new User(createUserBean.getUsername(), createUserBean.getFirstName(),
				createUserBean.getLastName(), createUserBean.getMobileNumber(), createUserBean.getPassword(),
				createUserBean.isUserIsLocked()));
		userRoleService.createUserRole(new CreateUserRoleBean(user.getId(), roleId));
		userOrganizationService.createUserOrganization(new CreateUserOrganizationBean(user.getId(), organizationId));
		return user;
	}

	@Override
	public User deleteById(Long id) throws UserNotFoundException {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(CommonConstants.USER_NOT_FOUND + id));
		user.setLocked(true);
		return userRepository.save(user);
	}

	@Override
	public User updateById(Long id, UserRequestModel userRequestModel) throws UserNotFoundException {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(CommonConstants.USER_NOT_FOUND + id));
		if (user.isLocked()) {
			throw new UserNotFoundException("User not available :: " + id);
		}
		user.setFirstName(userRequestModel.getFirstName());
		user.setLastName(userRequestModel.getLastName());
		user.setMobileNumber(userRequestModel.getMobileNumber());
		user.setPassword(userRequestModel.getPassword());
		user.setLocked(userRequestModel.isLocked());
		return userRepository.save(user);
	}

	@Override
	public List<User> getActiveUsers() {
		List<User> users = userRepository.findAll();
		users.removeIf(User::isLocked);
		return users;
	}

	@Override
	public User findById(Long id) throws UserNotFoundException {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(CommonConstants.USER_NOT_FOUND + id));
		if (user.isLocked()) {
			throw new UserNotFoundException(CommonConstants.USER_NOT_FOUND + id);
		}
		return user;
	}

	@Override
	public User findByUsername(String username) throws UserNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user.isLocked()) {
			throw new UserNotFoundException(CommonConstants.USER_NOT_FOUND + username);
		}
		return user;
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) throws UserNotFoundException {
		User user = userRepository.findByUsernameAndPassword(username, password);
		if (user.isLocked()) {
			throw new UserNotFoundException(CommonConstants.USER_NOT_FOUND + username);
		}
		return user;
	}

	@Override
	public List<User> findByOrganizationId(long organizationId) throws OrganizationNotFoundException {
		List<User> activeUsers = new ArrayList<>();
		List<UserOrganization> userOrganizations = userOrganizationService.findByOrganizationId(organizationId);
		for (UserOrganization userOrganization : userOrganizations) {
			activeUsers.add(userOrganization.getUser());
		}
		activeUsers.removeIf(User::isLocked);
		return activeUsers;
	}

	@Override
	public String findOrganizationByUsername(String username)
			throws UserOrganizationNotFoundException, UserNotFoundException {
		long id = userRepository.findByUsername(username).getId();
		return userOrganizationService.findByUserId(id).getOrganization().getName();
	}
}