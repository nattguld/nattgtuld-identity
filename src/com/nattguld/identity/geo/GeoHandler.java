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
import com.nattguld.util.geo.GeoCoordinates;
import com.nattguld.util.geo.GeoManager;
import com.nattguld.util.geo.GeoPosition;

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
		return fetchLocationByCoordinates(geo.getGeoCoords());
	}
	
	/**
	 * Fetches the location for given coordinates.
	 * 
	 * @param geoCoordinate The geo coordinate.
	 * 
	 * @return The location.
	 */
	public static String fetchLocationByCoordinates(GeoCoordinates geoCoordinate) {
		try (HttpClient c = new HttpClient()) {
			RequestResponse rr = c.dispatchRequest(new GetRequest("https://maps.googleapis.com/maps/api/geocode/json?latlng=" 
					+ geoCoordinate.getLatitude() + "," + geoCoordinate.getLongitude() + "&key=" + GEOCODING_API_KEY));
			
			if (!rr.validate()) {
				System.err.println("Failed to fetch location by coordinates " + geoCoordinate.toString());
				return "Los Angeles, United States";
			}
			JsonReader jsonObject = rr.getJsonReader();
			
			String status = jsonObject.getAsString("status");
					
			if (!status.equalsIgnoreCase("OK")) {
				System.err.println("Failed to fetch location by coordinates, invalid response status");
				return "Los Angeles, United States";
			}
			String city = "Los Angeles";
			String country = "United States";
			
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
	public static GeoCoordinates fetchIPCoordinate() {
		return fetchIPCoordinate(ProxyManager.LOCALHOST);
	}
	
	/**
	 * Fetches the IP location of a given http client session.
	 * 
	 * @return The geo position.
	 */
	public static GeoCoordinates fetchIPCoordinate(HttpClient c) {
		return fetchIPCoordinate(c.getProxy());
	}
	
	/**
	 * Fetches the IP location of a given proxy.
	 * 
	 * @return The geo position.
	 */
	public static GeoCoordinates fetchIPCoordinate(HttpProxy proxy) {
		proxy = Objects.isNull(proxy) || proxy == ProxyManager.LOCALHOST ? null : proxy;
		
		try (HttpClient c = new HttpClient(proxy)) {
			RequestResponse rr = c.dispatchRequest(new GetRequest("https://www.iplocation.net/"));
			
			if (!rr.validate()) {
				System.err.println("Failed to fetch geo coordinate for " + proxy + " (" + rr.getCode() + ")");
				return new GeoCoordinates(34.052235, -118.243683);
			}
			String latitude = rr.getAsDoc().select("#wrapper > section > div > div > div.col.col_8_of_12 > div:nth-child(12) > div > table > tbody:nth-child(4) > tr > td:nth-child(3)").text();
			String longitude = rr.getAsDoc().select("#wrapper > section > div > div > div.col.col_8_of_12 > div:nth-child(12) > div > table > tbody:nth-child(4) > tr > td:nth-child(4)").text();
					
			double latDbl = Double.parseDouble(latitude);
			double longDbl = Double.parseDouble(longitude);
					
			return new GeoCoordinates(latDbl, longDbl);
		}
	}
	
	/**
	 * Fetches the geo position for a given query.
	 * 
	 * @param query The query.
	 * 
	 * @return The geo position.
	 */
	public static GeoCoordinates fetchCoordinateByQuery(String query) {
		try (HttpClient c = new HttpClient()) {
			RequestResponse rr = c.dispatchRequest(new GetRequest("https://maps.googleapis.com/maps/api/geocode/json?address=" 
					+ query + "&key=" + GEOCODING_API_KEY));
			
			if (!rr.validate()) {
				System.err.println("Failed to fetch geo for query " + query);
				return GeoManager.DEFAULT_GEO_COORDINATES;
			}
			JsonReader jsonObject = rr.getJsonReader();
		    
			String status = jsonObject.getAsString("status");
	    
			if (!status.equalsIgnoreCase("OK")) {
				System.err.println("Failed to fetch location by coordinates, invalid response status");
				return GeoManager.DEFAULT_GEO_COORDINATES;
			}
			JsonObject resultsObj = jsonObject.getAsJsonArray("results").get(0).getAsJsonObject();
			JsonObject geometryObj = resultsObj.get("geometry").getAsJsonObject();
			JsonObject locObj = geometryObj.get("location").getAsJsonObject();
	    	
			return new GeoCoordinates(locObj.get("lat").getAsDouble(), locObj.get("lng").getAsDouble());
		}
	}
	
	/**
	 * Retrieves whether a given latitude is valid or not.
	 * 
	 * @param latitude The latitude.
	 * 
	 * @return The result.
	 */
	public static boolean isValidLatitude(double latitude) {
		return latitude >= -90d && latitude <= 90d;
	}
	
	/**
	 * Retrieves whether a given longitude is valid or not.
	 * 
	 * @param longitude The longitude.
	 * 
	 * @return The result.
	 */
	public static boolean isValidLongitude(double longitude) {
		return longitude >= -180d && longitude <= 180d;
	}

}
