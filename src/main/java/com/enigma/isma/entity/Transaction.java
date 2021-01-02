package com.enigma.isma.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="transaction")
public class Transaction {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "uuid")
	private String id;

	@Column(name="rent_date")
	private Date rentDate;

	@Column(name="payment")
	private Double payment;

	@Column(name="return_date", nullable = true)
	private Date returnDate;

	@Column(name="penalty_fee", nullable = true)
	private Double penaltyfee;

	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name="book_id")
	private Books bookTransaction;

	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;

	@Column(name="total_payment", nullable = true)
	private Double totalPayment;

	public Transaction() {
		super();
	}

	public Transaction(Date rentDate, Double payment, Books bookTransaction, User user) {
		super();
		this.rentDate = rentDate;
		this.payment = payment;
		this.bookTransaction = bookTransaction;
		this.user = user;
	}



	public Transaction(Date returnDate, Double penaltyfee, Double totalPayment) {
		super();
		this.returnDate = returnDate;
		this.penaltyfee = penaltyfee;
		this.totalPayment = totalPayment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getRentDate() {
		return rentDate;
	}

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Double getPenaltyfee() {
		return penaltyfee;
	}

	public void setPenaltyfee(Double penaltyfee) {
		this.penaltyfee = penaltyfee;
	}

	public Books getBookTransaction() {
		return bookTransaction;
	}

	public void setBookTransaction(Books book) {
		this.bookTransaction = bookTransaction;
	}

	public User getMember() {
		return user;
	}

	public void setMember(User user) {
		this.user = user;
	}

	public Double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(Double totalBayar) {
		this.totalPayment = totalBayar;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", rentDate=" + rentDate + ", payment=" + payment + ", returnDate="
				+ returnDate + ", penaltyfee=" + penaltyfee + ", bookTransaction=" + bookTransaction.getTitle() + ", user="
				+ user.getName() + ", totalBayar=" + totalPayment + "]";
	}



}
