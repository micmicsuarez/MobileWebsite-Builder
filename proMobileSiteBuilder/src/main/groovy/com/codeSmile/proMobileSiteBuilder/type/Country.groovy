package com.codeSmile.proMobileSiteBuilder.type

public enum Country {
	USA("United States"),
	AU("Australia"),
	PH("Philippines")

	String country

	public Country(String country) {
		this.country = country
	}

	public static Country valueOfCountry(String country) {
		values().find {
			it.country == country
		}
	}
}