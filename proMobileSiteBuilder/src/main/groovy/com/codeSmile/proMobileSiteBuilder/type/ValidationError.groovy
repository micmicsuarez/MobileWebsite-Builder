package com.codeSmile.proMobileSiteBuilder.type

public enum ValidationError {

	FIELD_NOT_NULLABLE("nullable", "Field must not be null"),
	FIELD_SHOULD_BE_UNIQUE("unique", "Field is already taken, please try another"),
	FIELD_INVALID_PASSWORD("minimum", "Invalid length of password ")

	String grailsErrorCode
	String errorMessage
	String errorCode

	ValidationError(String grailsErrorCode, String errorMessage) {
		this.grailsErrorCode = grailsErrorCode
		this.errorCode = name()
		this.errorMessage = errorMessage
	}

	static ValidationError valueOfCode(String grailsErrorCode) {
		values().find { it.grailsErrorCode == grailsErrorCode}
	}

	String toString() {
		return errorCode
	}
}