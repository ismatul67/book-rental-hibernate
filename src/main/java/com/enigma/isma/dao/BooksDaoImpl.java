package com.enigma.isma.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import com.enigma.isma.config.HibernateConfiguration;
import com.enigma.isma.entity.Books;

public class BooksDaoImpl implements BooksDao {

	Session session;
	CriteriaBuilder criteriaBuilder;

	public BooksDaoImpl() {
		this.session = HibernateConfiguration.getSessionFactory().openSession();
		criteriaBuilder = session.getCriteriaBuilder();

	}

	@Override
	public String saveBook(Books book) {
		// TODO Auto-generated method stub
		String status;
		try {
			this.session.beginTransaction();
			this.session.save(book);
			this.session.getTransaction().commit();
			status="Success";
		}catch (Exception e){
			status="Failed";
		}
		return status;
	}

	@Override
	public List<Books> getAllBooks() {
		// TODO Auto-generated method stub
		List<Books> books;
		try {
			session.beginTransaction();
			books = session.createQuery("from Books").getResultList();
			session.getTransaction().commit();
		}catch (Exception e){
			books=null;
		}
		return books;
	}

	@Override
	public String updateBook(Books book) {
		// TODO Auto-generated method stub
		String status;
		try {
			this.session.beginTransaction();
			this.session.update(book);
			this.session.getTransaction().commit();
			status="Success";
		}catch (Exception e){
			status="Failed";
		}
		return status;
	}

	@Override
	public List<Books> getBooksByTitle(String title) {
		List<Books> result;
	try {
		this.session.beginTransaction();
		CriteriaQuery<Books> query = criteriaBuilder.createQuery(Books.class);
		Root<Books> root = query.from(Books.class);

		query.where(
				criteriaBuilder.like(root.get("title"), title));
		result = session.createQuery(query).getResultList();
	}catch (Exception e){
		result=null;
	}
		return result;
	}

	@Override
	public Books getBookSingleTitle(String title) {
		Books book;
		try {
		CriteriaQuery<Books> query = criteriaBuilder.createQuery(Books.class);
		Root<Books> root = query.from(Books.class);

		query.where(criteriaBuilder.like(root.get("title"), title));
		book=session.createQuery(query).getSingleResult();
		}catch (Exception e){
			book=null;
		}

		return book;
	}

	@Override
	public List<Books> getBooksByAvailableStatus(boolean status) {
		List<Books> result;
		try {
			this.session.beginTransaction();
			CriteriaQuery<Books> query = criteriaBuilder.createQuery(Books.class);
			Root<Books> root = query.from(Books.class);

			query.where(criteriaBuilder.equal(root.get("isAvailable"),status));

			result = session.createQuery(query).getResultList();
		}catch (Exception e){
			result=null;
		}
		return result;
	}

	@Override
	public Books getBookById(String id) {
		Books book;
		try {
			CriteriaQuery<Books> query = criteriaBuilder.createQuery(Books.class);
			Root<Books> root = query.from(Books.class);

			query.where(criteriaBuilder.like(root.get("id"), id));
			book=session.createQuery(query).getSingleResult();
		}catch (Exception e){
			book=null;
		}

		return book;
	}

	@Override
	public List<Books> getBooksByTransaction() {
		// TODO Auto-generated method stub
		return null;
	}
}
