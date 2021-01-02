package com.enigma.isma.dao;

import java.util.List;

import com.enigma.isma.entity.Books;


public interface BooksDao {

	public String saveBook (Books book);

	public List<Books> getAllBooks ();

	public String updateBook (Books book);

	public List<Books> getBooksByTitle(String title);

	public Books getBookSingleTitle(String title);

	public List<Books> getBooksByAvailableStatus(boolean status);

	public Books getBookById (String id);

	public List<Books> getBooksByTransaction();

}
