package com.codeSmile.proMobileSiteBuilder

import com.codeSmile.proMobileSiteBuilder.type.AuthenticationTokenStatus
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class AuthenticationToken {

	String token
	User user
	Date lastUsed
	Date dateCreated
	AuthenticationTokenStatus status = AuthenticationTokenStatus.ACTIVE

	static constraints = {
		token nullable: false, unique: true
		user nullable: false
		lastUsed nullable: true
	}

	static mapping = {
		id generator:'sequence', column:'id', params:[sequence:'authentication_token_sequence']
		version false
	}
}