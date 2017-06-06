package com.codeSmile.proMobileSiteBuilder

import com.codeSmile.proMobileSiteBuilder.dto.AddressDto
import com.codeSmile.proMobileSiteBuilder.dto.UserDto
import com.codeSmile.proMobileSiteBuilder.dto.UserInfoDto

import com.codeSmile.proMobileSiteBuilder.type.Country
import com.codeSmile.proMobileSiteBuilder.type.UserStatus

import  com.codeSmile.proMobileSiteBuilder.exception.ValidationRequestException
import com.codeSmile.proMobileSiteBuilder.exception.ResourceNotFoundException
import com.codeSmile.proMobileSiteBuilder.exception.ValidationObjectException

import grails.test.mixin.TestFor
import spock.lang.Specification
import grails.buildtestdata.mixin.Build

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(UserService)
@Build([User, UserInfo, Address])
class UserServiceSpec extends Specification {

	UserInfoService userInfoService = Mock()

	def setup() {
		service.userInfoService = userInfoService
		User.metaClass.encodePassword = {}
	}

	def cleanup() {
		GroovySystem.metaClassRegistry.removeMetaClass(User)
	}

	void "create method should create a new user"() {
		given:
		AddressDto addressDto = new AddressDto("Mandaue St.", null, null, "Mandaue", Country.PH)
		UserInfoDto userInfoDto = new UserInfoDto("Michael", "Suarez", "Dominguez", "micmic.suarez@gmail.com", addressDto)
		UserDto userDto = new UserDto("micmicsuarez", "password",  null, userInfoDto)

		userInfoService.create(userInfoDto, _ as User) >> UserInfo.build (
			firstName: userInfoDto.firstName, 
			lastName: userInfoDto.lastName,
			middleName: userInfoDto.middleName,
			emailAddress: userInfoDto.emailAddress,
			address:  Address.build ( 
				addressLineOne: userInfoDto.addressDto.addressLineOne,
				city: userInfoDto.addressDto.city,
				country: userInfoDto.addressDto.country
			)
		)

		when:
		def createdUser = service.create(userDto)

		then:
		createdUser != null
	}

	void "create method should create a new user that contains the exact values from UserDto, UserInfoDto, and AddressDto"() {
		given:
		AddressDto addressDto = new AddressDto("Mandaue St.", null, null, "Mandaue", Country.PH)
		UserInfoDto userInfoDto = new UserInfoDto("Michael", "Suarez", "Dominguez", "micmic.suarez@gmail.com", addressDto)
		UserDto userDto = new UserDto("micmicsuarez", "password",  null, userInfoDto)

		userInfoService.create(userInfoDto, _ as User) >> UserInfo.build (
			firstName: userInfoDto.firstName, 
			lastName: userInfoDto.lastName,
			middleName: userInfoDto.middleName,
			emailAddress: userInfoDto.emailAddress,
			address:  Address.build ( 
				addressLineOne: userInfoDto.addressDto.addressLineOne,
				city: userInfoDto.addressDto.city,
				country: userInfoDto.addressDto.country
			)
		)

		when:
		def createdUser = service.create(userDto)

		then:
		createdUser.username == userDto.username
		createdUser.password == userDto.password
		createdUser.userInfo.firstName == userInfoDto.firstName
		createdUser.userInfo.middleName == userInfoDto.middleName
		createdUser.userInfo.lastName == userInfoDto.lastName
		createdUser.userInfo.address.addressLineOne == addressDto.addressLineOne
		createdUser.userInfo.address.addressLineTwo == null
		createdUser.userInfo.address.addressLineThree == null
		createdUser.userInfo.address.city == addressDto.city
		createdUser.userInfo.address.country == addressDto.country
	}

