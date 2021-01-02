package com.enigma.isma.dao;

import java.util.List;

import com.enigma.isma.entity.Shelf;

public interface ShelfDao {

	public String saveShelf(Shelf shelf);

	public List<Shelf> getAllShelf();

	public Shelf getShelfById (Integer id);
}
