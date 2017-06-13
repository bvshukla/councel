package com.councel.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

public class Tester {

	public static void main(String[] args) throws ParseException {
		String startTime = "2017-05-27T18:55:00.000+05:30";
		
		ISO8601DateFormat iso = new ISO8601DateFormat();

		iso.setTimeZone(TimeZone.getTimeZone("IST"));
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		
		cal.setTime(iso.parse(startTime));
		
		Timestamp startTimeVal = new Timestamp(cal.getTime().getTime());
		
		System.out.println(cal.getTime());
		System.out.println(startTimeVal);
	}

}
