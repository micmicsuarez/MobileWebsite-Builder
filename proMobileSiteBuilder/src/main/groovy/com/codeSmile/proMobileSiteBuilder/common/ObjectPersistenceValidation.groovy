package com.codeSmile.proMobileSiteBuilder.common

import com.codeSmile.proMobileSiteBuilder.exception.ValidationObjectException

import groovy.util.logging.Log4j
import org.springframework.validation.FieldError
import grails.validation.ValidationException
import org.springframework.validation.ObjectError

@Log4j
public class ObjectPersistenceValidation {


	public def validateAndSave(Object object, Map options = [flush: true] ) {

		try {
			object.save(options)
		} catch (ValidationException e) {
			object.errors.allErrors.each { logError(it)}
			throw new ValidationObjectException("Invalid [$object]", object)
		}
	}

	private void logError(ObjectError objectError) {
		if (error instanceof FieldError) {
			def rejectedValue = error.rejectedValue
			log.debug "FIELD=${error.field}, VALUE=${rejectedValue}, TYPE=[${rejectedValue.getClass()}]"
		} else {
			log.debug " $error unknown"
		}
	}
}
