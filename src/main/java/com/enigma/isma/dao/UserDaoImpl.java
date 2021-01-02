package com.enigma.isma.dao;

import java.security.interfaces.ECKey;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import com.enigma.isma.config.HibernateConfiguration;
import com.enigma.isma.entity.User;

public class UserDaoImpl implements UserDao {

	Session session;
	CriteriaBuilder criteriaBuilder;

	public UserDaoImpl() {
		this.session = HibernateConfiguration.getSessionFactory().openSession();
		criteriaBuilder = session.getCriteriaBuilder();

	}

	@Override
	public String saveMember(User user) {
		String status;
		try {
			this.session.beginTransaction();
			this.session.save(user);
			session.getTransaction().commit();
			status= "Success";
		}catch (Exception e){
			status="failed";
		}
		return status;
	}

	@Override
	public List<User> getAllMember() {
		List<User> users;
		try {
			session.beginTransaction();
			users = session.createQuery("from User").getResultList();
			session.getTransaction().commit();
		}catch (Exception e){
			users=null;
		}

		return users;
	}

	@Override
	public User getMemberById(String id) {
	User user;
	try {
		CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
		Root<User> root = query.from(User.class);
		query.where(
				criteriaBuilder.like(root.get("id"), id)
		);

		user=session.createQuery(query).getSingleResult();
	}catch(Exception e){
		user=null;
	}
	return user;
	}

	@Override
	public User getMemberByName(String nameMember) {
	User user;
	try {
		CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
		Root<User> root = query.from(User.class);
		query.where(criteriaBuilder.like(root.get("name"), nameMember));

		user=session.createQuery(query).getSingleResult();
	}catch (Exception e){
		user = null;
	}
	return user;
	}

	@Override
	public User login(String email, String password) {
		User user;
		try {
			CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
			Root<User> root = query.from(User.class);

			List<Predicate> predicates = new ArrayList<>();

			predicates.add(criteriaBuilder.equal(root.get("email"),email));

			// equal
			predicates.add(criteriaBuilder.equal(root.get("password"), password));

			query.where(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));
			user = session.createQuery(query).getSingleResult();
		}catch (Exception e){
			user=null;
		}

		return user;
	}
}
