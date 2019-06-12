package com.nattguld.identity;

/**
 * 
 * @author randqm
 *
 */

public enum Device {
	
	MOBILE("Mobile", 0),
	DESKTOP("Desktop", 1),
	RESPONSIVE("Responsive", 2);
	
	
	/**
	 * The name.
	 */
	private final String name;
	
	/**
	 * The id.
	 */
	private final int id;
	
	
	/**
	 * Creates a new device.
	 * 
	 * @param name The name.
	 * 
	 * @param id The id.
	 */
	private Device(String name, int id) {
		this.name = name;
		this.id = id;
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
	 * Retrieves the id.
	 * 
	 * @return The id.
	 */
	public int getId() {
		return id;
	}
	
    @Override
    public String toString() {
    	return getName();
    }
	
}
