package com.nattguld.identity.geo;

import java.util.Objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nattguld.data.json.JsonReader;
import com.nattguld.http.HttpClient;
import com.nattguld.http.proxies.HttpProxy;
import com.nattguld.http.proxies.ProxyManager;
import com.nattguld.http.requests.impl.GetRequest;
import com.nattguld.http.response.RequestResponse;

/**
 * 
 * @author randqm
 *
 */
 
public class GeoHandler {
	
	/**
	 * The Google geo coding API key.
	 */
	private static final String GEOCODING_API_KEY = "AIzaSyDhZ3DBNJ2_UpPjS_y4esQztsu6YsCpC1Q";
	
	
	/**
	 * Fetches the location for a geo position.
	 * 
	 * @param geo The geo position.
	 * 
	 * @return The location.
	 */
	public static String fetchLocationByCoordinates(GeoPosition geo) {
		return fetchLocationByCoordinates(geo.getLatitude(), geo.getLongitude());
	}
	
	/**
	 * Fetches the location for given coordinates.
	 * 
	 * @param latitude The latitude.
	 * 
	 * @param longitude The longitude.
	 * 
	 * @return The location.
	 */
	public static String fetchLocationByCoordinates(double latitude, double longitude) {
		try (HttpClient c = new HttpClient()) {
			RequestResponse rr = c.dispatchRequest(new GetRequest("https://maps.googleapis.com/maps/api/geocode/json?latlng=" 
					+ latitude + "," + longitude + "&key=" + GEOCODING_API_KEY));
			
			if (!rr.validate()) {
				System.err.println("Failed to fetch location by coordinates [" + latitude + ":" + longitude + "]");
				return "Los Angeles, USA";
			}
			JsonReader jsonObject = rr.getJsonReader();
			
			String status = jsonObject.getAsString("status");
					
			if (!status.equalsIgnoreCase("OK")) {
				System.err.println("Failed to fetch location by coordinates, invalid response status");
				return "Los Angeles, USA";
			}
			String city = "Los Anagels";
			String country = "USA";
			
			JsonArray results = jsonObject.getAsJsonArray("results");
	    	
			for (JsonElement e : results) {
				JsonObject result = e.getAsJsonObject();
			    		
				JsonArray types = result.get("types").getAsJsonArray();
				
				if (types.get(0).getAsString().equalsIgnoreCase("locality") && types.get(1).getAsString().equalsIgnoreCase("political")) {
					JsonArray addressComponents = result.get("address_components").getAsJsonArray();
			    			
					for (JsonElement e2 : addressComponents) {
						JsonObject adrComp = e2.getAsJsonObject();
			    				
						JsonArray adrTypes = adrComp.get("types").getAsJsonArray();
									
						if (adrTypes.size() != 2) {
							continue;
						}
						String type1 = adrTypes.get(0).getAsString();
						String type2 = adrTypes.get(1).getAsString();
			    				
						if (type1.equalsIgnoreCase("locality") && type2.equalsIgnoreCase("political")) {
							city = adrComp.get("long_name").getAsString();
						} else if (type1.equalsIgnoreCase("country") && type2.equalsIgnoreCase("political")) {
							country = adrComp.get("long_name").getAsString();
						}
						if (Objects.nonNull(city) && Objects.nonNull(country)) {
							break;
						}
					}
				}
				if (Objects.nonNull(city) && Objects.nonNull(country)) {
					break;
				}
			}
			return city + ", " + country;
		}
	}
	
	/**
	 * Fetches the IP location of our localhost.
	 * 
	 * @return The geo position.
	 */
	public static GeoPosition fetchIPLocation() {
		return fetchIPLocation(ProxyManager.INVALID_PROXY);
	}
	
	/**
	 * Fetches the IP location of a given http client session.
	 * 
	 * @return The geo position.
	 */
	public static GeoPosition fetchIPLocation(HttpClient c) {
		return fetchIPLocation(c.getProxy());
	}
	
	/**
	 * Fetches the IP location of a given proxy.
	 * 
	 * @return The geo position.
	 */
	public static GeoPosition fetchIPLocation(HttpProxy proxy) {
		proxy = Objects.isNull(proxy) || proxy == ProxyManager.INVALID_PROXY ? null : proxy;
		
		try (HttpClient c = new HttpClient(proxy)) {
			RequestResponse rr = c.dispatchRequest(new GetRequest("https://www.iplocation.net/"));
			
			if (!rr.validate()) {
				System.err.println("Failed to fetch geo position for " + proxy + " (" + rr.getCode() + ")");
				return new GeoPosition(34.052235, -118.243683);
			}
			String latitude = rr.getAsDoc().select("#wrapper > section > div > div > div.col.col_8_of_12 > div:nth-child(12) > div > table > tbody:nth-child(4) > tr > td:nth-child(3)").text();
			String longitude = rr.getAsDoc().select("#wrapper > section > div > div > div.col.col_8_of_12 > div:nth-child(12) > div > table > tbody:nth-child(4) > tr > td:nth-child(4)").text();
					
			double latDbl = Double.parseDouble(latitude);
			double longDbl = Double.parseDouble(longitude);
					
			return new GeoPosition(latDbl, longDbl);
		}
	}
	
	/**
	 * Fetches the geo position for a given query.
	 * 
	 * @param query The query.
	 * 
	 * @return The geo position.
	 */
	public static GeoPosition fetchGeoByQuery(String query) {
		try (HttpClient c = new HttpClient()) {
			RequestResponse rr = c.dispatchRequest(new GetRequest("https://maps.googleapis.com/maps/api/geocode/json?address=" 
					+ query + "&key=" + GEOCODING_API_KEY));
			
			if (!rr.validate()) {
				System.err.println("Failed to fetch geo for query " + query);
				return new GeoPosition(34.052235, -118.243683);
			}
			JsonReader jsonObject = rr.getJsonReader();
		    
			String status = jsonObject.getAsString("status");
	    
			if (!status.equalsIgnoreCase("OK")) {
				System.err.println("Failed to fetch location by coordinates, invalid response status");
				return new GeoPosition(34.052235, -118.243683);
			}
			JsonObject resultsObj = jsonObject.getAsJsonArray("results").get(0).getAsJsonObject();
			JsonObject geometryObj = resultsObj.get("geometry").getAsJsonObject();
			JsonObject locObj = geometryObj.get("location").getAsJsonObject();
	    	
			return new GeoPosition(locObj.get("lat").getAsDouble(), locObj.get("lng").getAsDouble());
		}
	}

}
