package com.enigma.isma.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import com.enigma.isma.config.HibernateConfiguration;
import com.enigma.isma.entity.Category;

public class CategoryDaoImpl implements CategoryDao {

	Session session;
	CriteriaBuilder criteriaBuilder;

	public CategoryDaoImpl() {
		this.session = HibernateConfiguration.getSessionFactory().openSession();
		criteriaBuilder = session.getCriteriaBuilder();

	}

	@Override
	public String saveCategory(Category category) {
		// TODO Auto-generated method stub
		String status;
		try {
			this.session.beginTransaction();
			this.session.save(category);
			this.session.getTransaction().commit();
			status="Success";
		}catch (Exception e){
			status="Failed";
		}
		return status;
	}

	@Override
	public String updateCategory(Category category) {
		// TODO Auto-generated method stub
		String status;
		try {
			this.session.beginTransaction();
			this.session.update(category);
			this.session.getTransaction().commit();
			status="Success";
		}catch (Exception e){
			status="Failed";
		}
		return status;
	}

	@Override
	public List<Category> getAllCategories() {
		List<Category> categories;
		try {
			session.beginTransaction();
			categories = session.createQuery("from Category").getResultList();
			session.getTransaction().commit();
		}catch (Exception e){
			categories=null;
		}
		return categories;
	}

	@Override
	public Category getCategoryByName(String name) {
		Category category;
		try {
		CriteriaQuery<Category> query = criteriaBuilder.createQuery(Category.class);
		Root<Category> root = query.from(Category.class);
		query.where(criteriaBuilder.like(root.get("name"), name));
		category=session.createQuery(query).getSingleResult();
		}catch (Exception e){
			category=null;
		}
		return category;
	}

	@Override
	public Category getCategoryById(Integer id) {
		Category category;
		try {
		session.beginTransaction();
		category = session.get(Category.class, id);
		session.getTransaction().commit();
		}catch (Exception e){
			category=null;
		}
		return category;
	}

}
