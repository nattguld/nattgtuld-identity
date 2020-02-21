package com.nattguld.identity.phone;

/**
 * 
 * @author randqm
 *
 */

public class Sim {
	
	/**
	 * The mobile country code.
	 */
	private final String mcc;
	
	/**
	 * The mobile network code.
	 */
	private final String mnc;
	
	/**
	 * The international mobile equipment identity.
	 */
	private final String imei;
	
	/**
	 * The unique serial number of the SIM.
	 */
	private final String iccId;
	
	
	/**
	 * Creates a new sim.
	 * 
	 * @param mcc The mobile country code.
	 * 
	 * @param mnc The mobile network code.
	 * 
	 * @param imei The international mobile equipment identity.
	 * 
	 * @param iccId The unique serial number of the SIM.
	 */
	public Sim(String mcc, String mnc, String imei, String iccId) {
		this.mcc = mcc;
		this.mnc = mnc;
		this.imei = imei;
		this.iccId = iccId;
	}
	
	/**
	 * Retrieves the mobile country code.
	 * 
	 * @return The mobile country code.
	 */
	public String getMcc() {
		return mcc;
	}
	
	/**
	 * Retrieves the mobile network code.
	 * 
	 * @return The mobile network code.
	 */
	public String getMnc() {
		return mnc;
	}
	
	/**
	 * Retrieves the international mobile equipment identity.
	 * 
	 * @return The IMEI.
	 */
	public String getIMEI() {
		return imei;
	}
	
	/**
	 * Retrieves the unique serial number of the SIM.
	 * 
	 * @return The ICCID.
	 */
	public String getICCID() {
		return iccId;
	}

}
