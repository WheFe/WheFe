package kr.ac.hansung.whefe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.whefe.dao.CategoryDao;
import kr.ac.hansung.whefe.model.Category;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryDao categoryDao;
	
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	public List<Category> getCategories() {
		return categoryDao.getCategories();
	}
	
	public List<Category> getCategories(String cafe_id) {
		return categoryDao.getCategories(cafe_id);
	}
	
	public boolean addCategory(String cafe_id, Category category) {
		return categoryDao.addCategory(cafe_id, category);
	}
	
	public boolean deleteCategory(String cafe_id, String category_name) {
		return categoryDao.deleteCategory(cafe_id, category_name);
	}
	
	public boolean editCategory(String cafe_id, String category_name, String newName) {
		return categoryDao.editCategory(cafe_id, category_name, newName);
	}
}
