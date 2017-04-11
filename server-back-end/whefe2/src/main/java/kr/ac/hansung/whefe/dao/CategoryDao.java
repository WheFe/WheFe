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
	
	public boolean addCategory(Category category) {
		String category_name = category.getCategory_name();
		String sql = "insert into category (category_name) values (?)";
		return ((jdbcTemplateObject.update(sql, category_name))==1);
	}
	
	public boolean deleteCategory(String category_name) {
		System.out.println("DAO!!!!!!!!!!" + category_name);
		String sql = "delete from category where category_name = ?";
		return (jdbcTemplateObject.update(sql, (String)category_name)==1);
	}
	
	public boolean editCategory(String category_name, String newName) {
		String sql = "update category set category_name = ? where category_name = ?";
		return ((jdbcTemplateObject.update(sql, newName, category_name))==1);
	}
}
