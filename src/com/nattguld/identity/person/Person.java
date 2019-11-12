package com.nattguld.identity.person;

import java.util.Objects;

import com.google.gson.JsonObject;
import com.mifmif.common.regex.Generex;
import com.nattguld.http.HttpClient;
import com.nattguld.http.requests.impl.GetRequest;
import com.nattguld.http.response.RequestResponse;
import com.nattguld.identity.Identity;
import com.nattguld.identity.Sex;
import com.nattguld.identity.person.profile.BioHandler;
import com.nattguld.util.chrono.DateTime;
import com.nattguld.util.generics.kvps.impl.StringKeyValuePair;
import com.nattguld.util.locale.Country;
import com.nattguld.util.locale.Language;
import com.nattguld.util.maths.Maths;
import com.nattguld.util.maths.Range;
import com.nattguld.util.text.TextSeed;
import com.nattguld.util.text.TextUtil;

/**
 * 
 * @author randqm
 *
 */

public class Person implements Identity {
	
	/**
	 * The bio.
	 */
	private final String bio;
	
    /**
     * The credentials.
     */
    private StringKeyValuePair creds;
    
    /**
     * The first name.
     */
    private String firstName;
    
    /**
     * The last name.
     */
    private String lastName;
    
    /**
     * The street.
     */
    private String street;
    
    /**
     * The city.
     */
    private String city;
    
    /**
     * The zip code.
     */
    private String zipcode;
    
    /**
     * The state or province.
     */
    private String state;
    
    /**
     * The date of birth.
     */
    private DateTime dob;
    
    /**
     * The avatar.
     */
    private String avatar;
    
    /**
     * The email address.
     */
    private String emailAddress;
    
	/**
	 * The sex.
	 */
	private final Sex sex;
	
	/**
	 * The country.
	 */
	private final Country country;
	
	/**
	 * The spoken language.
	 */
	private final Language language;
	
	/**
	 * The amount of username builds attempted.
	 */
	private int usernameBuilds;
	
	
	/**
	 * Creates a new person.
	 * 
	 * @param sex The sex.
	 * 
	 * @param ageRange The age range.
	 * 
	 * @param country The country.
	 */
	public Person(Sex sex, Range ageRange, Country country) {
		this.bio = BioHandler.getRandomBio();
		this.creds = new StringKeyValuePair(new Generex("([A-Z]{1})([aeiou]{1,2})([a-z]{1,2})([aeiou]{1,2})([a-z]{1,2})([aeiou]{1,2})([a-z]{1,2})").random(), TextUtil.generatePassword());
		this.firstName = new Generex("([A-Z]{1})([aeiou]{1,2})([a-z]{1,2})([aeiou]{1,2})([a-z]{1,2})").random();
		this.lastName = new Generex("([A-Z]{1})([aeiou]{1,2})([a-z]{1,2})([aeiou]{1,2})([a-z]{1,2})").random();
		this.street = "waystreet " + Maths.random(new Range(1, 500));
		this.city = "Local";
		this.zipcode = "NA";
		this.state = "NA";
		this.emailAddress = TextUtil.generateFakeEmail();
		this.sex = sex;
		this.country = country;
		this.language = country.getRandomLanguage();
		this.dob = new DateTime(new DateTime().getLocalDateTime().minusYears(18).minusMonths(1 + Maths.random(11)).minusDays(1 + Maths.random(27)));
	}

