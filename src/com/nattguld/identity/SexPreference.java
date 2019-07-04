package com.nattguld.identity;

/**
 * 
 * @author randqm
 *
 */

public enum SexPreference {
	
	MALE("Male", new Sex[] {Sex.MALE}),
	FEMALE("Female", new Sex[] {Sex.FEMALE}),
	MIX("Mix", new Sex[] {Sex.MALE, Sex.FEMALE});
	
	
	/**
	 * The name.
	 */
	private final String name;
	
	/**
	 * The sexes.
	 */
	private final Sex[] sexes;
	
	
	/**
	 * Creates a new sex preference.
	 * 
	 * @param name The name.
	 * 
	 * @param sexes The sexes.
	 */
	private SexPreference(String name, Sex[] sexes) {
		this.name = name;
		this.sexes = sexes;
	}
	
	/**
	 * Retrieves the name.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Retrieves the sexes.
	 * 
	 * @return The sexes.
	 */
	public Sex[] getSexes() {
		return sexes;
	}
	
	@Override
	public String toString() {
		return getName();
	}

}
