package com.councel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.councel.model.pojo.Client;
import com.councel.model.pojo.Lawyer;
import com.councel.model.pojo.ServiceError;
import com.councel.model.pojo.User;
import com.councel.model.pojo.User.UserType;
import com.councel.model.service.UserService;
import com.councel.model.utils.ErrorCode;
import com.councel.model.utils.ErrorMsg;
import com.councel.model.utils.ErrorUtils;
import com.councel.model.utils.FileUtils;
import com.councel.model.utils.ParseUtils;

@Path("/client")
public class UserManagementService {

	public static final String UPLOAD_FILE_SERVER = "C:\\Users\\bvshukla.ORADEV\\workspace\\councelApp\\src\\main\\webapp\\ProfilePic\\";

	@Path("/findUser.json")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object findUser(@QueryParam("userId") Long userId) {
		User user = UserService.findUser(userId);
		if (user != null && user.getUserType() == UserType.LAWYER) {
			user = UserService.findLawyer(userId, user);
		}
		return user;
	}

	@Path("/createUser.json")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object createUser(String inputJSON) {

		ParseUtils pu = new ParseUtils();
		try {
			User user = pu.parseUser(inputJSON);
			if (user instanceof Lawyer) {
				Lawyer lawyer = (Lawyer) user;
				UserService.createUser(lawyer);
				return lawyer;
			} else {
				Client cl = (Client) user;
				UserService.createUser(cl);
				return cl;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{}";

	}

	@Path("/updateUser.json")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object updateUser(String inputJSON) {

		JSONParser parser = new JSONParser();
		JSONObject jo;
		try {
			jo = (JSONObject) parser.parse(inputJSON);

			Long userId = (Long) jo.getOrDefault("userId", 0L);
			User u = UserService.findUser(userId);
			Lawyer lawyer = null;
			Client client = null;

			String userTypeStr = (String) jo.getOrDefault("userType", "Client");
			UserType userType = UserType.valueOf(userTypeStr.toUpperCase());

			if (jo.containsKey("firstName")) {
				String firstName = (String) jo.getOrDefault("firstName", "");
				u.setFirstName(firstName);
			}

			if (jo.containsKey("lastName")) {
				String lastName = (String) jo.getOrDefault("lastName", "");
				u.setLastName(lastName);
			}

			if (jo.containsKey("address")) {
				String address = (String) jo.getOrDefault("address", "");
				u.setAddress(address);
			}

			if (jo.containsKey("mobile")) {
				String mobile = (String) jo.getOrDefault("mobile", "");
				u.setMobile(mobile);
			}

			if (jo.containsKey("email")) {
				String email = (String) jo.getOrDefault("email", "");
				u.setEmail(email);
			}

			if (jo.containsKey("gender")) {
				String gender = (String) jo.getOrDefault("gender", "");
				u.setGender(gender);
			}

			if (jo.containsKey("dob")) {
				String dob = (String) jo.getOrDefault("dob", "");
				u.setDob(dob);
			}

			if (userType == UserType.LAWYER) {
				lawyer = (Lawyer) u;
				if (jo.containsKey("lawFirm")) {
					String lawFirm = (String) jo.getOrDefault("lawFirm", "");
					lawyer.setLawFirm(lawFirm);
				}
				if (jo.containsKey("courtName")) {
					String courtName = (String) jo.getOrDefault("courtName", "");
					lawyer.setCourtName(courtName);
				}
				if (jo.containsKey("location")) {
					String location = (String) jo.getOrDefault("location", "");
					lawyer.setLocation(location);
				}
				if (jo.containsKey("contactFor")) {
					String contactFor = (String) jo.getOrDefault("contactFor", "");
					lawyer.setContactFor(contactFor);
				}
				if (jo.containsKey("experience")) {
					String experience = (String) jo.getOrDefault("experience", "");
					lawyer.setExperience(experience);
				}
				if (jo.containsKey("bio")) {
					String bio = (String) jo.getOrDefault("bio", "");
					lawyer.setBio(bio);
				}
				UserService.updateLawyer(lawyer);
				return lawyer;
			} else {
				Client cl = (Client) u;
				UserService.updateClient(cl);
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
	public Object assignClient(String inputJSON) {

		JSONParser parser = new JSONParser();
		Client clR = null;
		try {
			JSONObject jo = (JSONObject) parser.parse(inputJSON);
			Long lawyerId = (Long) jo.getOrDefault("lawyerId", 0L);
			Long clientId = (Long) jo.getOrDefault("clientId", 0L);

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

	@Path("/login.json")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object login(String inputJSON) {

		JSONParser parser = new JSONParser();
		Client clR = null;
		try {
			JSONObject jo = (JSONObject) parser.parse(inputJSON);
			String email = (String) jo.getOrDefault("email", null);
			String mobile = (String) jo.getOrDefault("mobileNo", null);
			String password = (String) jo.getOrDefault("password", null);

			User user = UserService.findUserFromEmailOrPhone(email, mobile);

			if (user == null) {
				ServiceError error = new ServiceError();
				error.setErrorCode(ErrorCode.USER_NOT_REGISTERED);
				error.setErrorMsg(ErrorMsg.USER_NOT_REGISTERED);
				error.setErrorId(ErrorUtils.errorIds.get(ErrorCode.USER_NOT_REGISTERED));
				return error;
			} else {

				String passwordStr = ParseUtils.getMD5Hash(password);
				if (user.getPassword().equals(passwordStr)) {
					return user;
				} else {
					ServiceError error = new ServiceError();
					error.setErrorCode(ErrorCode.USER_PASSWORD_INCORRECT);
					error.setErrorMsg(ErrorMsg.USER_PASSWORD_INCORRECT);
					error.setErrorId(ErrorUtils.errorIds.get(ErrorCode.USER_PASSWORD_INCORRECT));
					return error;
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clR;
	}

	@Path("/upload.json")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Object uploadPic(@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileFormDataContentDisposition,
			@FormDataParam("userId") Long userId, @FormDataParam("fileType") String fileType) {

		String fileName = null;
		String uploadFilePath = null;

		try {
			fileName = fileType + "_" + userId + "_" + System.nanoTime() + ".jpg";
			uploadFilePath = writeToFileServer(fileInputStream, fileName);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			// release resources, if any
		}
		User user = UserService.findUser(userId);
		user.setProfilePic(uploadFilePath);
		if (user.getUserType() == UserType.LAWYER) {
			UserService.updateLawyer((Lawyer) user);
		} else {
			UserService.updateClient((Client) user);
		}

		return user;
	}

	private String writeToFileServer(InputStream inputStream, String fileName) throws IOException {

		OutputStream outputStream = null;
		String qualifiedUploadFilePath = fileName;
		File f = new File(qualifiedUploadFilePath);
		if (!f.exists()) {
			f.createNewFile();
		}

		try {

			outputStream = new FileOutputStream(f);
			IOUtils.copy(inputStream, outputStream);
			qualifiedUploadFilePath = FileUtils.uploadToCloudinary(f);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			// release resource, if any
			outputStream.close();
		}
		return qualifiedUploadFilePath;
	}
}
