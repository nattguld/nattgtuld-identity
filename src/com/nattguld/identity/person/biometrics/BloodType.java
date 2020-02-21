package com.nattguld.identity.person.biometrics;

import com.nattguld.util.maths.Maths;

/**
 * 
 * @author randqm
 *
 */

public enum BloodType {
	
	O_POSITIVE("O+", 37),
	O_NEGATIVE("O-", 8),
	A_POSTIVIE("A+", 33),
	A_NEGATIVE("A-", 7),
	B_POSITIVE("B+", 9),
	B_NEGATIVE("B-", 2),
	AB_POSITIVE("AB+", 3),
	AB_NEGATIVE("AB-", 1);
	
	
	/**
	 * The name.
	 */
	private final String name;
	
	/**
	 * The occurrence %.
	 */
	private final int occurrence;
	
	
	/**
	 * Creates a new blood type.
	 * 
	 * @param name The name.
	 * 
	 * @param occurrence The occurrence %.
	 */
	private BloodType(String name, int occurrence) {
		this.name = name;
		this.occurrence = occurrence;
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
	 * Retrieves the occurrence.
	 * 
	 * @return The occurrence.
	 */
	public int getOccurrence() {
		return occurrence;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	/**
	 * Retrieves a random blood type.
	 * 
	 * @return The blood type.
	 */
	public static BloodType getRandom() {
		int count = 0;
		int roll = Maths.random(100);
		
		for (BloodType bt : values()) {
			count += bt.getOccurrence();
			
			if (roll <= count) {
				return bt;
			}
		}
		System.err.println("Failed to generate random blood type");
		return BloodType.O_POSITIVE;
	}

}
