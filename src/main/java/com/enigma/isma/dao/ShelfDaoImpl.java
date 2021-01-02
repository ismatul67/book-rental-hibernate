package com.enigma.isma.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import com.enigma.isma.config.HibernateConfiguration;
import com.enigma.isma.entity.Shelf;

public class ShelfDaoImpl implements ShelfDao {

	Session session;
	CriteriaBuilder criteriaBuilder;

	public ShelfDaoImpl() {
		this.session = HibernateConfiguration.getSessionFactory().openSession();
		criteriaBuilder= session.getCriteriaBuilder();

	}

	@Override
	public String saveShelf(Shelf shelf) {
		// TODO Auto-generated method stub

		String status;
		try {
			this.session.beginTransaction();
			this.session.save(shelf);
			this.session.getTransaction().commit();
			status = "Success";
		}catch (Exception e){
			status="Failed";
		}
		return status;
	}

	@Override
	public List<Shelf> getAllShelf() {
		// TODO Auto-generated method stub
		List<Shelf> shelf;
		try {
			session.beginTransaction();
			shelf = session.createQuery("from Shelf").getResultList();
			session.getTransaction().commit();
		}catch (Exception e){
			shelf=null;
		}
		return shelf;
	}

	@Override
	public Shelf getShelfById(Integer id) {
		// TODO Auto-generated method stub
		Shelf shelf;

		try {
			session.beginTransaction();
			shelf = session.get(Shelf.class, id);
			session.getTransaction().commit();
		}catch (Exception e){
			shelf=null;
		}
		return shelf;
	}

}
