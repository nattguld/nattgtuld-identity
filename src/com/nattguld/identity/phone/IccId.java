package com.nattguld.identity.phone;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mifmif.common.regex.Generex;
import com.nattguld.util.maths.algorithms.Luhn;

/**
 * 
 * @author randqm
 *
 */

public class IccId {
	
	/**
	 * The number types.
	 */
	private static final int[] NUMBER_TYPES = {
		89 //Telecom	
	};
	
	/**
	 * The telecom data.
	 */
	private static final int[][] TELECOM_DATA = new int[][] {
		//Country code, ISP code range
		{1, 480, 489}
	};
	
	
	/**
	 * Generates a random IccId number.
	 * 
	 * @return The number.
	 */
	public static String generate() {
		// 89       = telecom
		// 1        = united states
		// [480-489] = verizon
		// {13}     = sim account
		// {1}      = luhn check digit
		String regex = "(89)(1)(48[0-9])(\\d{13})";
		Generex generex = new Generex(regex);
		String randomStr = generex.random();
		
	    int checksumDigit = Luhn.generateChecksumDigit(randomStr);
		return randomStr + checksumDigit;
	}
	 
	/**
	 * Retrieves whether a given sim number is valid or not.
	 * 
	 * @param simId The sim number.
	 * 
	 * @return The result.
	 */
	public static boolean isValidSim(String simId) {
		String regex = "^(89)(1)(48[0-9])(\\d{13})(\\d)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(simId);
		
		if (!matcher.find()) {
			System.err.println("Invalid number");
			return false;
		}
		if (!Luhn.isValid(simId)) {
			System.out.println("Luhn is not valid");
			return false;
		}
		return true;
	}
	
}
