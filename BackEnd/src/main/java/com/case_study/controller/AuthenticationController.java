package com.case_study.controller;

import java.util.Base64;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.case_study.bean.LoginBean;
import com.case_study.entity.User;
import com.case_study.entity.UserRole;
import com.case_study.exception.UserNotFoundException;
import com.case_study.model.UserResponseModel;
import com.case_study.service.UserRoleService;
import com.case_study.service.UserService;

@RestController
public class AuthenticationController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	@PostMapping("/login")
	UserResponseModel findByUserNameAndPassword(@Valid @RequestBody LoginBean loginBean) throws UserNotFoundException {
		User user = userService.findByUsernameAndPassword(loginBean.getUsername(), loginBean.getPassword());
		String stringToEncode = user.getUsername() + ":" + user.getPassword();
		String token = Base64.getEncoder().encodeToString(stringToEncode.getBytes());
		List<UserRole> userRole = userRoleService.findByUser(user);
		return new UserResponseModel(user, userRole, token);
	}
}