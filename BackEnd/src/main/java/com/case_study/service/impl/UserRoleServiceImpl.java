package com.case_study.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.case_study.bean.CreateUserRoleBean;
import com.case_study.entity.Role;
import com.case_study.entity.User;
import com.case_study.entity.UserRole;
import com.case_study.exception.RoleNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserRoleNotFoundException;
import com.case_study.model.UserRoleModel;
import com.case_study.repository.UserRoleRepository;
import com.case_study.service.RoleService;
import com.case_study.service.UserRoleService;
import com.case_study.service.UserService;
import com.case_study.utility.CommonConstants;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Override
	public UserRole createUserRole(CreateUserRoleBean createUserRoleBean)
			throws UserRoleNotFoundException, UserNotFoundException, RoleNotFoundException {
		UserRole userRole = new UserRole();
		User user = userService.findById(createUserRoleBean.getUserId());
		Role role = roleService.findById(createUserRoleBean.getRoleId());
		userRole.setRole(role);
		userRole.setUser(user);
		userRoleRepository.save(userRole);
		return userRole;
	}

	@Override
	public UserRole deleteById(Long id) throws UserRoleNotFoundException {
		UserRole userRole = userRoleRepository.findById(id)
				.orElseThrow(() -> new UserRoleNotFoundException(CommonConstants.USER_ROLE_NOT_FOUND + id));
		userRoleRepository.delete(userRole);
		return userRole;
	}

	@Override
	public UserRole updateById(Long id, UserRoleModel userRoleModel)
			throws UserRoleNotFoundException, UserNotFoundException, RoleNotFoundException {
		UserRole userRole = userRoleRepository.findById(id)
				.orElseThrow(() -> new UserRoleNotFoundException(CommonConstants.USER_ROLE_NOT_FOUND + id));
		User user = userService.findById(userRoleModel.getUser().getId());
		Role role = roleService.findById(userRoleModel.getRole().getId());
		userRole.setUser(user);
		userRole.setRole(role);
		return userRoleRepository.save(userRole);
	}

	@Override
	public List<UserRole> findAll() {
		return userRoleRepository.findAll();
	}

	@Override
	public List<UserRole> findByUserId(Long id) throws UserRoleNotFoundException, UserNotFoundException {
		User user = userService.findById(id);
		return userRoleRepository.findByUser(user);
	}

	@Override
	public List<UserRole> findByUser(User user) {
		return userRoleRepository.findByUser(user);
	}
}