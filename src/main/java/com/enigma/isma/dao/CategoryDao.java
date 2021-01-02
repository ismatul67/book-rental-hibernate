package com.enigma.isma.dao;

import java.util.List;

import com.enigma.isma.entity.Category;


public interface CategoryDao {

	public String saveCategory(Category category);

	public String updateCategory(Category category);

	public Category getCategoryById(Integer id);

	public List<Category> getAllCategories();

	public Category getCategoryByName(String category);

}
