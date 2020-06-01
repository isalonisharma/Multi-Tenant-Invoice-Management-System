package com.caseStudy.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.caseStudy.model.CurrentUserDetailsModel;
import com.caseStudy.model.UserModel;
import com.caseStudy.repository.UserRepository;
import com.caseStudy.repository.UserRoleRepository;

@Service
public class CurrentUserDetailsService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel userModel = userRepository.findByUsername(username);
		if (userModel == null) {
			throw new UsernameNotFoundException("User 404 Not Found");
		}
		return new CurrentUserDetailsModel(userModel, userRoleRepository);
	}

}
