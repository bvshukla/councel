package com.councel.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.cloudinary.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.councel.model.pojo.Client;
import com.councel.model.pojo.ClientAppointments;
import com.councel.model.pojo.Lawyer;
import com.councel.model.service.CalendarService;
import com.councel.model.service.UserService;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.databind.util.ISO8601Utils;

@Path("/calendar")
public class CalendarManagementService {

	@Path("/getAppointments.json")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object getAppointments(String inputJSON) {
		List<ClientAppointments> appointments = null;
		JSONArray ja = new JSONArray();
		try {
			JSONParser parser = new JSONParser();

			JSONObject jo = (JSONObject) parser.parse(inputJSON);

			Long lawyerId = (Long) jo.getOrDefault("lawyerId", null);
			Long clientId = (Long) jo.getOrDefault("clientId", null);

			String startTime = (String) jo.getOrDefault("startTime", null);
			String endTime = (String) jo.getOrDefault("endTime", null);

			Lawyer lawyer = null;
			Client client = null;
			Timestamp startTimeVal = null;
			Timestamp endTimeVal = null;
			if (lawyerId != null) {
				lawyer = (Lawyer) UserService.findUser(lawyerId);
			}

			if (client != null) {
				client = (Client) UserService.findUser(clientId);
			}

			ISO8601DateFormat iso = new ISO8601DateFormat();
			if (startTime != null) {
				java.util.Date d =  iso.parse(startTime);
				startTimeVal = new Timestamp(d.getTime());
				
			}
			if (endTime != null) {
				endTimeVal = (Timestamp) iso.parse(endTime);
			}

			appointments = UserService.findClientAppointments(lawyer, client, startTimeVal, endTimeVal);

			for (ClientAppointments appointment : appointments) {
				JSONObject appJ = new JSONObject();
				appJ.put("id", appointment.getAppointmentId());
				appJ.put("title", appointment.getPurpose() + '-' + appointment.getVenue()
						+ (appointment.getClient() != null ? '-' + appointment.getClient().getEmail() : ""));
				appJ.put("start", iso.format(appointment.getAppointmentStartTime()));
				appJ.put("end", iso.format(appointment.getAppointmentEndTime()));
				appJ.put("venue", appointment.getVenue());
				appJ.put("purpose", appointment.getPurpose());
				appJ.put("client", appointment.getClient());
				appJ.put("availableToClients", appointment.getAvailableToClients());

				ja.put(appJ);

			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ja.toString();

	}

	@Path("/createAppointments.json")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object createAppointments(String inputJSON) {
		// List<ClientAppointments> appointments = null;
		JSONArray ja = new JSONArray();
		JSONObject appJ = new JSONObject();
		try {
			JSONParser parser = new JSONParser();

			JSONObject jo = (JSONObject) parser.parse(inputJSON);

			Long lawyerId = (Long) jo.getOrDefault("lawyerId", null);
			Long clientId = (Long) jo.getOrDefault("clientId", null);

			String startTime = (String) jo.getOrDefault("startTime", null);
			String endTime = (String) jo.getOrDefault("endTime", null);

			String purpose = (String) jo.getOrDefault("purpose", null);
			String venue = (String) jo.getOrDefault("venue", "Office");
			Boolean availableToClients = (Boolean) jo.getOrDefault("availableToClients", false);
			
			Lawyer lawyer = null;
			Client client = null;
			Timestamp startTimeVal = null;
			Timestamp endTimeVal = null;
			if (lawyerId != null) {
				lawyer = (Lawyer) UserService.findUser(lawyerId);
			}

			if (client != null) {
				client = (Client) UserService.findUser(clientId);
			}

			ISO8601DateFormat iso = new ISO8601DateFormat();

			iso.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
			if (startTime != null) {
				java.util.Date d = iso.parse(startTime);
				startTimeVal = new Timestamp(d.getTime());
			}
			if (endTime != null) {
				java.util.Date d = iso.parse(endTime);
				endTimeVal = new Timestamp(d.getTime());
			}

			ClientAppointments appointment = new ClientAppointments();
			appointment.setAppointmentEndTime(endTimeVal);
			appointment.setAppointmentStartTime(startTimeVal);
			appointment.setClient(client);
			appointment.setLawyer(lawyer);
			appointment.setVenue(venue);
			appointment.setPurpose(purpose);
			appointment.setAvailableToClients(availableToClients);
			CalendarService.createAppointment(appointment);

			appJ.put("id", appointment.getAppointmentId());
			appJ.put("title", appointment.getPurpose() + '-' + appointment.getVenue()
					+ (appointment.getClient() != null ? '-' + appointment.getClient().getEmail() : ""));
			appJ.put("start", iso.format(appointment.getAppointmentStartTime()));
			appJ.put("end", iso.format(appointment.getAppointmentEndTime()));
			appJ.put("venue", appointment.getVenue());
			appJ.put("purpose", appointment.getPurpose());
			appJ.put("availableToClients", appointment.getAvailableToClients());

			ja.put(appJ);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return appJ.toString();

	}

	@Path("/updateAppointments.json")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object updateAppointments(String inputJSON) {
		// List<ClientAppointments> appointments = null;
		JSONArray ja = new JSONArray();
		JSONObject appJ = new JSONObject();
		try {
			JSONParser parser = new JSONParser();

			JSONObject jo = (JSONObject) parser.parse(inputJSON);

			Long id = (Long) jo.getOrDefault("id", null);

			ClientAppointments appointment = CalendarService.findAppointment(id);
			ISO8601DateFormat iso = new ISO8601DateFormat();

			iso.setTimeZone(TimeZone.getTimeZone("GMT"));
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			if (jo.containsKey("startTime")) {

				Timestamp startTimeVal = null;
				String startTime = (String) jo.getOrDefault("startTime", null);
				if (startTime != null) {
					
					
					
					cal.setTime(iso.parse(startTime));
					
					startTimeVal = new Timestamp(cal.getTime().getTime());
				}
				appointment.setAppointmentStartTime(startTimeVal);
			}
			
			if(jo.containsKey("endTime")){
				String endTime = (String) jo.getOrDefault("endTime", null);
				Timestamp endTimeVal = null;

				if (endTime != null) {
					//java.util.Date d = iso.parse(endTime);
					cal.setTime(iso.parse(endTime));
					endTimeVal = new Timestamp(cal.getTime().getTime());
				}
				appointment.setAppointmentEndTime(endTimeVal);
			}
			
			if(jo.containsKey("purpose")){
				String purpose = (String) jo.getOrDefault("purpose", null);
				appointment.setPurpose(purpose);
			}
			
			
			if(jo.containsKey("venue")){
				String venue = (String) jo.getOrDefault("venue", "");
				appointment.setVenue(venue);
			}
			
			if(jo.containsKey("availableToClients")){
				Boolean availableToClients = (Boolean)jo.getOrDefault("availableToClients", false);
				appointment.setAvailableToClients(availableToClients);
			}
			appointment = CalendarService.updateAppointment(appointment);
			

			appJ.put("id", appointment.getAppointmentId());
			appJ.put("title", appointment.getPurpose() + '-' + appointment.getVenue()
					+ (appointment.getClient() != null ? '-' + appointment.getClient().getEmail() : ""));
			appJ.put("start", iso.format(appointment.getAppointmentStartTime()));
			appJ.put("end", iso.format(appointment.getAppointmentEndTime()));
			appJ.put("venue", appointment.getVenue());
			appJ.put("purpose", appointment.getPurpose());
			appJ.put("availableToClients", appointment.getAvailableToClients());

			ja.put(appJ);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return appJ.toString();

	}
	
	@Path("/deleteAppointments.json")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object deleteAppointments(String inputJSON) {
		// List<ClientAppointments> appointments = null;
		JSONArray ja = new JSONArray();
		JSONObject appJ = new JSONObject();
		try {
			JSONParser parser = new JSONParser();

			JSONObject jo = (JSONObject) parser.parse(inputJSON);

			Long id = (Long) jo.getOrDefault("id", null);

			ClientAppointments appointment = CalendarService.findAppointment(id);
			
			
			CalendarService.deleteAppointment(appointment);
			
			appJ.put("status", "success");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			appJ.put("status", "faliure");
		} 
		return appJ.toString();

	}
}
