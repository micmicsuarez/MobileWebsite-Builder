package com.codeSmile.proMobileSiteBuilder

import com.codeSmile.proMobileSiteBuilder.type.Country

class LoginController  {

	static responseFormats = ['json']
	
	def login() {
		respond (new Address(addressLineOne: "haha", country: Country.PH))
	}
}