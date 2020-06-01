package com.caseStudy.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.caseStudy.repository.UserOrganizationRepository;
import com.caseStudy.repository.UserRepository;
import com.caseStudy.repository.UserRoleRepository;

public class CurrentUserDetailsModel implements UserDetails {
	private static final long serialVersionUID = 1L;
	private UserModel userModel;

	UserRepository userRepository;
	UserRoleRepository userRoleRepository;
	UserOrganizationRepository organizationRepository;

	public CurrentUserDetailsModel(UserModel userModel, UserRoleRepository userRoleRepository) {
		super();
		this.userModel = userModel;
		this.userRoleRepository = userRoleRepository;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		List<UserRoleModel> listUserRoles = userRoleRepository.findByuserModel(userModel);
		for (UserRoleModel userrole : listUserRoles) {
			authorities.add(new SimpleGrantedAuthority(userrole.getRoleModel().getRole()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return userModel.getPassword();
	}

	@Override
	public String getUsername() {
		return userModel.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getOrganization() {

		return null;
	}
}