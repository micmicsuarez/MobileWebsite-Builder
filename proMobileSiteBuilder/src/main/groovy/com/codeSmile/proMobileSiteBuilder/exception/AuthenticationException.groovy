package com.codeSmile.proMobileSiteBuilder.exception

import com.codeSmile.proMobileSiteBuilder.type.ApiError

public class AuthenticationException extends RuntimeException {

	ApiError apiError

	AuthenticationException(ApiError apiError = ApiError.RESOURCE_NOT_FOUND) {
		super(apiError.errorMessage)
		this.apiError = apiError
	}
}