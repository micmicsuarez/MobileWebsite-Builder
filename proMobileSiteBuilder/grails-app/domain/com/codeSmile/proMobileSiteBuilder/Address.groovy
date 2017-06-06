package com.codeSmile.proMobileSiteBuilder

import com.codeSmile.proMobileSiteBuilder.type.Country

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Address {

	String addressLineOne
	String addressLineTwo
	String addressLineThree
	String city
	Country country

	static mapping = {
		id generator:'sequence', column:'id', params:[sequence:'address_sequence']
	}
	
	static constraints = {
		addressLineTwo nullable: true
		addressLineThree nullable: true
	}
}
