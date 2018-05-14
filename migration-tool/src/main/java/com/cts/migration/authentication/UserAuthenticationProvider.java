package com.cts.migration.authentication;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.cts.migration.entity.CustomUser;
import com.cts.migration.service.UserManagementService;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserManagementService customUserService;

	private static final Logger log = LoggerFactory.getLogger(UserAuthenticationProvider.class);

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		log.info("Authetication in progress");
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		if(null == username || null == password || "".equals(username) || "".equals(password)){
			throw new BadCredentialsException("Both username and password is required");
		}


		CustomUser user = customUserService.loadUserByUsername(username);
		if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
			throw new BadCredentialsException("Username not found.");
		}
		if (!password.equals(user.getPassword())) {
			throw new BadCredentialsException("Wrong password.");
		}

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	public boolean supports(Class<?> arg0) {
		return true;
	}

}
