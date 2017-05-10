package com.councel.model.service;

import com.councel.model.pojo.Client;
import com.councel.model.pojo.Lawyer;
import com.councel.model.pojo.User;
import com.councel.model.utils.DBUtils;

public class UserService {

	public static Long createUser(User user){
		Long userId =(Long) DBUtils.save(user);
		return userId;
	}
	
	public static void updateUser(User user){
		DBUtils.update(user);
	}
	
	public static void updateLawyer(Lawyer user){
		DBUtils.update(user);
	}
	
	public static void updateClient(Client user){
		DBUtils.update(user);
	}
	
	public static void deleteUser(User user){
		DBUtils.delete(user);
	}
	
	public static User findUser(Long userId){
		User res = null;
		res = DBUtils.findByKey(User.class, userId);
		return res;
	}
	
	
}
