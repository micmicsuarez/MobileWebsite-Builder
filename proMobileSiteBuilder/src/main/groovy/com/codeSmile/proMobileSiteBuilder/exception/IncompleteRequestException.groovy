package com.codeSmile.proMobileSiteBuilder.exception

import com.codeSmile.proMobileSiteBuilder.type.ApiError

public class IncompleteRequestException extends RuntimeException {

	ApiError apiError

	IncompleteRequestException(ApiError apiError = ApiError.INCOMPLETE_REQUEST) {
		super(apiError.errorMessage)
		this.apiError = apiError
	}
}