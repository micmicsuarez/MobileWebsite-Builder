package com.codeSmile.proMobileSiteBuilder.dto

import com.codeSmile.proMobileSiteBuilder.type.Country
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class AddressDto {

	String addressLineOne
	String addressLineTwo
	String addressLineThree
	String city
	Country country

	public AddressDto() {}

	public AddressDto(String addressLineOne, String addressLineTwo, String addressLineThree,  String city, Country country) {
		this.addressLineOne = addressLineOne
		this.addressLineTwo = addressLineTwo
		this.addressLineThree = addressLineThree
		this.city = city
		this.country = country
	}
}