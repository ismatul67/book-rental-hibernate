package com.enigma.isma.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="shelf")
public class Shelf {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="shelf_code", unique = true)
	private String shelfCode;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "shelf")
	@Cascade(CascadeType.SAVE_UPDATE)
	private List<Books> book;

	public Shelf() {

	}

	public Shelf(String shelfCode) {
		this.shelfCode = shelfCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShelfCode() {
		return shelfCode;
	}

	public void setShelfCode(String shelfCode) {
		this.shelfCode = shelfCode;
	}


	public List<Books> getBook() {
		return book;
	}

	public void setBook(List<Books> book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "Shelf [id=" + id + ", shelfCode=" + shelfCode + ", book=" + book + "]";
	}






}
