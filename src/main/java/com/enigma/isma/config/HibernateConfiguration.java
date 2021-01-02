package com.enigma.isma.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfiguration {

	public static SessionFactory sessionFactory;

	public static SessionFactory buildSessionFactory() {
		Configuration configuration = new Configuration();
		sessionFactory = configuration.configure("hibernate.cfg.xml").buildSessionFactory();
		return sessionFactory;

	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null)
			return buildSessionFactory();
		return sessionFactory;

	}
}
