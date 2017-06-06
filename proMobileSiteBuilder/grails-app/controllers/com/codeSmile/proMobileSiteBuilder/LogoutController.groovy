package com.codeSmile.proMobileSiteBuilder

import com.codeSmile.proMobileSiteBuilder.common.ExceptionHandler
import grails.plugin.springsecurity.annotation.Secured


class LogoutController  {

	static responseFormats = ['json']

	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def logout() {

	}
}