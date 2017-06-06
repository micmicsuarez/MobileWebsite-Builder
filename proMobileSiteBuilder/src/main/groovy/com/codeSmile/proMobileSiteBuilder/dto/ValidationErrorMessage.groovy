package com.codeSmile.proMobileSiteBuilder.dto

import  com.codeSmile.proMobileSiteBuilder.type.ValidationError

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
public class ValidationErrorMessage {

	public ValidationErrorMessage(ValidationError validationError, String field) {
		this.validationError = validationError
		this.field = field
	}

	public ValidationErrorMessage(ValidationError validationError) {
		this.validationError = validationError
		this.field = null
	}

	String field
	ValidationError validationError
	Map<String, String> params

	public Map toMap() {
		return [field: field?: ' ',
				errorCode: validationError.errorCode,
				params: params?: ' ',
				errorMessage: validationError.errorMessage]
	}
}