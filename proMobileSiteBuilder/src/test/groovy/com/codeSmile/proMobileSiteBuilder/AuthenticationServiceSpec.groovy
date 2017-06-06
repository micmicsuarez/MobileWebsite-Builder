package com.codeSmile.proMobileSiteBuilder

import com.codeSmile.proMobileSiteBuilder.type.UserStatus
import com.codeSmile.proMobileSiteBuilder.exception.AuthenticationException
import org.springframework.security.authentication.encoding.PasswordEncoder

import grails.test.mixin.TestFor
import spock.lang.Specification
import grails.buildtestdata.mixin.Build

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(AuthenticationService)
@Build([User])
class AuthenticationServiceSpec extends Specification {
	
	UserService userService = Mock()
	PasswordEncoder passwordEncoder = Mock()

	def setup() {
		User.metaClass.encodePassword = {}
		service.userService = userService
		service.passwordEncoder = passwordEncoder
	}

	def cleanup() {
		GroovySystem.metaClassRegistry.removeMetaClass(User)
	}

	void "authenticate method should authenticate  the user cedentials and return the user"() {
		given:
		String username = "micmicsuarez"
		String password = "password"
		User user = User.build(username: username, password: password, status: UserStatus.ACTIVE)

		 userService.fetchUserByUsernameOrEmail(username) >> user
		 passwordEncoder.isPasswordValid(user.password, password, null) >> true

		when:
		def validatedUser = service.authenticate(username, password)

		then:
		validatedUser != null
	}

	void "authenticate method should throw AuthenticationException when user's status is not ACTIVE"() {
		given:
		String username = "micmicsuarez"
		String password = "password"
		User user = User.build(username: username, password: password, status: UserStatus.DELETED)

		 userService.fetchUserByUsernameOrEmail(username) >> user

		when:
		service.authenticate(username, password)

		then:
		thrown(AuthenticationException)
	}

	void "authenticate method should throw AuthenticationException when username is invalid"() {
		given:
		String invalidUsername = "michael"
		String password = "password"
		User user = User.build(username: "micmicsuarez", password: password, status: UserStatus.ACTIVE)
		
		userService.fetchUserByUsernameOrEmail(invalidUsername) >> null

		when:
		service.authenticate(invalidUsername, password)

		then:
		thrown(AuthenticationException)
	}

	void "authenticate method should throw an exception when password is incorrect"() {
		given:
		String username = "micmicsuarez"
		String password = "password"
		String incorrectPassword = "incorrectPassword"
		User user = User.build(username: username, password: password, status: UserStatus.ACTIVE)

		 userService.fetchUserByUsernameOrEmail(username) >> user
		 passwordEncoder.isPasswordValid(user.password, incorrectPassword, null) >> false

		when:
		service.authenticate(username, incorrectPassword)

		then:
		thrown(AuthenticationException)
	}
}
