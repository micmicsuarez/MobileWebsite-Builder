package com.codeSmile.proMobileSiteBuilder.exception

import com.codeSmile.proMobileSiteBuilder.type.ApiError

public class ResourceNotFoundException extends RuntimeException {

	ApiError apiError

	ResourceNotFoundException(ApiError apiError = ApiError.RESOURCE_NOT_FOUND) {
		super(apiError.errorMessage)
		this.apiError = apiError
	}
}