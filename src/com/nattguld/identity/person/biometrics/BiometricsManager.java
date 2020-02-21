package com.nattguld.identity.person.biometrics;

import com.nattguld.identity.Sex;
import com.nattguld.identity.person.models.DataModelsManager;
import com.nattguld.identity.person.models.impl.HumanHeights;
import com.nattguld.util.locale.Country;
import com.nattguld.util.maths.DoubleRange;
import com.nattguld.util.maths.Maths;
import com.nattguld.util.maths.measurements.impl.HeightMeasurementUnit;

/**
 * 
 * @author randqm
 *
 */

public class BiometricsManager {
	
	
	/**
	 * Generates random biometrics for a given country and sex.
	 * 
	 * @param country The country.
	 * 
	 * @param sex The sex.
	 * 
	 * @return The biometrics.
	 */
	public static BioMetrics generateRandom(Country country, Sex sex) {
		EyeColor eyeColor = EyeColor.values()[Maths.random(EyeColor.values().length)]; //TODO by country
		BloodType bloodType = BloodType.getRandom();
		
		HumanHeights hh = DataModelsManager.getHumanHeights(country);
		double avgHeight = sex == Sex.FEMALE ? hh.getFemaleCm() : hh.getMaleCm();
		double height = avgHeight * new DoubleRange(0.925, 1.025).getRandom();
		
		int[] ftAndIn = HeightMeasurementUnit.CENTIMETER.toFeetAndInches(height);
		double recWeight = getRecommendedWeight(sex, HeightMeasurementUnit.feetAndInchesToInches(ftAndIn[0], ftAndIn[1]));
		double weight = recWeight * new DoubleRange(0.9, 1.1).getRandom();
		
		return new BioMetrics(height, weight, eyeColor, bloodType);
	}
	
	/**
	 * Retrieves the recommended weight for a given sex and height in inches.
	 * 
	 * @param sex The sex.
	 * 
	 * @param inchesHeight The height in inches.
	 * 
	 * @return The recommended weight.
	 */
	public static double getRecommendedWeight(Sex sex, int inchesHeight) {
		double baseKg = sex == Sex.FEMALE ? 53.1 : 56.2;
		
		int inches = inchesHeight - (5 * 12);
		
		if (inches < 0) {
			return baseKg;
		}
		double recWeight = ((sex == Sex.FEMALE ? 1.36 : 1.41) * (double)inches) + baseKg;
		//Male:	56.2 kg + 1.41 kg per inch over 5 feet
		//Female:	53.1 kg + 1.36 kg per inch over 5 feet
		return recWeight;
	}

}
