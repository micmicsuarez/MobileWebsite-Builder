package com.codeSmile.proMobileSiteBuilder.dto

import com.codeSmile.proMobileSiteBuilder.type.ApiError
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
public class ApiErrorMessage {

	public ApiErrorMessage(ApiError apiError, List<ValidationErrorMessage> validationErrorMessages) {
		this.apiError = apiError
		this.validationErrorMessages = validationErrorMessages
	}

	public ApiErrorMessage(ApiError apiError) {
		this.apiError = apiError
		this.validationErrorMessages = []
	}

	ApiError apiError
	List<ValidationErrorMessage> validationErrorMessages
	
	public Map toMap() {
		def map = [
			errorCode: apiError.errorCode,
			errorMessage: apiError.errorMessage
		]

		if (apiError == ApiError.VALIDATION_ERROR || apiError == ApiError.INVALID_REQUEST) {
			def validationErrorList = []
			validationErrorMessages.each { validationErrorMessage ->
				validationErrorList.add(validationErrorMessage.toMap())
			}
			map.put('validationErrors', validationErrorList)
		}

		return map
	}
}