package com.nattguld.identity.person.models.impl;

import com.nattguld.util.locale.models.CountryDataModel;

/**
 * 
 * @author randqm
 *
 */

public class GivenNames extends CountryDataModel {

	/**
	 * The given names set name.
	 */
	private final String givenNamesSet;
	
	
	/**
	 * Creates a new human heights model.
	 * 
	 * @param countryCode The country code.
	 * 
	 * @param countryName The country name.
	 * 
	 * @param givenNamesSet The given names set name.
	 */
	public GivenNames(String countryCode, String countryName, String givenNamesSet) {
		super(countryCode, countryName);
		
		this.givenNamesSet = givenNamesSet;
	}
	
	/**
	 * Retrieves the given names set name.
	 * 
	 * @return The given names set name.
	 */
	public String getGivenNamesSet() {
		return givenNamesSet;
	}

}
