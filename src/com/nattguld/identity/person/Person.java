package com.nattguld.identity.person;

import java.util.Objects;

import com.nattguld.identity.Sex;
import com.nattguld.identity.person.biometrics.BioMetrics;
import com.nattguld.identity.person.biometrics.BiometricsManager;
import com.nattguld.identity.person.profile.BioHandler;
import com.nattguld.util.chrono.DateTime;
import com.nattguld.util.generics.kvps.impl.StringKeyValuePair;
import com.nattguld.util.geo.GeoManager;
import com.nattguld.util.geo.GeoPosition;
import com.nattguld.util.geo.location.Address;
import com.nattguld.util.geo.models.impl.CountryCities;
import com.nattguld.util.locale.Country;
import com.nattguld.util.locale.Language;
import com.nattguld.util.maths.Maths;
import com.nattguld.util.maths.Range;
import com.nattguld.util.text.TextUtil;

/**
 * 
 * @author randqm
 *
 */

public class Person {
	
	/**
	 * The country.
	 */
	private final Country country;
	
	/**
	 * The sex.
	 */
	private final Sex sex;
	
	/**
	 * The age range.
	 */
	private final Range ageRange;
	
	/**
	 * The person's biometrics.
	 */
	private BioMetrics bioMetrics;
	
	/**
	 * The given name.
	 */
	private String givenName;
	
	/**
	 * The surname
	 */
	private String surname;
	
	/**
	 * The geo position.
	 */
	private GeoPosition geoPos;
	
	/**
	 * The biography.
	 */
	private String bio;
	
	/**
	 * The online credentials.
	 */
	private StringKeyValuePair onlineCreds;
	
	/**
	 * The email credentials.
	 */
	private StringKeyValuePair emailCreds;
	
	/**
	 * The address.
	 */
	private Address address;
	
	/**
	 * The spoken language.
	 */
	private Language language;
	
	/**
	 * The time of birth.
	 */
	private long dobTime;
	
	/**
	 * The date of birth.
	 */
	private transient DateTime dob;
	
	
	/**
	 * Creates a new person identity.
	 * 
	 * @param sex The sex.
	 * 
	 * @param ageRange The age range.
	 * 
	 * @param country The country.
	 */
	public Person(Sex sex, Range ageRange, Country country) {
		this.sex = sex;
		this.ageRange = ageRange;
		this.country = country;
	}
	
	/**
	 * Retrieves the sex.
	 * 
	 * @return The sex.
	 */
	public Sex getSex() {
		return sex;
	}
	
	/**
	 * Retrieves the country.
	 * 
	 * @return The country.
	 */
	public Country getCountry() {
		return country;
	}
	
	/**
	 * Retrieves the bio metrics.
	 * 
	 * @return The bio metrics.
	 */
	public BioMetrics getBioMetrics() {
		if (Objects.isNull(bioMetrics)) {
			setBioMetrics(BiometricsManager.generateRandom(country, sex));
		}
		return bioMetrics;
	}
	
	/**
	 * Modifies the bio metrics.
	 * 
	 * @param bioMetrics The bio metrics.
	 * 
	 * @return The person.
	 */
	public Person setBioMetrics(BioMetrics bioMetrics) {
		this.bioMetrics = bioMetrics;
		return this;
	}
	
	/**
	 * Retrieves the given name.
	 * 
	 * @return The given name.
	 */
	public String getGivenName() {
		if (Objects.isNull(givenName)) {
			setGivenName(CredentialsHandler.getGivenName(sex, country));
		}
		return givenName;
	}
	
	/**
	 * Modifies the given name.
	 * 
	 * @param givenName The new given name.
	 * 
	 * @return The person.
	 */
	public Person setGivenName(String givenName) {
		this.givenName = givenName;
		return this;
	}
	
	/**
	 * Retrieves the surname.
	 * 
	 * @return The surname.
	 */
	public String getSurname() {
		if (Objects.isNull(surname)) {
			setSurname(CredentialsHandler.getSurname(country));
		}
		return surname;
	}
	
	/**
	 * Modifies the name.
	 * 
	 * @param surname The new name.
	 * 
	 * @return The person.
	 */
	public Person setSurname(String surname) {
		this.surname = surname;
		return this;
	}
	
	/**
	 * Retrieves the full name.
	 * 
	 * @return The full name.
	 */
	public String getFullName() {
		return getGivenName() + " " + getSurname();
	}
	
	/**
	 * Retrieves the geo position.
	 * 
	 * @return The geo position.
	 */
	public GeoPosition getGeoPos() {
		if (Objects.isNull(geoPos)) {
			System.err.println("No geo position set for person");
			setGeoPos(GeoManager.DEFAULT_GEO_POSITION);
		}
		return geoPos;
	}
	
