package com.codeSmile.proMobileSiteBuilder.exception

import com.codeSmile.proMobileSiteBuilder.type.ApiError

public class InvalidRequestException extends RuntimeException {

	ApiError apiError

	InvalidRequestException(ApiError apiError = ApiError.INVALID_REQUEST) {
		super(apiError.errorMessage)
		this.apiError = apiError
	}
}