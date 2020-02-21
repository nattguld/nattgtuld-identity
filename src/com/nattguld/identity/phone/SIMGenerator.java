package com.nattguld.identity.phone;

import java.util.Objects;

import com.mifmif.common.regex.Generex;
import com.nattguld.identity.person.models.DataModelsManager;
import com.nattguld.identity.person.models.impl.CountrySim;
import com.nattguld.util.locale.Country;
import com.nattguld.util.maths.algorithms.Luhn;

/**
 * 
 * @author randqm
 *
 */

public class SIMGenerator { //http://phone.fyicenter.com/408_General_What_Is_the_ICCID_on_of_My_Phone.html

	
	/**
	 * Generates a SIM for a given country.
	 * 
	 * @param cr The country.
	 * 
	 * @return The SIM.
	 */
	public static Sim generateSIM(Country cr) {
		int phoneCode = cr.getPhoneCode();
		String phoneCodeStr = phoneCode < 10 ? ("0" + phoneCode) : Integer.toString(phoneCode);
		
		if (cr == Country.CANADA) {
			phoneCodeStr = "302";
		} else if (cr == Country.RUSSIA) {
			phoneCodeStr = "701";
		} else if (cr == Country.KAZAKHSTAN) {
			phoneCodeStr = "997";
		}
		CountrySim cSim = DataModelsManager.getCountrySim(cr);
		
		if (Objects.isNull(cSim)) {
			System.err.println("Failed to find sim details for " + cr.getName());
			return null;
		}
		String mcc = cSim.getMcc();
		String mnc = cSim.getRandomMnc();
		String subscriberId = new Generex("\\d{6}").random();
		
		String imei = generateIMEI();
		
		//usa example: 89 01 010xxxxxxxxxxxx
		//BE example:  89 32 01xxxxxxxxxxxx
		//CA example:  89 30201xxxxxxxxxxxx
		String baseIccId = "89" + phoneCodeStr + mnc + "000011" + subscriberId;
		int checksumDigit = Luhn.generateChecksumDigit(baseIccId);
		String iccId = baseIccId + checksumDigit;
		
		/*if (iccId.length() < 20) {
			if (iccId.length() < 19) {
				System.err.println("Invalid ICCID generated, length: " + iccId.length());
			}
			iccId += "f";
		}*/
		return new Sim(mcc, mnc, imei, iccId);
	}
	
	/**
	 * Generates an IMEI.
	 * 
	 * @return The generated IMEI.
	 */
	public static String generateIMEI() {
        int pos;
        int[] str = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int sum = 0;
        int final_digit;
        int t;
        int len_offset;
        int len = 15;
        String imei = "";

        String[] rbi = new String[]{"01", "10", "30", "33", "35", "44", "45", "49", "50", "51", "52", "53", "54", "86", "91", "98", "99"};
        String[] arr = rbi[(int) Math.floor(Math.random() * rbi.length)].split("");
        str[0] = Integer.parseInt(arr[0]);
        str[1] = Integer.parseInt(arr[1]);
        pos = 2;

        while (pos < len - 1) {
            str[pos++] = (int) (Math.floor(Math.random() * 10) % 10);
        }

        len_offset = (len + 1) % 2;
        for (pos = 0; pos < len - 1; pos++) {
            if ((pos + len_offset) % 2 != 0) {
                t = str[pos] * 2;
                if (t > 9) {
                    t -= 9;
                }
                sum += t;
            } else {
                sum += str[pos];
            }
        }

        final_digit = (10 - (sum % 10)) % 10;
        str[len - 1] = final_digit;

        for (int d : str) {
            imei += String.valueOf(d);
        }
        return imei;
    }
	
}
