package com.codeSmile.proMobileSiteBuilder.dto

import  com.codeSmile.proMobileSiteBuilder.type.UserStatus
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class UserDto {

	private String username
	private String password
	private UserStatus status
	private UserInfoDto userInfoDto

	public UserDto() {}

	public UserDto(String username, String password, UserStatus status, UserInfoDto userInfoDto) {
		this.username = username
		this.password = password
		this.status = status
		this.userInfoDto = userInfoDto
	}
}