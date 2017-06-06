package com.codeSmile.proMobileSiteBuilder.exception

import com.codeSmile.proMobileSiteBuilder.type.ApiError

public class BadRequestException extends RuntimeException {

	ApiError apiError

	BadRequestException(ApiError apiError = ApiError.BAD_REQUEST) {
		super(apiError.errorMessage)
		this.apiError = apiError
	}
}