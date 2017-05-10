package com.councel.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.councel.model.pojo.Client;
import com.councel.model.pojo.Lawyer;
import com.councel.model.pojo.User;
import com.councel.model.service.UserService;
import com.councel.model.utils.ParseUtils;

@Path("/client")
public class ClientManagementService {
	

	@Path("/findUser.json")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object findUser(@QueryParam("userId")Long userId){
		User user = UserService.findUser(userId);
		return user;
	}
	
	@Path("/createUser.json")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object createUser(String inputJSON){
		
		ParseUtils pu = new ParseUtils();
		try {
			User user = pu.parseUser(inputJSON);
			if(user instanceof Lawyer){
				Lawyer lawyer = (Lawyer)user;
				UserService.createUser(lawyer);
				return lawyer;
			}else{
				Client cl = (Client)user;
				UserService.createUser(cl);
				return cl;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{}";
		
	}
	
	@Path("/assignClient.json")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object assignClient(String inputJSON){
		
		JSONParser parser = new JSONParser();
		Client clR = null;
		try {
			JSONObject jo = (JSONObject)parser.parse(inputJSON);
			Long lawyerId = (Long)jo.getOrDefault("lawyerId", 0L);
			Long clientId = (Long)jo.getOrDefault("clientId", 0L);
			
			Lawyer lawyer = (Lawyer) UserService.findUser(lawyerId);
			Client cl = (Client) UserService.findUser(clientId);
			
			cl.setLawyer(lawyer);			
			UserService.updateClient(cl);
			
			clR = (Client) UserService.findUser(clientId);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clR;
		
	}
}
