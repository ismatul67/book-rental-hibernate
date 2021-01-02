package com.enigma.isma.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="books")
public class Books {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "uuid")
	private String id;

	@Column(name="title")
	private String title;

	@Column(name="author")
	private String author;

	@Column(name="status")
	private boolean isAvailable;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="shelf_id")
	private Shelf shelf;


	@OneToMany(fetch = FetchType.EAGER, mappedBy = "bookTransaction")
	private List<Transaction> transaction;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="category_id")
	private Category category;

	public Books() {
	}

	public Books(String title, String author, boolean isAvailable, Shelf shelf, Category category) {
		this.title = title;
		this.author = author;
		this.isAvailable = isAvailable;
		this.shelf = shelf;
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean available) {
		isAvailable = available;
	}

	public Shelf getShelf() {
		return shelf;
	}

	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}

	public List<Transaction> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


}
