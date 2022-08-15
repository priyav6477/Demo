package com.anniyamtech.bbps.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.anniyamtech.bbps.responseDto.BillerListResponseDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DateFormatter  {

	public static void method()throws ParseException, DatatypeConfigurationException {
		// TODO Auto-generated method stub
		  String json = "{\"head\":{\"refId\":\"hvHpCfomjOHDGVu03qlCjZP15ia20881231\",\"origInst\":\"KM21\",\"ts\":\"2022-03-29T12:31:35\",\"ver\":\"1.0\"},\"reason\":{\"responseCode\":\"000\",\"responseReason\":\"Successful\"},\"billers\":[]}";
		    Gson gson=  new GsonBuilder().setDateFormat("dd-MM-yyyy").create();

		    BillerListResponseDto response = gson.fromJson(json,  BillerListResponseDto.class);
		    System.out.println(response);
		    
		    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			GregorianCalendar gc = new GregorianCalendar();
			String dateString = sdf1.format(gc.getTime());
			gc.setTime(sdf1.parse(dateString));
			XMLGregorianCalendar text = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
			String date = text.toString();
			System.out.println(">>>>>>>>"+date+"<<<<<<<<<");
			OffsetDateTime odt = OffsetDateTime.parse( date );
			OffsetDateTime odtTruncatedToWholeSecond = odt.truncatedTo( ChronoUnit.SECONDS );
			System.out.println(">>>>>>>>"+odtTruncatedToWholeSecond+"<<<<<<<<<");
			
	}

}
