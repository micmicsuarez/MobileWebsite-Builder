package com.codeSmile.proMobileSiteBuilder

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class UserInfo {

	String firstName
	String lastName
	String middleName
	String emailAddress
	Address address

	static belongsTo = [user: User]

	static mapping = {
		id generator:'sequence', column:'id', params:[sequence:'user_info_sequence']
	}

	static constraints = {
		firstName nullable: true
		lastName nullable: true
		middleName nullable: true
		emailAddress nullable: true
		address nullable: true
	}
}
