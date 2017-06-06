package com.codeSmile.proMobileSiteBuilder

import org.springframework.security.core.userdetails.UserDetails
import com.codeSmile.proMobileSiteBuilder.type.UserStatus
import com.codeSmile.proMobileSiteBuilder.type.AuthenticationTokenStatus
import grails.plugin.springsecurity.rest.token.generation.TokenGenerator
import grails.plugin.springsecurity.rest.token.AccessToken

import grails.test.mixin.TestFor
import spock.lang.Specification
import grails.buildtestdata.mixin.Build

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(AuthenticationTokenService)
@Build([User, AuthenticationToken])
class AuthenticationTokenServiceSpec extends Specification {

	TokenGenerator tokenGenerator = Mock()

	def setup() {
		service.tokenGenerator = tokenGenerator
		User.metaClass.encodePassword = {}
	}

	def cleanup() {
		GroovySystem.metaClassRegistry.removeMetaClass(User)
	}

	void "generateToken should generate an authentication token for the login user"() {
		given:
		String username = "micmicsuarez"
		String password = "password"
		User user = User.build(username: username, password: password, status: UserStatus.ACTIVE)
		tokenGenerator.generateAccessToken(_ as UserDetails) >>  new AccessToken("sample")

		when:
		def authenticationToken = service.generateToken(user)

		then:
		authenticationToken != null
		authenticationToken.token != null
		authenticationToken.status == AuthenticationTokenStatus.ACTIVE
	}
}
