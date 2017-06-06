package com.codeSmile.proMobileSiteBuilder.exception

import  com.codeSmile.proMobileSiteBuilder.type.ValidationError
import com.codeSmile.proMobileSiteBuilder.dto.ValidationErrorMessage
import com.codeSmile.proMobileSiteBuilder.dto.ApiErrorMessage

public class ValidationObjectException extends RuntimeException {

	Object invalidObject
	List<ValidationErrorMessage> errorMessages

	public ValidationObjectException(String message, Object invalidObject, List<ValidationErrorMessage> errorMessages = []) {
		super(message)
		this.invalidObject = invalidObject
		this.errorMessages = errorMessages
	}

	public ApiErrorMessage getApiErrorMessage() {
		def validationErrorMessages = []

		invalidObject.errors.fieldErrors.each { 
			validationErrorMessages.add(new ValidationErrorMessage(ValidationError.valueOfCode(it.code), it.field))
		}

		errorMessages.each { errorMessage ->
			validationErrorMessages.add(errorMessage)
		}

		invalidObject.errors.globalErrors.each {
			validationErrorMessages.add(new ValidationErrorMessage(ValidationError.valueOfCode(it.code)))
		}

		return new ApiErrorMessage(ApiError.VALIDATION_ERROR, validationErrorMessages)
	}
}