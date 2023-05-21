package com.case_study.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.case_study.entity.User;
import com.case_study.entity.UserRole;
import com.case_study.repository.UserOrganizationRepository;
import com.case_study.repository.UserRepository;
import com.case_study.repository.UserRoleRepository;

public class CurrentUserDetailsModel implements UserDetails {
	private static final long serialVersionUID = 1L;
	private User user;

	UserRepository userRepository;
	UserRoleRepository userRoleRepository;
	UserOrganizationRepository organizationRepository;

	public CurrentUserDetailsModel(User user, UserRoleRepository userRoleRepository) {
		super();
		this.user = user;
		this.userRoleRepository = userRoleRepository;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		List<UserRole> listUserRoles = userRoleRepository.findByUser(user);
		for (UserRole userrole : listUserRoles) {
			authorities.add(new SimpleGrantedAuthority(userrole.getRole().getDesignation()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
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