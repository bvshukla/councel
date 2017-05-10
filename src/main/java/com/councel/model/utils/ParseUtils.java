package com.councel.model.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.councel.model.pojo.Client;
import com.councel.model.pojo.Lawyer;
import com.councel.model.pojo.User;
import com.councel.model.pojo.User.UserType;

public class ParseUtils {

	String firstName;
	String lastName;
	String address;
	String mobileNo;
	String email;
	String password;
	String userTypeStr;
	String lawFirm;
	String courtName;
	
	public User parseUser(String jsonInput) throws ParseException{

		JSONParser parser = new JSONParser();
		JSONObject jo = (JSONObject)parser.parse(jsonInput);
		
		firstName = (String)jo.getOrDefault("firstName","");
		lastName = (String)jo.getOrDefault("lastName","");
		address = (String)jo.getOrDefault("address","");
		mobileNo = (String)jo.getOrDefault("mobileNo","");
		email = (String)jo.getOrDefault("email","");
		password = (String)jo.getOrDefault("password","");
		userTypeStr = (String)jo.getOrDefault("userType","Client");
		lawFirm = (String)jo.getOrDefault("lawFirm","");
		courtName = (String)jo.getOrDefault("courtName","");
		
		UserType userType = UserType.valueOf(userTypeStr.toUpperCase());
		
		if(userType == UserType.LAWYER){
			Lawyer lawyer = new Lawyer();
			lawyer.setFirstName(firstName);
			lawyer.setLastName(lastName);
			lawyer.setAddress(address);
			lawyer.setMobileNo(mobileNo);
			lawyer.setEmail(email);
			lawyer.setPassword(password);
			lawyer.setLawFirm(lawFirm);
			lawyer.setCourtName(courtName);
			return lawyer;
		}else{
			Client cl = new Client();
			cl.setFirstName(firstName);
			cl.setLastName(lastName);
			cl.setAddress(address);
			cl.setMobileNo(mobileNo);
			cl.setEmail(email);
			cl.setPassword(password);
			return cl;
		}

	}
}
