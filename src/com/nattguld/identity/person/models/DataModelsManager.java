package com.nattguld.identity.person.models;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nattguld.identity.person.models.impl.CountrySim;
import com.nattguld.identity.person.models.impl.GivenNames;
import com.nattguld.identity.person.models.impl.GivenNamesSet;
import com.nattguld.identity.person.models.impl.HumanHeights;
import com.nattguld.identity.person.models.impl.Surnames;
import com.nattguld.util.locale.Country;
import com.nattguld.util.locale.LocaleManager;

/**
 * 
 * @author randqm
 *
 */

public class DataModelsManager {
	
	/**
	 * Holds the human heights.
	 */
	private static List<HumanHeights> humanHeights = new ArrayList<>();
	
	/**
	 * Holds the given name sets.
	 */
	private static List<GivenNamesSet> givenNamesSets = new ArrayList<>();
	
	/**
	 * Holds the given names.
	 */
	private static List<GivenNames> givenNames = new ArrayList<>();
	
	/**
	 * Holds the surnames.
	 */
	private static List<Surnames> surnames = new ArrayList<>();
	
	/**
	 * The country sims.
	 */
	private static List<CountrySim> sims = new ArrayList<>();
	
	
	/**
	 * Retrieves sim details for a given country.
	 * 
	 * @param cr The country.
	 * 
	 * @return The sim details.
	 */
	public static CountrySim getCountrySim(Country cr) {
		if (sims.isEmpty()) {
			JsonArray arr = loadData("country_sims.json");
			
			if (Objects.isNull(arr) || arr.size() == 0) {
				System.err.println("Failed to load country sims");
				return null;
			}
			for (JsonElement el : arr) {
				JsonObject obj = el.getAsJsonObject();
				
				String countryCode = obj.get("country_code").getAsString();
				String countryName = obj.get("country_name").getAsString();
				String mcc = obj.get("mcc").getAsString();
				JsonArray mncsJsonArr = obj.get("mncs").getAsJsonArray();
				
				String[] mncs = new String[mncsJsonArr.size()];
				
				for (int i = 0; i < mncs.length; i ++) {
					mncs[i] = mncsJsonArr.get(i).getAsString();
				}
				sims.add(new CountrySim(countryCode, countryName, mcc, mncs));
			}
		}
		return sims.stream()
				.filter(g -> g.getCountryCode().equalsIgnoreCase(cr.getCode()))
				.findFirst().orElse(null);
	}
	
	/**
	 * Retrieves the given names set for a given set name.
	 * 
	 * @param setName the set name.
	 * 
	 * @return The given names set.
	 */
	public static GivenNamesSet getGivenNamesSet(String setName) {
		if (givenNamesSets.isEmpty()) {
			JsonArray arr = loadData("given_name_sets.json");
			
			if (Objects.isNull(arr) || arr.size() == 0) {
				System.err.println("Failed to load given name sets");
				return null;
			}
			for (JsonElement el : arr) {
				JsonObject obj = el.getAsJsonObject();
				
				String set = obj.get("set").getAsString();
				JsonArray maleJsonArr = obj.get("male").getAsJsonArray();
				JsonArray femaleJsonArr = obj.get("female").getAsJsonArray();
				
				String[] maleNames = new String[maleJsonArr.size()];
				
				for (int i = 0; i < maleNames.length; i++) {
					maleNames[i] = maleJsonArr.get(i).getAsString();
				}
				String[] femaleNames = new String[femaleJsonArr.size()];
				
				for (int i = 0; i < femaleNames.length; i++) {
					femaleNames[i] = femaleJsonArr.get(i).getAsString();
				}
				givenNamesSets.add(new GivenNamesSet(set, maleNames, femaleNames));
			}
		}
		return givenNamesSets.stream()
				.filter(g -> g.getSetName().equalsIgnoreCase(setName))
				.findFirst().orElse(null);
	}
	
	/**
	 * Retrieves the given names for a given country.
	 * 
	 * @param country The country.
	 * 
	 * @return The given names.
	 */
	public static GivenNames getGivenNames(Country country) {
		if (givenNames.isEmpty()) {
			JsonArray arr = loadData("given_names.json");
			
			if (Objects.isNull(arr) || arr.size() == 0) {
				System.err.println("Failed to load given names");
				return null;
			}
			for (JsonElement el : arr) {
				JsonObject obj = el.getAsJsonObject();
				
				String countryCode = obj.get("country_code").getAsString();
				String countryName = obj.get("country_name").getAsString();
				String setName = obj.get("given_name_set").getAsString();
				
				givenNames.add(new GivenNames(countryCode, countryName, setName));
			}
		}
		return givenNames.stream()
				.filter(g -> g.getCountryCode().equalsIgnoreCase(country.getCode()))
				.findFirst().orElse(null);
	}
	
	/**
	 * Retrieves the surnames for a given country.
	 * 
	 * @param country The country.
	 * 
	 * @return The surnames.
	 */
	public static Surnames getSurnames(Country country) {
		if (surnames.isEmpty()) {
			JsonArray arr = loadData("surnames.json");
			
			if (Objects.isNull(arr) || arr.size() == 0) {
				System.err.println("Failed to load surnames");
				return null;
			}
			for (JsonElement el : arr) {
				JsonObject obj = el.getAsJsonObject();
				
				String countryCode = obj.get("country_code").getAsString();
				String countryName = obj.get("country_name").getAsString();
				JsonArray surnamesJsonArr = obj.get("last_names").getAsJsonArray();
				String[] surnamesArr = new String[surnamesJsonArr.size()];
				
				for (int i = 0; i < surnamesArr.length; i++) {
					surnamesArr[i] = surnamesJsonArr.get(i).getAsString();
				}
				surnames.add(new Surnames(countryCode, countryName, surnamesArr));
			}
		}
		return surnames.stream()
				.filter(g -> g.getCountryCode().equalsIgnoreCase(country.getCode()))
				.findFirst().orElse(null);
	}
	
	/**
	 * Retrieves the country human heights for a given country.
	 * 
	 * @param country The country.
	 * 
	 * @return The human heights.
	 */
	public static HumanHeights getHumanHeights(Country country) {
		if (humanHeights.isEmpty()) {
			JsonArray arr = loadData("average_human_heights.json");
			
			if (Objects.isNull(arr) || arr.size() == 0) {
				System.err.println("Failed to load human heights");
				return null;
			}
			for (JsonElement el : arr) {
				JsonObject obj = el.getAsJsonObject();
				
				String countryCode = obj.get("country_code").getAsString();
				String countryName = obj.get("country_name").getAsString();
				double maleCm = obj.get("male_cm").getAsDouble();
				double femaleCm = obj.get("female_cm").getAsDouble();
				
				humanHeights.add(new HumanHeights(countryCode, countryName, maleCm, femaleCm));
			}
		}
		return humanHeights.stream()
				.filter(g -> g.getCountryCode().equalsIgnoreCase(country.getCode()))
				.findFirst().orElse(null);
	}
	
	/**
	 * Loads the data from a given file name into a json array.
	 * 
	 * @param fileName The file name.
	 * 
	 * @return The json array.
	 */
	private static JsonArray loadData(String fileName) {
		try (InputStreamReader reader = new InputStreamReader(LocaleManager.class.getResourceAsStream("/data/" + fileName), "UTF-8")) {
			return (JsonArray)new JsonParser().parse(reader);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
