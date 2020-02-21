package com.nattguld.identity.person;

import java.util.Objects;

import com.mifmif.common.regex.Generex;
import com.nattguld.identity.Sex;
import com.nattguld.identity.person.models.DataModelsManager;
import com.nattguld.identity.person.models.impl.GivenNames;
import com.nattguld.identity.person.models.impl.GivenNamesSet;
import com.nattguld.identity.person.models.impl.Surnames;
import com.nattguld.util.generics.kvps.impl.StringKeyValuePair;
import com.nattguld.util.locale.Country;
import com.nattguld.util.maths.Maths;
import com.nattguld.util.text.TextSeed;
import com.nattguld.util.text.TextUtil;

/**
 * 
 * @author randqm
 *
 */

public class CredentialsHandler {
	
	
	/**
	 * Retrieves name credentials for a given sex and country.
	 * 
	 * @param sex The sex.
	 * 
	 * @param country The country.
	 * 
	 * @return The name credentials.
	 */
	public static StringKeyValuePair getNameCredentials(Sex sex, Country country) {
		return new StringKeyValuePair(getGivenName(sex, country), getSurname(country));
	}
	
	/**
	 * Retrieves a full name as string.
	 * 
	 * @param sex The sex.
	 * 
	 * @param country The country.
	 * 
	 * @return The full name.
	 */
	public static String getFullNameAsString(Sex sex, Country country) {
		StringKeyValuePair creds = getNameCredentials(sex, country);
		return creds.getKey() + " " + creds.getValue();
	}
	
	/**
	 * Retrieves a given name for a given sex and country.
	 * 
	 * @param sex The sex.
	 * 
	 * @param country The country.
	 * 
	 * @return The given name.
	 */
	public static String getGivenName(Sex sex, Country country) {
		GivenNames givenNames = DataModelsManager.getGivenNames(country);
		
		if (Objects.isNull(givenNames)) {
			System.err.println("Failed to find given names for " + country.getName());
			return sex == Sex.FEMALE ? "Lisa" : "John";
		}
		String setName = givenNames.getGivenNamesSet();
		
		if (setName.isEmpty()) {
			System.err.println("Failed to find set name for " + country.getName());
			return sex == Sex.FEMALE ? "Lisa" : "John";
		}
		GivenNamesSet givenNamesSet = DataModelsManager.getGivenNamesSet(setName);
		
		if (Objects.isNull(givenNamesSet)) {
			System.err.println("Failed to find given names set for " + setName);
			return sex == Sex.FEMALE ? "Lisa" : "John";
		}
		String[] options = sex == Sex.FEMALE ? givenNamesSet.getFemaleGivenNames() : givenNamesSet.getMaleGivenNames();
		
		if (options.length <= 0) {
			System.err.println("Failed to find " + sex.getName() + " names in given names set: " + setName);
			return sex == Sex.FEMALE ? "Lisa" : "John";
		}
		return options[Maths.random(options.length)];
	}
	
	/**
	 * Retrieves a surname for a given country.
	 * 
	 * @param country The country.
	 * 
	 * @return The surname.
	 */
	public static String getSurname(Country country) {
		Surnames surnames = DataModelsManager.getSurnames(country);
		
		if (Objects.isNull(surnames)) {
			System.err.println("Failed to find surname for " + country.getName());
			return "Smith";
		}
		String[] options = surnames.getSurnames();
		
		if (options.length <= 0) {
			System.err.println("No surname options for " + country.getName());
			return "Smith";
		}
		return options[Maths.random(options.length)];
	}
	
	/**
	 * Generates and retrieves online credentials.
	 * 
	 * @param nameCreds The name credentials.
	 * 
	 * @param birthYear The birth year.
	 * 
	 * @return The online credentials.
	 */
	public static StringKeyValuePair generateOnlineCredentials(String givenName, String surname, int birthYear) {
		String username = new Generex("([A-Z]{1})([aeiou]{1,2})([a-z]{1,2})([aeiou]{1,2})([a-z]{1,2})([aeiou]{1,2})([a-z]{1,2})").random();
		String password = TextUtil.generatePassword();
		
		if (Maths.random(2) == 1) {
			int randomizationAttempts = 0;
			
			while (randomizationAttempts < 5) {
				String randomizedUsername = randomizeUsername(username, givenName, surname, birthYear);
				
				if (isValidUsername(randomizedUsername)) {
					username = randomizedUsername;
					break;
				}
				randomizationAttempts++;
			}
		}
		return new StringKeyValuePair(username, password);
	}
	
	/**
	 * Randomizes a given username.
	 * 
	 * @param firstName The given name.
	 * 
	 * @param lastName The surname.
	 * 
	 * @param birthYear The birth year.
	 * 
	 * @return The randomized username.
	 */
	public static String randomizeUsername(String originalUsername, String firstName, String lastName, int birthYear) {
		String username = firstName + lastName + Maths.random(999);
		String rndStr = TextUtil.randomString(2, 5, TextSeed.LOWERCASE);
		String birthYear2d = Integer.toString(birthYear).substring(2, 4);
		//Generate/format username
		int roll = Maths.random(8);
		
		switch (roll) {
		case 0:
			username = Maths.random(1) == 0 ? (firstName + lastName) : (lastName + firstName);
			break;
			
		case 1:
			username = Maths.random(1) == 0 ? (firstName + lastName + birthYear2d) : (lastName + firstName + birthYear2d);
			break;
			
		case 2:
			username = Maths.random(1) == 0 ? (lastName + birthYear2d) : (firstName + birthYear2d);
			break;
			
		case 3:
			username = Maths.random(1) == 0 ? (firstName + birthYear2d + lastName) : (lastName + birthYear2d + firstName);
			break;
			
		case 4:
			username = Maths.random(1) == 0 ? (firstName + rndStr) : (rndStr + firstName);
			break;
			
		case 5:
			username = Maths.random(1) == 0 ? (lastName + rndStr) : (rndStr + lastName);
			break;
			
		case 6:
			username = Maths.random(1) == 0 ? (lastName + rndStr + birthYear2d) : (rndStr + lastName + birthYear2d);
			break;
			
		case 7:
			username = Maths.random(1) == 0 ? (firstName + rndStr + birthYear2d) : (rndStr + firstName + birthYear2d);
			break;
			
		case 8:
			username = Maths.random(1) == 0 ? (firstName + lastName + rndStr) : (lastName + firstName + rndStr);
			break;
			
		case 9:
			username = Maths.random(1) == 0 ? (rndStr + firstName + lastName) : (rndStr + lastName + firstName);
			break;
			
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
			username = originalUsername;
			break;
		}
		if (username.length() > 18) {
			username = username.substring(0, 18);
		}
		if (username.length() < 6 && username.length() >= 3) {
			username += (100 + Maths.random(899));
		}
		return username;
	}
	
	/**
	 * Retrieves whether a given username is valid or not.
	 * 
	 * @param username The username.
	 * 
	 * @return The result.
	 */
	public static boolean isValidUsername(String username) {
		if (username.length() < 3) {
			System.err.println("Username below 3 characters, re-generating");
			return false;
		}
		if (!username.matches("[A-Za-z0-9]+")) {
			System.err.println("Username is not alphanumerc => " + username);
			return false;
		}
		if (Character.isDigit(username.toCharArray()[0])) {
			System.err.println("Username starts with digit");
			return false;
		}
		return true;
	}

}
