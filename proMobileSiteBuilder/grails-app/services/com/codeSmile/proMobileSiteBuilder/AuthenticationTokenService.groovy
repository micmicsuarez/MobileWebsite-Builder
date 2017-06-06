package com.codeSmile.proMobileSiteBuilder

import org.springframework.security.core.userdetails.UserDetails
import grails.plugin.springsecurity.rest.token.generation.TokenGenerator
import grails.transaction.Transactional

@Transactional
class AuthenticationTokenService {
	
	TokenGenerator tokenGenerator

	public AuthenticationToken generateToken(User user) {
		String tokenValue = tokenGenerator.generateAccessToken(user as UserDetails).accessToken
		AuthenticationToken authenticationToken = new AuthenticationToken(
			token: tokenValue,
			user: user
		)

		return authenticationToken.save(flush: true, failOnError: true)
	}
}
