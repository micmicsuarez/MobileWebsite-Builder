package com.codeSmile.proMobileSiteBuilder

import com.codeSmile.proMobileSiteBuilder.type.UserStatus
import com.codeSmile.proMobileSiteBuilder.dto.UserDto
import com.codeSmile.proMobileSiteBuilder.exception.ResourceNotFoundException
import com.codeSmile.proMobileSiteBuilder.exception.ValidationRequestException
import com.codeSmile.proMobileSiteBuilder.exception.ValidationObjectException
import com.codeSmile.proMobileSiteBuilder.type.ApiError
import com.codeSmile.proMobileSiteBuilder.type.ValidationError
import com.codeSmile.proMobileSiteBuilder.common.ObjectPersistenceValidation

import grails.transaction.Transactional

@Transactional
class UserService  extends ObjectPersistenceValidation {

	UserInfoService userInfoService

	public User create(UserDto userDto) {
		
		User user = new User()
		assignValuesToUser(user, userDto)

		if(userDto.userInfoDto) {
			user.userInfo = userInfoService.create(userDto.userInfoDto, user)
		}

		if(!usernameUnique(userDto.username)) {
			user.errors.rejectValue('username', ValidationError.FIELD_SHOULD_BE_UNIQUE.grailsErrorCode)
			throw new ValidationObjectException('Username is already taken', user)
		}
		
		return validateAndSave(user)
	}

	public void update(User user, UserDto userDto) {

		if(!allowedToUpdate(user)) {
			throw new ValidationRequestException(ApiError.REQUEST_NOT_ALLOWED)
		}

		assignValuesToUser(user, userDto)

		if(userDto.userInfoDto) {
			userInfoService.update(user, userDto)
		}

		user.save(flush: true, failOnError: true)
	}

	private boolean usernameUnique(String username) {
		return User.findByUsername(username) ? false : true
	}

	private boolean allowedToUpdate(User user) {
		return user.status == UserStatus.ACTIVE
	}

	private User assignValuesToUser(User user, UserDto userDto) {

		if(userDto.username) {
			user.username = userDto.username
		}

		if(userDto.password) {
			user.password = userDto.password
		}

		if(userDto.status) {
			user.status = userDto.status
		}

		return user
	}

	public void deactivate(List<User> users) {

		users.each { user ->
			if(user.status == UserStatus.ACTIVE) {
				updateStatus(user, UserStatus.DEACTIVATED)
			} else {
				throw new ValidationRequestException(ApiError.REQUEST_NOT_ALLOWED)
			}
		}
	}

	public void delete(List<User> users) {

		users.each { user ->
			if(user.status == UserStatus.DEACTIVATED) {
				updateStatus(user, UserStatus.DELETED)
			} else {
				throw new ValidationRequestException(ApiError.REQUEST_NOT_ALLOWED)
			}
		}
	}

	private void updateStatus(User user, UserStatus status) {
		user.status = status
		user.save(flush: true, failOnError: true)
	}

	public User fetchById(Long id) {
		User user = User.findById(id)

		if(!user) {
			throw new ResourceNotFoundException()
		}

		return user
	}

	public User fetchUserByUsernameOrEmail(String username) {
		User user = User.findByUsername(username)

		if(!user) {
			UserInfo userInfo = userInfoService.fetchInfoByEmailAddress(username)
			if(userInfo)  user = userInfo.user
		}

		return user
	}

	public List<User> fetchAll(int max=0, int offset=0) {
		return User.findAllByStatusNotEqual(UserStatus.DELETED, [max: max, offset: offset])
	}

	public List<User> fetchActiveUsers(int max=0, int offset=0) {
		return User.findAllByStatus(UserStatus.ACTIVE, [max: max, offset: offset])
	}
}
