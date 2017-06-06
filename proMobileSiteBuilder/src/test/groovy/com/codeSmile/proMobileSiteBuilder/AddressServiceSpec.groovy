package com.codeSmile.proMobileSiteBuilder

import com.codeSmile.proMobileSiteBuilder.dto.AddressDto
import com.codeSmile.proMobileSiteBuilder.type.Country

import grails.test.mixin.TestFor
import spock.lang.Specification
import grails.buildtestdata.mixin.Build

@TestFor(AddressService)
@Build([Address])
class AddressServiceSpec extends Specification {

	def  setup() {	
	}

	def cleanup() {

	}

	void "create method should create an address for the user"() {
		given:
		AddressDto addressDto = new AddressDto(
			addressLineOne: "Brg. Cambaro", 
			city: "Manduade",
			country: Country.PH
		)

		when:
		Address address = service.create(addressDto)

		then:
		address != null
		address.addressLineOne == addressDto.addressLineOne
		address.city == addressDto.city
	}

	void "update method should update the address information of the user"() {
		given:
		AddressDto addressDto = new AddressDto(
			addressLineOne: "Mabolo", 
			city: "Cebu",
			country: Country.PH
		)

		Address address = Address.build(
			addressLineOne: "Brg. Cambaro",
			city: "Manduade",
			country: Country.PH
		)

		when:
		service.update(address, addressDto)

		then:
		address.addressLineOne == addressDto.addressLineOne
		address.city == addressDto.city
	}
}