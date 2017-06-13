package com.councel.model.utils;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

public class DBUtils {

	public static SessionFactory factory;
	private static String packageName = "com.councel.model.pojo";
	static {
		SchemaTranslator st = new SchemaTranslator();
		Configuration conf = new Configuration().configure().addPackage(packageName); 

		try {
			for (Class clazz : st.getClasses(packageName)) {

				conf.addAnnotatedClass(clazz);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		factory = conf.buildSessionFactory();
	}

	public static Object save(Object object) {
		Session session = factory.openSession();
		Transaction tx = null;
		Object id = null;
		try {
			tx = session.beginTransaction();
			id = session.save(object);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return id;
	}

	public static void update(Object object) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(object);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static void saveOrupdate(Object object) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(object);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static void delete(Object object) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(object);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static <T> T findByKey(Class<T> entityClass, Object primaryKey) {
		Session session = factory.openSession();
		Transaction tx = null;
		T res = null;
		try {
			tx = session.beginTransaction();
			res = session.find(entityClass, primaryKey);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return res;
	}



	
	public static void main(String[] args) {

	}
}
