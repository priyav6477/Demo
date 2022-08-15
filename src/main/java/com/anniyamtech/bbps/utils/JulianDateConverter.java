package com.anniyamtech.bbps.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Component;

@Component
public class JulianDateConverter {

	/*
	 * @Value("${agentInstitutionId}") 
	 */
	static	String agentId  = "KM21";

	private static String minuteCalc(int mm) {

		String m = Integer.toString(mm);
		StringBuilder sb = new StringBuilder();

		if (m.length() == 1) {
			sb.append("0");
			return new String(sb + m);
		} else {
			return m;
		}
	}

	private static String dayOfTheYear(int day) {

		String d = Integer.toString(day);
		StringBuilder sb = new StringBuilder();

		if (d.length() == 2) {

			sb.append("0");

			return new String(sb + d);

		} else if (d.length() == 1) {
			sb.append("00");
			return new String(sb + d);
		} else {
			return d;
		}

	}

	public static String generationLogic() {

		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		// String AlphaNumericString="^[a-zA-Z0-9_]*$";

		// Pattern p=Pattern.compile(AlphaNumericString);
		int n = 27;
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));

		}
		String Y = new SimpleDateFormat("yy").format(new Date());
		String julianDateString = Y.substring(1);

		Calendar date = Calendar.getInstance();
		int day = LocalDate.now().getDayOfYear();
		int hour = date.get(Calendar.HOUR_OF_DAY);
		int minute = date.get(Calendar.MINUTE);

		String mm = minuteCalc(minute);
		String doy = dayOfTheYear(day);
		String julian = julianDateString + doy + hour + mm;

		return sb + julian;

	}

	public static String generationLogic2() {

		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		int n = 12;
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));

		}
		String Y = new SimpleDateFormat("yy").format(new Date());
		String julianDateString = Y.substring(1);
		int day = LocalDate.now().getDayOfYear();
		String doy = dayOfTheYear(day);
		
		String julian =julianDateString+doy;

		//String julian = doy + julianDateString;
		System.out.println(agentId);
		return agentId + julian+ sb ;
	}

	public static String dateFormatter() throws ParseException, DatatypeConfigurationException {
		
		  SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			GregorianCalendar gc = new GregorianCalendar();
			String dateString = sdf1.format(gc.getTime());
			gc.setTime(sdf1.parse(dateString));
			XMLGregorianCalendar text = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
			String date = text.toString();
			OffsetDateTime odt = OffsetDateTime.parse( date );
			OffsetDateTime odtTruncatedToWholeSecond = odt.truncatedTo( ChronoUnit.SECONDS );
		    String formattedDate=odtTruncatedToWholeSecond.toString();
		return formattedDate;
		
		
	}
	
	
	/*
	 * public static void main(String[] args) {
	 * 
	 * 
	 * System.out.println(generationLogic());
	 * System.out.println(generationLogic2()); }
	 */

}
