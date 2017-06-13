package com.councel.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.cloudinary.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.councel.model.pojo.Client;
import com.councel.model.pojo.ClientAppointments;
import com.councel.model.pojo.Lawyer;
import com.councel.model.pojo.User;
import com.councel.model.pojo.User.UserType;
import com.councel.model.service.CalendarService;
import com.councel.model.service.LawyerService;
import com.councel.model.service.UserService;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

@Path("/lawyers")
public class LawyerManagementService {

	@Path("/getLawyers.json")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Object findLawyers(String inputJSON) {
		String input = null;
		List<Lawyer> lawyers = null;
		if (inputJSON != null && inputJSON.trim().length() > 0) {
			JSONParser parser = new JSONParser();

			JSONObject jo;
			try {
				jo = (JSONObject) parser.parse(inputJSON);
				if (jo.containsKey("input")) {
					input = (String) jo.getOrDefault("input", "");
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		lawyers = LawyerService.findLawyers(input);
		return lawyers;
	}

	
	@Path("/getClientAppointments.json")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Object getClientAppointments(String inputJSON) {
		Long lawyerId = null;
		List<ClientAppointments> appointments = null;
		JSONArray ja = new JSONArray();
		if (inputJSON != null && inputJSON.trim().length() > 0) {
			JSONParser parser = new JSONParser();

			JSONObject jo;
			try {
				jo = (JSONObject) parser.parse(inputJSON);
				if (jo.containsKey("lawyerId")) {
					lawyerId = (Long) jo.getOrDefault("lawyerId", 0);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		appointments = LawyerService.findClientAppointments(lawyerId);
		ISO8601DateFormat iso = new ISO8601DateFormat();
		for (ClientAppointments appointment : appointments) {
			JSONObject appJ = new JSONObject();
			appJ.put("id", appointment.getAppointmentId());
			appJ.put("start", iso.format(appointment.getAppointmentStartTime()));
			appJ.put("end", iso.format(appointment.getAppointmentEndTime()));
			appJ.put("availableToClients", appointment.getAvailableToClients());
			ja.put(appJ);
		}
		
		
		return ja.toString();
	}
	
	@Path("/bookAppointments.json")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Object bookAppointments(String inputJSON) {
		Long lawyerId = null;
		Long clientId = null;
		String slotStr = null;
		String purpose = null;
		List<ClientAppointments> appointments = null;
		JSONObject appObj = new JSONObject();
		if (inputJSON != null && inputJSON.trim().length() > 0) {
			JSONParser parser = new JSONParser();

			JSONObject jo;
			try {
				jo = (JSONObject) parser.parse(inputJSON);
				lawyerId = (Long)jo.getOrDefault("lawyerId", 0L);
				clientId = (Long)jo.getOrDefault("clientId", 0L);
				slotStr = (String)jo.getOrDefault("slot", "");
				purpose = (String)jo.getOrDefault("purpose", "");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		appointments = LawyerService.findClientAppointments(lawyerId);
		
		ClientAppointments cai = new ClientAppointments();
		
		Lawyer lawyer = (Lawyer) UserService.findUser(lawyerId);
		Client client  = (Client) UserService.findUser(clientId);
		cai.setLawyer(lawyer);
		cai.setClient(client);
		cai.setPurpose(purpose);
		
		ISO8601DateFormat iso = new ISO8601DateFormat();
		try {
			Date startDate = iso.parse(slotStr);
			Timestamp startTime = new Timestamp(startDate.getTime());
			cai.setAppointmentStartTime(startTime);
			
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean appointmentBooked= false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(cai.getAppointmentStartTime());
		cal.add(Calendar.MINUTE, 5);
		
		Timestamp endTime = new Timestamp(cal.getTimeInMillis());
		
		for (ClientAppointments appointment : appointments) {
			Timestamp startTime = cai.getAppointmentStartTime();
			if(startTime.after(appointment.getAppointmentStartTime()) && endTime.before(appointment.getAppointmentEndTime())){
				cai.setAppointmentEndTime(endTime);
				CalendarService.createAppointment(cai);
				appointmentBooked = true;
				break;
			}
		}
		
			appObj.put("appointmentBooked", appointmentBooked);
		
		return appObj.toString();
	}
	
}
