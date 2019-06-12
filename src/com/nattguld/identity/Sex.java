package com.nattguld.identity;

/**
 * 
 * @author randqm
 *
 */

public enum Sex {
	
	MALE("Male"),
	FEMALE("Female"),
	TRANSSEXUAL("Transsexual");
	
	
	/**
	 * The name.
	 */
	private final String name;
	
	
	/**
	 * Creates a new sex.
	 * 
	 * @param name The name.
	 */
	private Sex(String name) {
		this.name = name;
	}
	
	/**
	 * Retrieves the name.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}
