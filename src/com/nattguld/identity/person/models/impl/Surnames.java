package com.nattguld.identity.person.models.impl;

import com.nattguld.util.locale.models.CountryDataModel;

/**
 * 
 * @author randqm
 *
 */

public class Surnames extends CountryDataModel {

	/**
	 * The surnames.
	 */
	private final String[] surnames;
	
	
	/**
	 * Creates a new human heights model.
	 * 
	 * @param countryCode The country code.
	 * 
	 * @param countryName The country name.
	 * 
	 * @param surnames The surnames.
	 */
	public Surnames(String countryCode, String countryName, String[] surnames) {
		super(countryCode, countryName);
		
		this.surnames = surnames;
	}
	
	/**
	 * Retrieves the surnames.
	 * 
	 * @return The surnames.
	 */
	public String[] getSurnames() {
		return surnames;
	}

}
