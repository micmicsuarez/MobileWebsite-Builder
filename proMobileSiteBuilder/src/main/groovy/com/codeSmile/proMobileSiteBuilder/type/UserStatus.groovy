package com.codeSmile.proMobileSiteBuilder.type

public enum UserStatus {
	PENDING,
	ACTIVE,
	INACTIVE,
	DEACTIVATED,
	DELETED

	public static UserStatus findByName(String name) {
		return values().find { name == it.name() }
	}
}
