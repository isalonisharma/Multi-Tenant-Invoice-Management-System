package com.caseStudy.controller;

import java.util.Base64;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.caseStudy.bean.LoginBean;
import com.caseStudy.dto.UserDTO;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.model.UserModel;
import com.caseStudy.model.UserRoleModel;
import com.caseStudy.service.UserRoleService;
import com.caseStudy.service.UserService;

@RestController
public class AuthenticationController {

	static final Logger logger = Logger.getLogger(AuthenticationController.class);

	@Autowired
	UserService userService;

	@Autowired
	UserRoleService userRoleService;

	@PostMapping("/login")
	UserDTO checkUserByUserNamePassword(@Valid @RequestBody LoginBean loginRequestBean) throws UserNotFoundException {
		logger.info("AuthenticationController--->>checkUserByUserNamePassword--->>Start");

		UserModel userModel = userService.findByUsernameAndPassword(loginRequestBean.getUsername(),
				loginRequestBean.getPassword());

		String s = userModel.getUsername() + ":" + userModel.getPassword();

		String token = Base64.getEncoder().encodeToString(s.getBytes());

		List<UserRoleModel> userRole = userRoleService.findByuserModel(userModel);

		UserDTO userDTO = new UserDTO(userModel, userRole, token);

		logger.info("AuthenticationController--->>checkUserByUserNamePassword--->>Ended");

		return userDTO;
	}
}
