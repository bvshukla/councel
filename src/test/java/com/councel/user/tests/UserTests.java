package com.councel.user.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.councel.model.pojo.Client;
import com.councel.model.pojo.Lawyer;
import com.councel.model.pojo.User.UserType;
import com.councel.model.service.UserService;
import com.councel.model.utils.DBUtils;

import junit.framework.TestCase;

public class UserTests extends TestCase {

	private  Lawyer lawyer ;
	private  Client client;

	private  Lawyer tlawyer ;
	private  Client tclient;
	
	private  Lawyer dlawyer ;
	private  Client dclient;
	
	public UserTests(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		
		lawyer = new Lawyer();
		lawyer.setFirstName("vatsal");
		lawyer.setLastName("shukla");
		lawyer.setEmail("bhaktavatsalshukla@gmail.com");
		lawyer.setAddress("Hyderabad");
		lawyer.setCourtName("Cyberabad");
		lawyer.setLawFirm("H&M");
		lawyer.setMobile("9573545118");
		lawyer.setPassword("1234");
		
		client = new Client();
		client.setFirstName("pulkit");
		client.setLastName("sharma");
		client.setEmail("pulkit.sharma@gmail.com");
		client.setAddress("Hyderabad");
		client.setMobile("7893922700");
		client.setPassword("1234");
		
		tlawyer = new Lawyer();
		tlawyer.setFirstName("vatsal");
		tlawyer.setLastName("shukla");
		tlawyer.setEmail("bhaktavatsalshukla1@gmail.com");
		tlawyer.setAddress("Hyderabad");
		tlawyer.setCourtName("Cyberabad");
		tlawyer.setLawFirm("H&M");
		tlawyer.setMobile("9573545118");
		tlawyer.setPassword("1234");
		
		
		tclient = new Client();
		tclient.setFirstName("pulkit");
		tclient.setLastName("sharma");
		tclient.setEmail("pulkit.sharma1@gmail.com");
		tclient.setAddress("Hyderabad");
		tclient.setMobile("7893922700");
		tclient.setPassword("1234");
		
		dlawyer = new Lawyer();
		dlawyer.setFirstName("vatsal");
		dlawyer.setLastName("shukla");
		dlawyer.setEmail("bhaktavatsalshukla2@gmail.com");
		dlawyer.setAddress("Hyderabad");
		dlawyer.setCourtName("Cyberabad");
		dlawyer.setLawFirm("H&M");
		dlawyer.setMobile("9573545118");
		dlawyer.setPassword("1234");
		
		dclient = new Client();
		dclient.setFirstName("pulkit");
		dclient.setLastName("sharma");
		dclient.setEmail("pulkit.sharma2@gmail.com");
		dclient.setAddress("Hyderabad");
		dclient.setMobile("7893922700");
		dclient.setPassword("1234");
		
		UserService.createUser(tlawyer);
		UserService.createUser(tclient);
		UserService.createUser(dlawyer);
		UserService.createUser(dclient);
		super.setUp();
	}

	protected void tearDown() throws Exception {
		UserService.deleteUser(tlawyer);
		UserService.deleteUser(tclient);
		//UserService.deleteUser(dlawyer);
		//UserService.deleteUser(dclient);
		UserService.deleteUser(lawyer);
		UserService.deleteUser(client);
		super.tearDown();
	}
	
	

	public void testCreateUser() {
		
		Long clientId = UserService.createUser(client);
		
		assertNotNull("Client Not Saved successfully", clientId);
		
		
		Long lawyerId = UserService.createUser(lawyer);
		assertNotNull("Lawyer Not Saved successfully", lawyerId);
		
	}

	public void testUpdateUser() {

		List<Client> l = new ArrayList<Client>();
		l.add(tclient);
		//tlawyer.setClients(l);

		UserService.updateLawyer(tlawyer);
		
		Long clientId = tclient.getUserId();
		tclient = (Client) UserService.findUser(clientId);
		
		assertNotNull("lawyer not assigned", tclient.getLawyer());
	}

	public void testDeleteUser() {
		Long clientId = dclient.getUserId();
		Long lawyerId = dlawyer.getUserId();
		
		UserService.deleteUser(dclient);
		UserService.deleteUser(dlawyer);
		
		dclient = (Client) UserService.findUser(clientId);
		dlawyer = (Lawyer) UserService.findUser(lawyerId);
		assertNull("Client not deleted", dclient);
		assertNull("Lawyer not deleted", dlawyer);
	}

	public void testFindUser() {
		fail("Not yet implemented");
	}

}
