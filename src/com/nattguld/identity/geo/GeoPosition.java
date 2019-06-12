package com.nattguld.identity.geo;

import java.util.Objects;

/**
 * 
 * @author randqm
 *
 */

public class GeoPosition {
	
	/*
	 * The country name.
	 */
	private String country;
	
	/**
	 * The city name.
	 */
	private String city;
	
	/**
	 * The latitude.
	 */
	private double latitude;
	
	/*
	 * The longitude.
	 */
	private double longitude;
	
	
	/**
	 * Creates a new position.
	 * 
	 * @param latitude The latitude.
	 * 
	 * @param longitude The longitude.
	 * 
	 * @throws Exception 
	 */
	public GeoPosition(double latitude, double longitude) {
		this(null, latitude, longitude);
	}
	
	/**
	 * Creates a new position.
	 * 
	 * @param name The location name.
	 * 
	 * @param latitude The latitude.
	 * 
	 * @param longitude The longitude.
	 * 
	 * @throws Exception 
	 */
	public GeoPosition(String name, double latitude, double longitude) {
		if (Objects.isNull(name)) {
			System.err.println("No position name specified");
			return;
		}
		String[] split = name.split(",");
		
		this.country = split[0].trim();
		this.city = split[1].trim();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	 * Creates a new position.
	 * 
	 * @param country The country name.
	 * 
	 * @param city The city name.
	 * 
	 * @param latitude The latitude.
	 * 
	 * @param longitude The longitude.
	 */
	public GeoPosition(String country, String city, double latitude, double longitude) {
		this.country = country;
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	 * Retrieves the latitude.
	 * 
	 * @return The latitude.
	 */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * Retrieves the longitude.
	 * 
	 * @return The longitude.
	 */
	public double getLongitude() {
		return longitude;
	}
	
	/**
	 * Retrieves the country of the position.
	 * 
	 * @return The country.
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * Retrieves the city of the position.
	 * 
	 * @return The city.
	 */
	public String getCity() {
		return city;
	}
	
	@Override
	public String toString() {
		return country + ", " + city + " [lat: " + getLatitude() + ", lon: " + getLongitude() + "]";
	}
	
}
