package com.nattguld.identity.person.models.impl;

import com.nattguld.util.locale.models.CountryDataModel;

/**
 * 
 * @author randqm
 *
 */

public class HumanHeights extends CountryDataModel {

	/**
	 * The average height for a male in centimeter.
	 */
	private final double maleCm;
	
	/**
	 * The average height for a female in centimeter.
	 */
	private final double femaleCm;
	
	
	/**
	 * Creates a new human heights model.
	 * 
	 * @param countryCode The country code.
	 * 
	 * @param countryName The country name.
	 * 
	 * @param maleCm The average height for a male in centimeter.
	 * 
	 * @param femaleCm The average height for a female in centimeter.
	 */
	public HumanHeights(String countryCode, String countryName, double maleCm, double femaleCm) {
		super(countryCode, countryName);
		
		this.maleCm = maleCm;
		this.femaleCm = femaleCm;
	}
	
	/**
	 * Retrieves the average height for a male in centimeter.
	 * 
	 * @return The average height for a male in centimeter.
	 */
	public double getMaleCm() {
		return maleCm;
	}
	
	/**
	 * Retrieves the average height for a female in centimeter.
	 * 
	 * @return The average height for a female in centimeter.
	 */
	public double getFemaleCm() {
		return femaleCm;
	}

}
