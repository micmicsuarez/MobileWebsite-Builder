package com.codeSmile.proMobileSiteBuilder

import com.codeSmile.proMobileSiteBuilder.type.Country
import com.codeSmile.proMobileSiteBuilder.dto.AddressDto
import com.codeSmile.proMobileSiteBuilder.dto.UserInfoDto
import com.codeSmile.proMobileSiteBuilder.type.UserStatus
import com.codeSmile.proMobileSiteBuilder.exception.ValidationObjectException

import grails.test.mixin.TestFor
import spock.lang.Specification
import grails.buildtestdata.mixin.Build

@TestFor(UserInfoService)
@Build([UserInfo, Address])
class UserInfoServiceSpec extends Specification {

	AddressService addressService = Mock()

	def  setup() {	
		service.addressService = addressService
		User.metaClass.encodePassword = {}
	}

	def cleanup() {
		GroovySystem.metaClassRegistry.removeMetaClass(User)
	}

	void "create method should create a user info object"() {
		given:
		AddressDto addressDto = new AddressDto("Mandaue St.", null, null, "Mandaue", Country.PH)
		UserInfoDto userInfoDto = new UserInfoDto( 
			firstName: "micmic", 
			lastName: "lastName",  
			middleName: "middleName", 
			emailAddress: "micmicsuarez@gmail.com", 
			addressDto:addressDto
		)
		addressService.create(userInfoDto.addressDto) >> Address.build(
			addressLineOne: addressDto.addressLineOne,
			city: addressDto.city,
			country: addressDto.country
		)

		User user = User.build(username: "micmicsuarez", password: "password", status: UserStatus.ACTIVE)

		when:
		def userInfo = service.create(userInfoDto, user)

		then:
		userInfo != null
		userInfo.firstName == userInfoDto.firstName
	}

	void "create method should throw an exception when emailAddress is not unique"() {
		given:
		AddressDto addressDto = new AddressDto("Mandaue St.", null, null, "Mandaue", Country.PH)
		UserInfoDto userInfoDto = new UserInfoDto( 
			firstName: "micmic", 
			lastName: "lastName",  
			middleName: "middleName", 
			emailAddress: "micmicsuarez@gmail.com", 
			addressDto:addressDto
		)
		addressService.create(userInfoDto.addressDto) >> Address.build(
			addressLineOne: addressDto.addressLineOne,
			city: addressDto.city,
			country: addressDto.country
		)

		User user = User.build(username: "micmicsuarez", password: "password", status: UserStatus.ACTIVE)
		UserInfo otherUserInfo = UserInfo.build(firstName: "michael", emailAddress: "micmicsuarez@gmail.com")

		when:
		def userInfo = service.create(userInfoDto, user)

		then:
		thrown(ValidationObjectException)
	}

	void "update method should change the user information"() {
		given:
		UserInfoDto userInfoDto = new UserInfoDto(firstName: "micmicsuarez")
		UserInfo userInfo = UserInfo.build(firstName: "michael")

		when:
		service.update(userInfo, userInfoDto)

		then:
		userInfo.firstName == userInfoDto.firstName
	}
}