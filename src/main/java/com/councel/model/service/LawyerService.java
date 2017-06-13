package com.councel.model.service;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import com.councel.model.pojo.ClientAppointments;
import com.councel.model.pojo.Lawyer;
import com.councel.model.utils.DBUtils;

public class LawyerService {

	public static List<Lawyer> findLawyers(String input) {
		Session session = DBUtils.factory.openSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Lawyer> query = builder.createQuery(Lawyer.class);
		Root<Lawyer> root = query.from(Lawyer.class);

		query.select(root);
		if (input != null) {
			query.where(builder.like(root.<String>get("lawFirm"), input));

			query.where(builder.like(root.<String>get("courtName"), input));

			query.where(builder.like(root.<String>get("location"), input));

			query.where(builder.like(root.<String>get("contactFor"), input));
		}

		query.orderBy(builder.desc(root.<Long>get("userId")));

		List<Lawyer> lawyers = null;
		try {
			lawyers = em.createQuery(query).getResultList();
		} catch (Exception e) {

		}
		session.close();
		return lawyers;
	}

	public static List<ClientAppointments> findClientAppointments(Long lawyerId) {
		List<ClientAppointments> appointments = null;
		if(lawyerId == null){
			return appointments;
		}
		Session session = DBUtils.factory.openSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ClientAppointments> query = builder.createQuery(ClientAppointments.class);
		Root<ClientAppointments> root = query.from(ClientAppointments.class);

		query.select(root);
			query.where(builder.equal(root.<Long>get("lawyer"), lawyerId));
			
			query.where(builder.equal(root.<Boolean>get("availableToClients"), true));
			query.where(builder.or(root.get("client").isNotNull()));
			query.where(builder.greaterThanOrEqualTo(root.<Timestamp>get("appointmentEndTime"), new Timestamp(System.currentTimeMillis())));
		

		query.orderBy(builder.desc(root.<Timestamp>get("appointmentStartTime")));

		
		try {
			appointments = em.createQuery(query).getResultList();
		} catch (Exception e) {

		}
		session.close();
		return appointments;
	}
	
}
