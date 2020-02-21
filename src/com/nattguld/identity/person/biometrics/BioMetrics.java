package com.nattguld.identity.person.biometrics;

import com.nattguld.util.maths.measurements.impl.MassMeasurementUnit;

/**
 * 
 * @author randqm
 *
 */

public class BioMetrics {
	
	/**
	 * The height in cm.
	 */
	private final double height;
	
	/**
	 * The weight in kg.
	 */
	private final double weight;
	
	/**
	 * The eye color.
	 */
	private final EyeColor eyeColor;
	
	/**
	 * The blood type.
	 */
	private final BloodType bloodType;
	
	
	/**
	 * Creates new biometrics.
	 * 
	 * @param height The height.
	 * 
	 * @param weight The weight.
	 * 
	 * @param eyeColor The eye color.
	 * 
	 * @param bloodType The blood type.
	 */
	public BioMetrics(double height, double weight, EyeColor eyeColor, BloodType bloodType) {
		this.height = height;
		this.weight = weight;
		this.eyeColor = eyeColor;
		this.bloodType = bloodType;
	}
	
	/**
	 * Retrieves the height.
	 * 
	 * @return The height.
	 */
	public double getHeight() {
		return height;
	}
	
	/**
	 * Retrieves the weight.
	 * 
	 * @return The weight.
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * Retrieves the weight using a given measurement unit.
	 * 
	 * @param mUnit the measurement unit.
	 * 
	 * @return The weight.
	 */
	public double getWeight(MassMeasurementUnit mUnit) {
		return MassMeasurementUnit.KILOGRAM.convert(getWeight(), mUnit);
	}
	
	/**
	 * Retrieves the eye color.
	 * 
	 * @return The eye color.
	 */
	public EyeColor getEyeColor() {
		return eyeColor;
	}
	
	/**
	 * Retrieves the blood type.
	 * 
	 * @return The blood type.
	 */
	public BloodType getBloodType() {
		return bloodType;
	}

}
