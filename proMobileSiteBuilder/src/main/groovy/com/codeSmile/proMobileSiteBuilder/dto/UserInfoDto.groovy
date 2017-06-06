package com.codeSmile.proMobileSiteBuilder.dto

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class UserInfoDto {

	private String firstName
	private String lastName
	private String middleName
	private String emailAddress
	private AddressDto addressDto

	public UserInfoDto() {}

	public UserInfoDto(String firstName, String lastName, String middleName, String emailAddress, AddressDto addressDto) {
		this.firstName = firstName
		this.lastName = lastName
		this.middleName = middleName
		this.emailAddress = emailAddress
		this.addressDto = addressDto
	}
}