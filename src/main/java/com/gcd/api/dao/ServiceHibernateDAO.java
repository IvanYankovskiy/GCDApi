package com.gcd.api.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ServiceHibernateDAO {
    @Autowired(required = false)
    SessionFactory sessionFactory;
    
    protected Session getCurrentSession(){
      return sessionFactory.getCurrentSession();
    }
    
}
