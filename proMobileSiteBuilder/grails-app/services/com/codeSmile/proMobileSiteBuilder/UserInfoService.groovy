package com.codeSmile.proMobileSiteBuilder

import com.codeSmile.proMobileSiteBuilder.dto.UserInfoDto
import com.codeSmile.proMobileSiteBuilder.type.ValidationError
import com.codeSmile.proMobileSiteBuilder.exception.ResourceNotFoundException
import com.codeSmile.proMobileSiteBuilder.exception.ValidationObjectException

import grails.transaction.Transactional

@Transactional
class UserInfoService  {

	AddressService addressService

	public UserInfo create(UserInfoDto userInfoDto, User user) {
		
		UserInfo userInfo = new UserInfo()
		userInfo.user = user
		contructUserInfo(userInfo, userInfoDto)
		
		if(userInfoDto.addressDto) {
			userInfo.address = addressService.create(userInfoDto.addressDto)
		} 
		
		if(!emailAddressIsUnique(userInfo.emailAddress)) {
			userInfo.errors.rejectValue('emailAddress', ValidationError.FIELD_SHOULD_BE_UNIQUE.grailsErrorCode)
			throw new ValidationObjectException('EmailAddress is already taken', userInfo)
		}

		return userInfo.save(flush:true, failOnErrors:true)
	}

	private boolean emailAddressIsUnique(String emailAddress) {
		return fetchInfoByEmailAddress(emailAddress) ? false : true
	}

	public void update(UserInfo userInfo, UserInfoDto userInfoDto) {

		contructUserInfo(userInfo, userInfoDto)
		if(userInfoDto.addressDto) {
			userInfo.address = addressService.update(userInfo, userInfoDto)
		}
		userInfo.save(flush: true, failOnErrors: true)
	}

	private UserInfo contructUserInfo(UserInfo userInfo, UserInfoDto userInfoDto) {

		if(userInfoDto.firstName) {
			userInfo.firstName = userInfoDto.firstName
		}

		if(userInfoDto.lastName) {
			userInfo.lastName = userInfoDto.lastName
		}

		if(userInfoDto.middleName) {
			userInfo.middleName = userInfoDto.middleName
		}

		if(userInfoDto.emailAddress) {
			userInfo.emailAddress = userInfoDto.emailAddress
		}

		return userInfo
	}

	public UserInfo fetchInfoByEmailAddress(String emailAddress) {
		return UserInfo.findByEmailAddress(emailAddress)
	}
}