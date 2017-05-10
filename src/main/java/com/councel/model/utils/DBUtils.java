package com.councel.model.utils;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import com.councel.model.pojo.Lawyer;
import com.councel.model.pojo.User;


public class DBUtils {
	
	private static SessionFactory factory; 
	private static String packageName = "com.councel.model.pojo";
	static{
		SchemaTranslator st = new SchemaTranslator();
		Configuration conf = new Configuration().
                configure().
                addPackage(packageName); //add package if used.
               
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
	
	public static Object save(Object object){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      Object id = null;
	      try{
		         tx = session.beginTransaction();
		         id = session.save(object); 
		         tx.commit();
		      }catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }finally {
		    	  session.close();
		      }
		return id;
	}
	
	public static void update(Object object){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      try{
		         tx = session.beginTransaction();
		         session.update(object);
		         tx.commit();
		      }catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }finally {
		    	  session.close();
		      }
	}
	
	public static void saveOrupdate(Object object){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      try{
		         tx = session.beginTransaction();
		         session.saveOrUpdate(object);
		         tx.commit();
		      }catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }finally {
		    	  session.close();
		      }
	}
	
	public static void delete(Object object){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      try{
		         tx = session.beginTransaction();
		         session.delete(object);
		         tx.commit();
		      }catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }finally {
		    	  session.close();
		      }
	}
	
	public static <T> T findByKey(Class<T> entityClass, Object primaryKey){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      T res = null;
	      try{
		         tx = session.beginTransaction();
		         res = session.find(entityClass, primaryKey);
		         tx.commit();
		      }catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }finally {
		    	  session.close();
		      }
	      return res;
	}
	
	
	public static <T> List<T> executeCustomSelectQuery(String queryStr, Class<T> className, Map<String,Object> paramMap){
		Session session = factory.openSession();
		NativeQuery query = session.createNativeQuery(queryStr,className);
		for(String key : paramMap.keySet()){
			query.setParameter(key, paramMap.get(key));
		}
		return query.list();		
	}
}
