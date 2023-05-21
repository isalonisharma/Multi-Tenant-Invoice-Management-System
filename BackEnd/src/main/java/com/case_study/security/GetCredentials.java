package com.case_study.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Check the credentials of current user who is log in
 * 
 * @author saloni.sharma
 */
public class GetCredentials {
	public static String checkUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	public static int checkRoleAuthentication() {
		int flag = 0;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"))) {
			flag = 1;
		} else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ORGANIZATION_ADMIN"))) {
			flag = 2;
		} else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			flag = 0;
		}
		return flag;
	}
}