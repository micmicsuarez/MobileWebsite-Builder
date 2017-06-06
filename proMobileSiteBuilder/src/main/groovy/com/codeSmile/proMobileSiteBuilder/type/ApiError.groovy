package com.codeSmile.proMobileSiteBuilder.type

public enum ApiError {

	AUTHENTICATION_FAILED("Wrong Username/Email and/or password combination."),
	VALIDATION_ERROR("Input data validation error."),
	RESOURCE_NOT_FOUND("Resouce not found"),
	REQUEST_NOT_ALLOWED("Request not allowed"),
	INVALID_REQUEST("Invalid request."),
	ACCESS_DENIED("User is not allowed"),
	UNREGISTERED_USER("User is not registered"),
	INCOMPLETE_REQUEST("Incomplete request"),
	BAD_REQUEST("Bad request")

	String errorMessage
	String errorCode

	ApiError(String errorMessage) {
		this.errorCode = name()
		this.errorMessage = errorMessage
	}

	String toString() {
		return errorCode
	}
}