package com.websystique.spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void persist(Object entity) {
	  //TODO which is better? Save exe's immediately and returns gen'd ID
	  //Can't call persist twice on an entity?  
//		getSession().persist(entity);
		getSession().saveOrUpdate(entity); 
	    
	}

	public void delete(Object entity) {
		getSession().delete(entity);
	}
}
