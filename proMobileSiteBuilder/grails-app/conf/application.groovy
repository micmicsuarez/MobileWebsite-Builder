

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.codeSmile.proMobileSiteBuilder.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.codeSmile.proMobileSiteBuilder.UserRole'
grails.plugin.springsecurity.authority.className = 'com.codeSmile.proMobileSiteBuilder.Role'

grails.plugin.springsecurity.rest.token.storage.useGorm = true
grails.plugin.springsecurity.rest.token.storage.gorm.tokenDomainClassName = 'com.codeSmile.proMobileSiteBuilder.AuthenticationToken'
grails.plugin.springsecurity.rest.token.storage.gorm.tokenValuePropertyName = 'token'

grails.plugin.springsecurity.secureChannel.useHeaderCheckChannelSecurity = true
grails.plugin.springsecurity.secureChannel.secureHeaderName = 'X-Forwarded-Proto'
grails.plugin.springsecurity.secureChannel.secureHeaderValue = 'http'
grails.plugin.springsecurity.secureChannel.insecureHeaderName = 'X-Forwarded-Proto'
grails.plugin.springsecurity.secureChannel.insecureHeaderValue = 'https'
grails.plugin.springsecurity.secureChannel.definition = [
	[pattern: '/**',        access: 'REQUIRES_SECURE_CHANNEL']
]

grails {
	plugin {
		springsecurity {
			filterChain {
				chainMap = [
					[pattern: '/api/auth/login', filters: 'anonymousAuthenticationFilter'] ,
					[pattern:'/api/**', filters:'JOINED_FILTERS,-anonymousAuthenticationFilter, -exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter, -restAuthenticationFilter'],
					[pattern: '/**', filters:'anonymousAuthenticationFilter']

				]
			}
			
			rest {
				token {
					validation {
						enableAnonymousAccess = true
					}
				}
			}
		}
	}
}