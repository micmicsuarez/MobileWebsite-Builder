package com.codeSmile.proMobileSiteBuilder.common

import com.codeSmile.proMobileSiteBuilder.exception.*
import org.springframework.http.HttpStatus

trait ExceptionHandler {

	def handleResourceNotFoundException(ResourceNotFoundException e) {
		/*response.status = HttpStatus.NOT_FOUND.value()
		respond new ApiErrorMessage(e.apiError).toMap()*/
	}
}