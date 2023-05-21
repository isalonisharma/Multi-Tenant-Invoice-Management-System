package com.case_study.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.case_study.entity.User;
import com.case_study.model.CurrentUserDetailsModel;
import com.case_study.repository.UserRepository;
import com.case_study.repository.UserRoleRepository;
import com.case_study.utility.CommonConstants;

@Service
public class CurrentUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(CommonConstants.USER_404_NOT_FOUND);
		}
		return new CurrentUserDetailsModel(user, userRoleRepository);
	}
}