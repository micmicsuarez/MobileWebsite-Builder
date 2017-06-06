package com.codeSmile.proMobileSiteBuilder.common

import org.springframework.http.HttpStatus

trait ResponseHandler {

	void sendResponse(){
		render status: HttpStatus.NO_CONTENT
	}
}