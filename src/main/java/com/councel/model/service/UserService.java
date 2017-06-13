package com.councel.model.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import com.councel.model.pojo.Client;
import com.councel.model.pojo.ClientAppointments;
import com.councel.model.pojo.ClientInfo;
import com.councel.model.pojo.Lawyer;
import com.councel.model.pojo.User;
import com.councel.model.utils.DBUtils;

public class UserService {

	public static Long createUser(User user) {
		Long userId = (Long) DBUtils.save(user);
		return userId;
	}

	public static void updateUser(User user) {
		DBUtils.update(user);
	}

	public static void updateLawyer(Lawyer user) {
		DBUtils.update(user);
	}

	public static void updateClient(Client user) {
		DBUtils.update(user);
	}

	public static void deleteUser(User user) {
		DBUtils.delete(user);
	}

	public static User findUser(Long userId) {
		User res = null;
		res = DBUtils.findByKey(User.class, userId);
		return res;
	}

	public static List<ClientInfo> loadClientInfo(Long userId) {
		String queryStr = "select u.userId as Id,u.firstName as clientName, u.mobile as phoneNo  from user u join client c on (u.userId = c.userId) where c.lawyer_userId = :userId";

		Session session = DBUtils.factory.openSession();
		NativeQuery<ClientInfo> query = session.createNativeQuery(queryStr, ClientInfo.class);
		query.setParameter("userId", userId);
		List<ClientInfo> results = query.getResultList();
		session.close();
		return results;
	}

	public static Lawyer findLawyer(Long userId, User user) {
		List<ClientInfo> clients = loadClientInfo(userId);
		Lawyer lawyer = (Lawyer) user;
		if (clients != null) {
			int cnt = clients.size();
			lawyer.setClientsCount(cnt);
			lawyer.setClientsInfo(clients);
		}
		return lawyer;
	}

	public static User findUserFromEmailOrPhone(String emailId, String phnNo) {
		Session session = DBUtils.factory.openSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(User.class);
		query.select(root);
		if (emailId != null) {
			query.where(builder.equal(root.get("email"), emailId));
		}
		if (phnNo != null) {
			query.where(builder.equal(root.get("mobileNo"), phnNo));
		}
		User user = null;
		try {
			user = em.createQuery(query).getSingleResult();
		} catch (Exception e) {

		}
		session.close();
		return user;
	}
	
	public static List<ClientAppointments> findClientAppointments(Lawyer lawyer, Client client, Timestamp startTime, Timestamp endTime) {
		Session session = DBUtils.factory.openSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ClientAppointments> query = builder.createQuery(ClientAppointments.class);
		Root<ClientAppointments> root = query.from(ClientAppointments.class);
		
		query.select(root);
		if (lawyer != null) {
			query.where(builder.equal(root.get("lawyer"), lawyer.getUserId()));
		}
		if (client != null) {
			query.where(builder.equal(root.get("client"), client.getUserId()));
		}
		if(startTime != null){
			query.where(builder.greaterThan(root.<Timestamp>get("appointmentEndTime"), startTime));
		}
		if(endTime != null){
			query.where(builder.lessThan(root.<Timestamp>get("appointmentEndTime"), endTime));
		}
		query.orderBy(builder.asc(root.<Timestamp>get("appointmentStartTime")));
		
		List<ClientAppointments> appointments = null;
		try {
			appointments = em.createQuery(query).getResultList();
		} catch (Exception e) {

		}
		session.close();
		return appointments;
	}
	
	public static void main(String[] args){
		Lawyer lawyer = (Lawyer) UserService.findUser(266L);
		List<ClientAppointments> cas = UserService.findClientAppointments(lawyer, null, null, null);
		System.out.println(cas);
	}
	
}
