package com.councel.model.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.councel.model.pojo.Client;
import com.councel.model.pojo.Lawyer;
import com.councel.model.pojo.User;
import com.councel.model.pojo.User.UserType;

public class ParseUtils {

	Long userId;
	String firstName;
	String lastName;
	String address;
	String mobileNo;
	String email;
	String password;
	String userTypeStr;
	String lawFirm;
	String courtName;
	String gender;
	String dob;
	
	String location;
	String contactFor;
	String experience;
	String bio;
	
	public User parseUser(String jsonInput) throws ParseException{

		JSONParser parser = new JSONParser();
		JSONObject jo = (JSONObject)parser.parse(jsonInput);
		
		firstName = (String)jo.getOrDefault("firstName","");
		lastName = (String)jo.getOrDefault("lastName","");
		address = (String)jo.getOrDefault("address","");
		mobileNo = (String)jo.getOrDefault("mobile","");
		email = (String)jo.getOrDefault("email","");
		password = (String)jo.getOrDefault("password","");
		userTypeStr = (String)jo.getOrDefault("userType","Client");
		lawFirm = (String)jo.getOrDefault("lawFirm","");
		courtName = (String)jo.getOrDefault("courtName","");
		userId = (Long)jo.getOrDefault("userId",0L);
		gender = (String)jo.getOrDefault("gender","");
		dob = (String)jo.getOrDefault("dob","");
		location = (String)jo.getOrDefault("location","");
		contactFor = (String)jo.getOrDefault("contactFor","");
		experience = (String)jo.getOrDefault("experience","");
		bio = (String)jo.getOrDefault("bio","");
		
		UserType userType = UserType.valueOf(userTypeStr.toUpperCase());
		password = getMD5Hash(password);
		
		if(userType == UserType.LAWYER){
			Lawyer lawyer = new Lawyer();
			lawyer.setUserId(userId);
			lawyer.setFirstName(firstName);
			lawyer.setLastName(lastName);
			lawyer.setAddress(address);
			lawyer.setMobile(mobileNo);
			lawyer.setEmail(email);
			lawyer.setPassword(password);
			lawyer.setLawFirm(lawFirm);
			lawyer.setCourtName(courtName);
			lawyer.setGender(gender);
			lawyer.setDob(dob);
			lawyer.setLocation(location);
			lawyer.setContactFor(contactFor);
			lawyer.setExperience(experience);
			lawyer.setBio(bio);
			return lawyer;
		}else{
			Client cl = new Client();
			cl.setUserId(userId);
			cl.setFirstName(firstName);
			cl.setLastName(lastName);
			cl.setAddress(address);
			cl.setMobile(mobileNo);
			cl.setEmail(email);
			cl.setPassword(password);
			cl.setGender(gender);
			cl.setDob(dob);
			return cl;
		}

	}
	
	public static String getMD5Hash(String inputStr){
		
		StringBuffer hexString = new StringBuffer();
		
		MessageDigest md;
		try {
			byte[] bytesOfMessage = inputStr.getBytes("UTF-8");
			md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(bytesOfMessage);
			
			for (int i = 0; i < hash.length; i++) {
			    if ((0xff & hash[i]) < 0x10) {
			        hexString.append("0"
			                + Integer.toHexString((0xFF & hash[i])));
			    } else {
			        hexString.append(Integer.toHexString(0xFF & hash[i]));
			    }
			}
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = hexString.toString();
		return result;
	}
}
