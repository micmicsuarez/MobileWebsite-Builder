package com.codeSmile.proMobileSiteBuilder

import com.codeSmile.proMobileSiteBuilder.type.UserStatus
import com.codeSmile.proMobileSiteBuilder.type.ValidationError

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class User  {

	public static final int PASSWORD_MIN_SIZE = 8
	
	transient springSecurityService
	
	UUID id = generateUserId()
	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	UserStatus status = UserStatus.ACTIVE
	UserInfo userInfo

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this)*.role
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	static transients = ['springSecurityService']

	static constraints = {
		password (nullable: true, minSize: PASSWORD_MIN_SIZE, validator: { val, object, errors ->
			if(val != null && val != "" && !(val ==~ /^([a-zA-Z0-9\p{P}\p{S}]+)$/)) {
				errors.rejectValue('password', ValidationError.FIELD_INVALID_PASSWORD.grailsErrorCode)
			}
		})
		username blank: false, unique: true
		userInfo nullable: true
	}

	static mapping = {
		table '`users`'
		password column: '`password`'
		id generator: 'uuid2', type: 'pg-uuid'
	}

	UUID generateUserId() {
		return UUID.randomUUID()
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}
