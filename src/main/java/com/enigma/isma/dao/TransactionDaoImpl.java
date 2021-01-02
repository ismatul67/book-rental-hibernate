package com.enigma.isma.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.enigma.isma.entity.User;
import org.hibernate.Session;

import com.enigma.isma.config.HibernateConfiguration;
import com.enigma.isma.entity.Transaction;

public class TransactionDaoImpl implements TransactionDao {
	Session session;
	CriteriaBuilder criteriaBuilder;

	public TransactionDaoImpl() {
		this.session = HibernateConfiguration.getSessionFactory().openSession();
		criteriaBuilder = session.getCriteriaBuilder();

	}

	@Override
	public String saveTransaction(Transaction transaction) {
		String status;
		try {
			this.session.beginTransaction();
			this.session.save(transaction);
			this.session.getTransaction().commit();
			status="Success";
		}catch (Exception e){
			status="Failed";
		}
		return status;
	}

	@Override
	public List<Transaction> getAllTransaction() {
		List<Transaction> transactions;
		try {
			session.beginTransaction();
			transactions = session.createQuery("from Transaction").getResultList();
			session.getTransaction().commit();
		}catch (Exception e){
			transactions=null;
		}

		return transactions;
	}

	@Override
	public String updateTransaction(Transaction transaction) {
		String status;
		try {
			this.session.beginTransaction();
			this.session.update(transaction);
			this.session.getTransaction().commit();
			status="Success";
		}catch (Exception e){
			status="Failed";
		}
		return status;
	}

	@Override
	public Transaction getTransactionById(String id) {
		Transaction transaction;
		try {
			CriteriaQuery<Transaction> query = criteriaBuilder.createQuery(Transaction.class);
			Root<Transaction> root = query.from(Transaction.class);

			query.where(criteriaBuilder.like(root.get("id"), id));
			transaction=session.createQuery(query).getSingleResult();
		}catch (Exception e){
			transaction=null;
		}

		return transaction;
	}

	@Override
	public  List<Transaction> getTransactionsByUserId(User user) {
		List<Transaction> transactions = null;
		try {
			this.session.beginTransaction();
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Transaction> query = criteriaBuilder.createQuery(Transaction.class);
			Root<Transaction> root = query.from(Transaction.class);

			query.select(root).where(criteriaBuilder.equal(root.get("userId"), user.getId()));

			transactions = session.createQuery(query).getResultList();


		} catch (Exception e) {
			transactions=null;
		}

		return transactions;
	}

	@Override
	public List<Transaction> getAllTransactionByDate(String date) {
		// TODO Auto-generated method stub
	        List<Transaction> transactions = null;
	        try {
				this.session.beginTransaction();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            Date date1 = simpleDateFormat.parse(date);
	            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
	            CriteriaQuery<Transaction> query = criteriaBuilder.createQuery(Transaction.class);

	            Root<Transaction> root = query.from(Transaction.class);

	            Predicate predicate1 = criteriaBuilder.equal(root.get("rentDate"), date1);
	            Predicate predicate2 = criteriaBuilder.equal(root.get("returnDate"), date1);

	            Predicate predicate = criteriaBuilder.or(predicate1, predicate2);

	            query.select(root).where(predicate);

	             transactions = session.createQuery(query).getResultList();


	        } catch (Exception e) {
	        	transactions=null;
	        }

	        return transactions;
	}

	@Override
	public List<Transaction> getAllTransactionByMonth(Integer month, Integer year) {
		 List<Transaction> result = new ArrayList<>();
		try {
			Calendar rentDate = new GregorianCalendar();
			List<Transaction> transactions = session.createQuery("from Transaction").list();
			for (Transaction transaction: transactions) {
				rentDate.setTime(transaction.getRentDate());

				if( rentDate.get(Calendar.MONTH)+1==month && rentDate.get(Calendar.YEAR)==year ) {
					result.add(transaction);
				}else{
					result=null;
				}
			}
		}catch (Exception e){
			result=null;
		}
        return result;
}

}
