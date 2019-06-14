package com.nattguld.identity;

import com.nattguld.identity.person.Person;
import com.nattguld.util.locale.Country;
import com.nattguld.util.maths.Maths;
import com.nattguld.util.maths.Range;

/**
 * 
 * @author randqm
 *
 */

public class IdentityHandler {

	
	/**
	 * The available western countries.
	 */
	public static final Country[] WESTERN_COUNTRIES = new Country[] {
			Country.CANADA, Country.UNITED_STATES, Country.ICELAND, Country.NORWAY, Country.SWEDEN, Country.FINLAND
			, Country.DENMARK, Country.IRELAND, Country.UNITED_KINGDOM, Country.NETHERLANDS, Country.BELGIUM
			, Country.LUXEMBOURG, Country.GERMANY, Country.AUSTRIA, Country.SWITZERLAND, Country.FRANCE
			, Country.SPAIN, Country.PORTUGAL, Country.ITALY, Country.GREECE, Country.AUSTRALIA
			, Country.NEW_ZEALAND, Country.SOUTH_AFRICA
	};
	
	/**
	 * The available majorly English speaking countries.
	 */
	public static final Country[] ENGLISH_COUNTRIES = new Country[] {
			Country.CANADA, Country.UNITED_KINGDOM, Country.UNITED_STATES, Country.IRELAND
			, Country.AUSTRALIA, Country.NEW_ZEALAND
	};
	
	/**
	 * The default age range.
	 */
	private static final Range DEFAULT_AGE_RANGE = new Range(18, 30);
	
	
	/**
	 * Generates a person with a given country.
	 * 
	 * @param country The country.
	 * 
	 * @return The person.
	 */
	public static Person generatePerson(Country country) {
		return generatePerson(getRandomSex(), country);
	}
	
	/**
	 * Generates a person with a given age range.
	 * 
	 * @param ageRange The age range.
	 * 
	 * @return The person.
	 */
	public static Person generatePerson(Range ageRange) {
		return generatePerson(getRandomSex(), ageRange);
	}
	
	/**
	 * Generates a person with a given sex.
	 * 
	 * @param sex The sex.
	 * 
	 * @return The person.
	 */
	public static Person generatePerson(Sex sex) {
		return generatePerson(sex, DEFAULT_AGE_RANGE);
	}
	
	/**
	 * Generates a person with a given sex and age range.
	 * 
	 * @param sex The sex.
	 * 
	 * @param ageRange The age range.
	 * 
	 * @return The person.
	 */
	public static Person generatePerson(Sex sex, Range ageRange) {
		return generatePerson(sex, ageRange, getRandomWesternCountry());
	}
	
	/**
	 * Generates a person with a given sex and country.
	 * 
	 * @param sex The sex.
	 * 
	 * @param country The country.
	 * 
	 * @return The person.
	 */
	public static Person generatePerson(Sex sex, Country country) {
		return generatePerson(sex, DEFAULT_AGE_RANGE);
	}
	
	/**
	 * Generates a person with a given age range and country.
	 * 
	 * @param ageRange The age range.
	 * 
	 * @param country The country.
	 * 
	 * @return The person.
	 */
	public static Person generatePerson(Range ageRange, Country country) {
		return generatePerson(ageRange, getRandomWesternCountry());
	}
	
	/**
	 * Generates a person with a given sex and age range and country.
	 * 
	 * @param sex The sex.
	 * 
	 * @param ageRange The age range.
	 * 
	 * @param country The country.
	 * 
	 * @return The person.
	 */
	public static Person generatePerson(Sex sex, Range ageRange, Country country) {
		Person person = new Person(sex, ageRange, country);
		
		try {
			person.generate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return person;
	}
	
	/**
	 * Retrieves a random sex.
	 * 
	 * @return The random sex.
	 */
	public static Sex getRandomSex() {
		return Sex.values()[Maths.random(Sex.values().length)];
	}
	
	/**
	 * Retrieves a random western country.
	 * 
	 * @return The country.
	 */
	public static Country getRandomWesternCountry() {
		return WESTERN_COUNTRIES[Maths.random(WESTERN_COUNTRIES.length)];
	}
	
	/**
	 * Retrieves a random english speaking country.
	 * 
	 * @return The country.
	 */
	public static Country getRandomEnglishCountry() {
		return ENGLISH_COUNTRIES[Maths.random(ENGLISH_COUNTRIES.length)];
	}
	
	/**
	 * Retrieves a random country.
	 * 
	 * @return The country.
	 */
	public static Country getRandomCountry() {
		return Country.values()[Maths.random(Country.values().length)];
	}

}
