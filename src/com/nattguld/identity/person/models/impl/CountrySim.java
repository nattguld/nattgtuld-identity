package com.nattguld.identity.person.models.impl;

import com.nattguld.util.locale.models.CountryDataModel;
import com.nattguld.util.maths.Maths;

/**
 * 
 * @author randqm
 *
 */

public class CountrySim extends CountryDataModel {
	
	/**
	 * The mobile country code.
	 */
	private final String mcc;
	
	/**
	 * the mobile network codes.
	 */
	private final String[] mncs;
	
	
	/**
	 * Creates a new human heights model.
	 * 
	 * @param countryCode The country code.
	 * 
	 * @param countryName The country name.
	 * 
	 * @param mcc The mobile country code.
	 * 
	 * @param mncs The mobile network codes.
	 */
	public CountrySim(String countryCode, String countryName, String mcc, String[] mncs) {
		super(countryCode, countryName);
		
		this.mcc = mcc;
		this.mncs = mncs;
	}
	
	/**
	 * Retrieves the mobile country code.
	 * 
	 * @return The mobile country code.
	 */
	public String getMcc() {
		return mcc;
	}
	
	/**
	 * Retrieves the mobile network codes.
	 * 
	 * @return The mobile network codes.
	 */
	public String[] getMncs() {
		return mncs;
	}
	
	/**
	 * Retrieves a random mobile network code.
	 * 
	 * @return The random mobile network code.
	 */
	public String getRandomMnc() {
		return getMncs()[Maths.random(getMncs().length)];
	}

}
