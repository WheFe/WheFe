package kr.ac.hansung.whefe.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.whefe.model.Category;


@Repository
public class CategoryDao {
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public List<Category> getCategories() {
		String sqlStatement = "select * from category";
		return jdbcTemplateObject.query(sqlStatement, new RowMapper<Category>() {

			@Override
			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {

				Category category = new Category();
				category.setCategory_name(rs.getString("category_name"));
				
				return category;
			}

		});
	}
	
	public List<Category> getCategories(String cafe_id) {
		String sqlStatement = "select * from category where cafe_id=?";
		return jdbcTemplateObject.query(sqlStatement, new Object[] {cafe_id},new RowMapper<Category>() {

			@Override
			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {

				Category category = new Category();
				category.setCafe_id(rs.getString("cafe_id"));
				category.setCategory_name(rs.getString("category_name"));
				return category;
			}

		});
	}
	
	public boolean addCategory(String cafe_id, Category category) {
		String category_name = category.getCategory_name();
		String sql = "insert into category (cafe_id, category_name) values (?,?)";
		return ((jdbcTemplateObject.update(sql, new Object[] {cafe_id, category_name}))==1);
	}
	
	public boolean deleteCategory(String cafe_id, String category_name) {
		System.out.println("DAO!!!!!!!!!!" + category_name);
		String sql = "delete from category where cafe_id = ? and category_name = ?";
		return (jdbcTemplateObject.update(sql, new Object[] {cafe_id, category_name})==1);
	}
	
	public boolean editCategory(String cafe_id, String category_name, String newName) {
		String sql = "update category set category_name = ? where cafe_id = ? and category_name = ?";
		return ((jdbcTemplateObject.update(sql, new Object[] {newName, cafe_id, category_name}))==1);
	}
}
