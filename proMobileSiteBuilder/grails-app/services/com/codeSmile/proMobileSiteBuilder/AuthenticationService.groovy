package com.codeSmile.proMobileSiteBuilder

import com.codeSmile.proMobileSiteBuilder.exception.AuthenticationException
import com.codeSmile.proMobileSiteBuilder.type.UserStatus
import com.codeSmile.proMobileSiteBuilder.type.ApiError

import grails.transaction.Transactional
import groovy.util.logging.Log4j
import org.springframework.security.authentication.encoding.PasswordEncoder

@Transactional
public class AuthenticationService {
	
	PasswordEncoder passwordEncoder
	UserService userService

	public User authenticate(String username, String password) {

		User user = userService.fetchUserByUsernameOrEmail(username)

		if(!user) {
			throw new AuthenticationException(ApiError.UNREGISTERED_USER)
		}

		if(user.status != UserStatus.ACTIVE) {
			throw new AuthenticationException(ApiError.ACCESS_DENIED)
		}

		if(!passwordEncoder.isPasswordValid(user.password, password, null)) {
			throw new AuthenticationException(ApiError.AUTHENTICATION_FAILED)
		}

		return user
	}
}
