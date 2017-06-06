package com.codeSmile.proMobileSiteBuilder

import com.codeSmile.proMobileSiteBuilder.dto.AddressDto

import grails.transaction.Transactional

@Transactional
class AddressService {

	public Address create(AddressDto addressDto) {
		Address address = new Address()
		constructAddress(address, addressDto)
		return address.save(flush: true, failOnError: true)
	}

	public void update(Address address, AddressDto addressDto) {
		constructAddress(address, addressDto)
		address.save(flush: true, failOnError: true)
	}

	private Address constructAddress(Address address, AddressDto addressDto) {

		if(addressDto.addressLineOne) {
			address.addressLineOne = addressDto.addressLineOne
		}

		if(addressDto.addressLineTwo) {
			address.addressLineTwo = addressDto.addressLineTwo
		}

		if(addressDto.addressLineThree) {
			address.addressLineThree = addressDto.addressLineThree
		}

		if(addressDto.city) {
			address.city = addressDto.city
		}

		if(addressDto.country) {
			address.country = addressDto.country
		}

		return address
	}
}