	/**
	 * Modifies the geo position.
	 * 
	 * @param geoPos The new geo position.
	 * 
	 * @return The person.
	 */
	public Person setGeoPos(GeoPosition geoPos) {
		this.geoPos = geoPos;
		return this;
	}
	
	/**
	 * Retrieves the biography.
	 * 
	 * @return The biography.
	 */
	public String getBio() {
		if (Objects.isNull(bio)) {
			setBio(BioHandler.getRandomBio());
		}
		return bio;
	}
	
	/**
	 * Modifies the biography.
	 * 
	 * @param bio The new biography.
	 * 
	 * @return The person.
	 */
	public Person setBio(String bio) {
		this.bio = bio;
		return this;
	}
	
	/*
	 * Retrieves the online credentials.
	 */
	public StringKeyValuePair getOnlineCreds() {
		if (Objects.isNull(onlineCreds)) {
			setOnlineCreds(CredentialsHandler.generateOnlineCredentials(getGivenName(), getSurname(), getBirthYear()));
		}
		return onlineCreds;
	}
	
	/**
	 * Modifies the online credentials.
	 * 
	 * @param onlineCreds The new online credentials.
	 * 
	 * @return The person.
	 */
	public Person setOnlineCreds(StringKeyValuePair onlineCreds) {
		this.onlineCreds = onlineCreds;
		return this;
	}
	
	/*
	 * Retrieves the email credentials.
	 */
	public StringKeyValuePair getEmailCreds() {
		if (Objects.isNull(emailCreds)) {
			setEmailCreds(new StringKeyValuePair(
					TextUtil.generateFakeEmail()
					, TextUtil.generatePassword()));
		}
		return emailCreds;
	}
	
	/**
	 * Modifies the email credentials.
	 * 
	 * @param emailCreds The new email credentials.
	 * 
	 * @return The person.
	 */
	public Person setEmailCreds(StringKeyValuePair emailCreds) {
		this.emailCreds = emailCreds;
		return this;
	}
	
	/**
	 * Modifies the address.
	 * 
	 * @param address The new address.
	 * 
	 * @return The person.
	 */
	public Person setAddress(Address address) {
		this.address = address;
		return this;
	}
	
	/**
	 * Retrieves the address.
	 * 
	 * @return The address.
	 */
	public Address getAddress() {
		if (Objects.isNull(getAddress())) {
			CountryCities cc = GeoManager.getCountryCities(country);
			
			if (Objects.isNull(cc)) {
				System.err.println("No cities found for " + country.getName());
				return null;
			}
			setAddress(new Address(GeoManager.DEFAULT_CITY, "somestreet", "13"));
			return address;
		}
		return address;
	}
	
	/**
	 * Retrieves the spoken language.
	 * 
	 * @return The language.
	 */
	public Language getLanguage() {
		if (Objects.isNull(language)) {
			setLanguage(country.getRandomLanguage());
		}
		return language;
	}
	
	/**
	 * Modifies the spoken language.
	 * 
	 * @param language The new spoken language.
	 * 
	 * @return The person.
	 */
	public Person setLanguage(Language language) {
		this.language = language;
		return this;
	}
	
	/**
	 * Retrieves the birth time.
	 * 
	 * @return The birth time.
	 */
	public long getDobTime() {
		if (dobTime == 0L) {
			setDob(new DateTime(new DateTime().getLocalDateTime()
					.minusYears(ageRange.getRandom())
					.minusMonths(1 + Maths.random(11))
					.minusDays(1 + Maths.random(27))));
		}
		return dobTime;
	}
	
	/**
	 * Modifies the birth time.
	 * 
	 * @param dobTime The new birth time.
	 * 
	 * @return The person.
	 */
	public Person setDobTime(long dobTime) {
		this.dobTime = dobTime;
		return this;
	}
	
	/**
	 * Retrieves the data of birth.
	 * 
	 * @return The date of birth.
	 */
	public DateTime getDob() {
		if (Objects.isNull(dob)) {
			getDobTime();
		}
		return dob;
	}
	
	/**
	 * Modifies the date of birth.
	 * 
	 * @param dob The new date of birth.
	 * 
	 * @return The person.
	 */
	public Person setDob(DateTime dob) {
		this.dob = dob;
		setDobTime(dob.getMilliSeconds());
		return this;
	}
	
	/**
	 * Retrieves the birth day.
	 * 
	 * @return The birth day.
	 */
	public int getBirthDay() {
		return getDob().getDay();
	}

	/**
	 * Retrieves the birth month.
	 * 
	 * @return The birth month.
	 */
	public int getBirthMonth() {
		return getDob().getMonth();
	}

	/**
	 * Retrieves the birth year.
	 * 
	 * @return The birth year.
	 */
	public int getBirthYear() {
		return getDob().getYear();
	}
	
	/**
	 * Retrieves the age.
	 * 
	 * @return The age.
	 */
	public int getAge() {
		return getDob().getPeriodTillToday().getYears();
	}

}
