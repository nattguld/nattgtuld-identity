package com.nattguld.identity.person.models.impl;

/**
 * 
 * @author randqm
 *
 */

public class GivenNamesSet {

	/**
	 * The set name.
	 */
	private final String setName;
	
	/**
	 * The male given names.
	 */
	private final String[] maleGivenNames;
	
	/**
	 * The female given names.
	 */
	private final String[] femaleGivenNames;
	
	
	/**
	 * Creates a new given names set model.
	 * 
	 * @param setName The set name.
	 * 
	 * @param male The male given names.
	 * 
	 * @param femaleGivenNames The female given names.
	 */
	public GivenNamesSet(String setName, String[] maleGivenNames, String[] femaleGivenNames) {
		this.setName = setName;
		this.maleGivenNames = maleGivenNames;
		this.femaleGivenNames = femaleGivenNames;
	}
	
	/**
	 * Retrieves the set name.
	 * 
	 * @return The set name.
	 */
	public String getSetName() {
		return setName;
	}
	
	/**
	 * Retrieves the male given names.
	 * 
	 * @return The male given names.
	 */
	public String[] getMaleGivenNames() {
		return maleGivenNames;
	}
	
	/**
	 * Retrieves the female names.
	 * 
	 * @return The female names.
	 */
	public String[] getFemaleGivenNames() {
		return femaleGivenNames;
	}

}
