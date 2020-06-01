package com.caseStudy.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Class Name: GetCredentials Description: Used to check the credentials of
 * current user who is log in
 * 
 * @author saloni.sharma
 */
public class GetCredentials {
	private static final Logger logger = LogManager.getLogger(GetCredentials.class.getName());

	/**
	 * Function Name: checkUserName Description: Checking the user name of current
	 * log in user
	 * 
	 * @return userName
	 */
	public static String checkUserName() {
		logger.info("GetCredentials--->>checkUserName--->>Start");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		logger.info(
				"GetCredentials--->>checkUserName--->>authentication.getPrincipal()" + authentication.getPrincipal());

		String userName = (authentication.getName());
		logger.info("GetCredentials--->>checkUserName--->>End");
		return userName;
	}

	/**
	 * Function Name: checkRoleAuthentication Description: Checking the role of
	 * current log in user
	 * 
	 * @return flag
	 */
	public static int checkRoleAuthentication() {
		logger.info("GetCredentials--->>checkAdminAuth--->>Start");
		
		int flag = 0;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		logger.info("GetCredentials--->>checkAdminAuth--->>authentication.getAuthorities()"
				+ authentication.getAuthorities());

		if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"))) {
			flag = 1;
		} else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ORGANIZATION_ADMIN"))) {
			flag = 2;
		} else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			flag = 0;
		}
		
		logger.info("GetCredentials--->>checkAdminAuth--->>End");
		
		return flag;
	}

}