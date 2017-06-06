package com.codeSmile.proMobileSiteBuilder.exception

import com.codeSmile.proMobileSiteBuilder.type.ApiError

public class ValidationRequestException extends RuntimeException {

	ApiError apiError

	ValidationRequestException(ApiError apiError) {
		super(apiError.errorMessage)
		this.apiError = apiError
	}
}