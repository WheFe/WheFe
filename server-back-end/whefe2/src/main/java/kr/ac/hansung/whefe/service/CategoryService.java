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
	
	public boolean addCategory(Category category) {
		return categoryDao.addCategory(category);
	}
	
	public boolean deleteCategory(String category_name) {
		return categoryDao.deleteCategory(category_name);
	}
	
	public boolean editCategory(String category_name, String newName) {
		return categoryDao.editCategory(category_name, newName);
	}
}