	@Override
	public void generate() {
		try (HttpClient c = new HttpClient()) {
			RequestResponse rr = c.dispatchRequest(new GetRequest("https://randomuser.me/api/?gender=" 
					+ (sex == Sex.FEMALE ? "female" : "male") + "&password=upper,lower,number,8-16"));
			
			if (!rr.validate()) {
				System.err.println("Failed to request identity details (" + rr.getCode() + ")");
				return;
			}
			JsonObject jsonObj = rr.getJsonReader().getObject();
			JsonObject results = jsonObj.get("results").getAsJsonArray().get(0).getAsJsonObject();
			
			emailAddress = results.get("email").getAsString();
			
			JsonObject nameObj = results.get("name").getAsJsonObject();
			firstName = nameObj.get("first").getAsString();
			lastName = nameObj.get("last").getAsString();
			
			JsonObject locObj = results.get("location").getAsJsonObject();
			JsonObject streetObj = locObj.get("street").getAsJsonObject();
			street = streetObj.get("name").getAsString() + " " + streetObj.get("number").getAsInt();
			state = locObj.has("state") && !locObj.get("state").isJsonNull() ? locObj.get("state").getAsString() : state;
			city = locObj.has("city") && !locObj.get("city").isJsonNull() ? locObj.get("city").getAsString() : city;
				
			try {
				zipcode = Integer.toString(locObj.get("postcode").getAsInt());
			} catch (Exception ex) {
				zipcode = locObj.has("postcode") && !locObj.get("postcode").isJsonNull() ? locObj.get("postcode").getAsString() : zipcode;
			}
			JsonObject coordsObj = locObj.get("coordinates").getAsJsonObject();
			String latitude = coordsObj.get("latitude").getAsString();
			String longitude = coordsObj.get("longitude").getAsString();
				
			JsonObject timezoneObj = locObj.get("timezone").getAsJsonObject();
			String timezoneOffset = timezoneObj.get("offset").getAsString();
			String timezoneName = timezoneObj.get("description").getAsString();
			
			JsonObject loginObj = results.get("login").getAsJsonObject();
			String uuid = loginObj.get("uuid").getAsString();
			String username = loginObj.get("username").getAsString();
			String password = loginObj.get("password").getAsString();

			JsonObject picObj = results.get("picture").getAsJsonObject();
			avatar = picObj.get("large").getAsString();
    		
    		String emailName = emailAddress.split("@")[0] + Maths.random(999);
    		emailAddress = emailName += "@gmail.com";
    		
    		String builtUsername = Maths.random(2) == 1 ? creds.getKey() : buildUsername(username);
    		
    		if (Objects.nonNull(builtUsername)) {
    			creds = new StringKeyValuePair(builtUsername, password);
    		} else {
    			if (isValid(username)) {
    				creds = new StringKeyValuePair(username, password);
    			}
    		}
		}
	}

	/**
	 * Builds a username.
	 * 
	 * @return The username.
	 */
	private String buildUsername(String originalUsername) {
		String username = firstName + lastName + Maths.random(999);
		String rndStr = TextUtil.randomString(2, 5, TextSeed.LOWERCASE);
		String birthYear2d = Integer.toString(getBirthYear()).substring(2, 4);
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
		usernameBuilds++;
		
		if (!isValid(username)) {
			return usernameBuilds < 4 ? buildUsername(originalUsername) : originalUsername;
		}
		return username;
	}
	
	private boolean isValid(String username) {
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
	
	/**
	 * Retrieves the bio.
	 * 
	 * @return The bio.
	 */
	public String getBio() {
		return bio;
	}
	
	/**
	 * Retrieves the credentials.
	 * 
	 * @return The credentials.
	 */
	public StringKeyValuePair getCreds() {
		return creds;
	}

	/**
	 * Modifies the credentials.
	 * 
	 * @param creds The new credentials.
	 */
	public void setCreds(StringKeyValuePair creds) {
		this.creds = creds;
	}

	/**
	 * Retrieves the first name.
	 * 
	 * @return The first name.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Modifies the first name.
	 * 
	 * @param firstName The new first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Retrieves the last name.
	 * 
	 * @return The last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Modifies the last name.
	 * 
	 * @param lastName The new last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Retrieves the street.
	 * 
	 * @return The street.
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Modifies the street.
	 * 
	 * @param street The new street.
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Retrieves the city.
	 * 
	 * @return The city.
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Modifies the city.
	 * 
	 * @param city The new city.
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Retrieves the zipcode.
	 * 
	 * @return The zipcode.
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * Modifies the zipcode.
	 * 
	 * @param zipcode The new zipcode.
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * Retrieves the state.
	 * 
	 * @return The state.
	 */
	public String getState() {
		return state;
	}

	/**
	 * Modifies the state.
	 * 
	 * @param state The new state.
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * Modifies the birth day.
	 * 
	 * @param birthDay The new birth day.
	 */
	public void setDob(DateTime dob) {
		this.dob = dob;
	}

	/**
	 * Retrieves the birth day.
	 * 
	 * @return The birth day.
	 */
	public int getBirthDay() {
		return dob.getDay();
	}

	/**
	 * Retrieves the birth month.
	 * 
	 * @return The birth month.
	 */
	public int getBirthMonth() {
		return dob.getMonth();
	}

	/**
	 * Retrieves the birth year.
	 * 
	 * @return The birth year.
	 */
	public int getBirthYear() {
		return dob.getYear();
	}
	
	/**
	 * Retrieves the age.
	 * 
	 * @return The age.
	 */
	public int getAge() {
		return dob.getPeriodTillToday().getYears();
	}

	/**
	 * Retrieves the avatar.
	 * 
	 * @return The avatar.
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * Modifies the avatar.
	 * 
	 * @param avatar The new avatar.
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * Retrieves the email address.
	 * 
	 * @return The email address.
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Modifies the email address.
	 * 
	 * @param emailAddress The new email address.
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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
	 * Retrieves the language.
	 * 
	 * @return The language.
	 */
	public Language getLanguage() {
		return language;
	}

}
