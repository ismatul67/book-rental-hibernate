package com.enigma.isma.dao;

import java.util.List;

import com.enigma.isma.entity.Transaction;
import com.enigma.isma.entity.User;

public interface TransactionDao {

	public String saveTransaction (Transaction transaction);

	public List<Transaction> getAllTransaction ();

	public String updateTransaction (Transaction transaction);

	public Transaction getTransactionById(String id);

	public  List<Transaction> getTransactionsByUserId(User user);

	public List<Transaction> getAllTransactionByDate (String date);

	public List<Transaction> getAllTransactionByMonth(Integer month, Integer year);

	public List<Transaction> getAllTransactionByDateSingleUser (String date, User user);

	public List<Transaction> getAllTransactionByMonthSingleUser(Integer month, Integer year, User user);



}