	void "create method should throw an exception when username is not unique"() {
		given:
		AddressDto addressDto = new AddressDto("Mandaue St.", null, null, "Mandaue", Country.PH)
		UserInfoDto userInfoDto = new UserInfoDto("Michael", "Suarez", "Dominguez", "micmic.suarez@gmail.com", addressDto)
		UserDto userDto = new UserDto("micmicsuarez", "password",  null, userInfoDto)

		User user = User.build(username: "micmicsuarez", password: "password", status: UserStatus.ACTIVE)

		userInfoService.create(userInfoDto, _ as User) >> UserInfo.build (
			firstName: userInfoDto.firstName, 
			lastName: userInfoDto.lastName,
			middleName: userInfoDto.middleName,
			emailAddress: userInfoDto.emailAddress,
			address:  Address.build ( 
				addressLineOne: userInfoDto.addressDto.addressLineOne,
				city: userInfoDto.addressDto.city,
				country: userInfoDto.addressDto.country
			)
		)

		when:
		service.create(userDto)

		then:
		thrown(ValidationObjectException)
	}

	void "update method should update the user's data when status is ACTIVE"() {
		given:
		UserDto userDto = new UserDto("michael", null,  null, null)
		User user = User.build(username: "micmicsuarez", password: "password", status: UserStatus.ACTIVE)

		when:
		service.update(user, userDto)

		then:
		user.username == userDto.username
	}

	void "update method should thrown an exception when the status of user is not ACTIVE"() {
		given:
		UserDto userDto = new UserDto("michael", null,  null, null)
		User user = User.build(username: "micmicsuarez", password: "password", status: UserStatus.DEACTIVATED)

		when:
		service.update(user, userDto)

		then:
		thrown(ValidationRequestException)
	}

	void "deactivate method should allow to deactivate user/s with status ACTIVE"() {
		given:
		User firstUser = User.build(username: "micmicsuarez", password: "password", status: UserStatus.ACTIVE)
		User secondUser = User.build(username: "michael", password: "password", status: UserStatus.ACTIVE)
		List users = [firstUser, secondUser]

		when:
		service.deactivate(users)

		then:
		firstUser.status == UserStatus.DEACTIVATED
		secondUser.status == UserStatus.DEACTIVATED
	}

	void "deactivate method should throw an exception when user/s status is not ACTIVE"() {
		given:
		User firstUser = User.build(username: "micmicsuarez", password: "password", status: UserStatus.DELETED)
		User secondUser = User.build(username: "michael", password: "password", status: UserStatus.ACTIVE)
		List users = [firstUser, secondUser]

		when:
		service.deactivate(users)

		then:
		thrown(ValidationRequestException)
	}

	void "delete method should allow the deletion of user/s if status is DEACTIVATED"() {
		given:
		User firstUser = User.build(username: "micmicsuarez", password: "password", status: UserStatus.DEACTIVATED)
		User secondUser = User.build(username: "michael", password: "password", status: UserStatus.DEACTIVATED)
		List users = [firstUser, secondUser]

		when:
		service.delete(users)

		then:
		firstUser.status == UserStatus.DELETED
		secondUser.status == UserStatus.DELETED
	}

	void "delete method should throw an exception when user/s status is not DEACTIVATED"() {
		given:
		User firstUser = User.build(username: "micmicsuarez", password: "password", status: UserStatus.DEACTIVATED)
		User secondUser = User.build(username: "michael", password: "password", status: UserStatus.ACTIVE)
		List users = [firstUser, secondUser]

		when:
		service.delete(users)

		then:
		thrown(ValidationRequestException)
	}

	void "fetchUserByUsernameOrEmail should fetch user given the username"() {
		given:
		String username = "micmicsuarez"
		String password = "password"
		User user = User.build(username: username, password: password, status: UserStatus.ACTIVE)

		when:
		def currentUser = service.fetchUserByUsernameOrEmail(username)

		then:
		currentUser != null
		currentUser.username == username
	}

	void "fetchUserByUsernameOrEmail should fetch user given the emailAddress"() {
		given:
		String username = "micmicsuarez"
		String password = "password"
		String emailAddress = "micmicsuarez@gmail.com"
		User user = User.build(username: username, password: password, status: UserStatus.ACTIVE)

		userInfoService.fetchInfoByEmailAddress(emailAddress) >> UserInfo.build(user:user, emailAddress: emailAddress)

		when:
		def currentUser = service.fetchUserByUsernameOrEmail(emailAddress)

		then:
		currentUser != null
		currentUser.username == username
		currentUser.userInfo.emailAddress == emailAddress
	}
}
