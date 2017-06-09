package kr.ac.hansung.whefe.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.whefe.model.Cafe_menu;

@Repository
public class Cafe_menuDao {

	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	/*
	 * public List<Cafe_menu> getCafe_menu(String category_name) { String sql =
	 * "select distinct menu_name from cafe_menu where category_name = " +
	 * "\""+category_name +"\""; return jdbcTemplateObject.query(sql, new
	 * RowMapper<Cafe_menu>() {
	 * 
	 * @Override public Cafe_menu mapRow(ResultSet rs, int rowNum) throws
	 * SQLException {
	 * 
	 * Cafe_menu cafe_menu = new Cafe_menu();
	 * cafe_menu.setCategory_name(rs.getString("category_name"));
	 * cafe_menu.setCafe_id(rs.getString("cafe_id"));
	 * cafe_menu.setHot_ice_none(rs.getString("hot_ice_none"));
	 * cafe_menu.setMenu_name(rs.getString("menu_name"));
	 * cafe_menu.setMenu_price(rs.getInt("menu_price"));
	 * cafe_menu.setMenu_size(rs.getString("menu_size")); return cafe_menu; }
	 * 
	 * }); }
	 */

	public List<Cafe_menu> getCafe_menu(String cafe_id) {
		String sql = "select distinct menu_name, cafe_id,category_name, menu_price, menu_image, hot_ice_none, menu_size from cafe_menu where cafe_id=?";
		return jdbcTemplateObject.query(sql, new Object[] { cafe_id }, new RowMapper<Cafe_menu>() {

			@Override
			public Cafe_menu mapRow(ResultSet rs, int rowNum) throws SQLException {

				Cafe_menu cafe_menu = new Cafe_menu();

				cafe_menu.setCafe_id(rs.getString("cafe_id"));
				cafe_menu.setMenu_name(rs.getString("menu_name"));
				cafe_menu.setCategory_name(rs.getString("category_name"));
				cafe_menu.setMenu_price(rs.getString("menu_price"));
				cafe_menu.setImageFilename(rs.getString("menu_image"));
				cafe_menu.setHot_ice_none(rs.getString("hot_ice_none"));
				cafe_menu.setMenu_size(rs.getString("menu_size"));

				return cafe_menu;
			}

		});
	}

	public List<Cafe_menu> getCafe_menu(String cafe_id, String category_name) {
		String sql = "select distinct menu_name from cafe_menu where category_name = ? and cafe_id=?";
		return jdbcTemplateObject.query(sql, new Object[] { category_name, cafe_id }, new RowMapper<Cafe_menu>() {

			@Override
			public Cafe_menu mapRow(ResultSet rs, int rowNum) throws SQLException {

				Cafe_menu cafe_menu = new Cafe_menu();
				/*
				 * cafe_menu.setCategory_name(rs.getString("category_name"));
				 * cafe_menu.setCafe_id(rs.getString("cafe_id"));
				 * cafe_menu.setHot_ice_none(rs.getString("hot_ice_none"));
				 */
				cafe_menu.setMenu_name(rs.getString("menu_name"));
				/*
				 * cafe_menu.setMenu_price(rs.getInt("menu_price"));
				 * cafe_menu.setMenu_size(rs.getString("menu_size"));
				 */
				return cafe_menu;
			}

		});
	}

	public List<Cafe_menu> getCafe_menu() {
		String sql = "select distinct * from cafe_menu";
		return jdbcTemplateObject.query(sql, new RowMapper<Cafe_menu>() {
			@Override
			public Cafe_menu mapRow(ResultSet rs, int rowNum) throws SQLException {

				Cafe_menu cafe_menu = new Cafe_menu();
				cafe_menu.setMenu_name(rs.getString("menu_name"));
				cafe_menu.setCategory_name(rs.getString("category_name"));
				cafe_menu.setCafe_id(rs.getString("cafe_id"));
				cafe_menu.setHot_ice_none(rs.getString("hot_ice_none"));
				cafe_menu.setMenu_price(rs.getString("menu_price"));
				// cafe_menu.setMenu_price(rs.getString("menu_price"));
				cafe_menu.setMenu_size(rs.getString("menu_size"));
				return cafe_menu;
			}

		});
	}

	public List<Cafe_menu> getMenu_price(String cafe_id, String menu_name) {
		String sql = "select * from cafe_menu where cafe_id = ? and menu_name = ?";
		return jdbcTemplateObject.query(sql, new Object[] { cafe_id, menu_name }, new RowMapper<Cafe_menu>() {
			@Override
			public Cafe_menu mapRow(ResultSet rs, int rowNum) throws SQLException {

				Cafe_menu cafe_menu = new Cafe_menu();
				cafe_menu.setMenu_name(rs.getString("menu_name"));
				cafe_menu.setCategory_name(rs.getString("category_name"));
				cafe_menu.setCafe_id(rs.getString("cafe_id"));
				cafe_menu.setHot_ice_none(rs.getString("hot_ice_none"));
				cafe_menu.setMenu_size(rs.getString("menu_size"));
				cafe_menu.setMenu_price(rs.getString("menu_price"));
				// cafe_menu.setMenu_price(rs.getString("menu_price"));
				return cafe_menu;
			}

		});
	}

	public boolean addMenu(String[] param) {
		String cafe_id = param[0];
		String category_name = param[1];
		String menu_name = param[2];
		String hot_small = param[3];
		String ice_small = param[4];
		String sql = "insert into cafe_menu(cafe_id, category_name, menu_name, hot_ice_none, menu_size, menu_price)"
				+ "values (?,?,?,?,?,?)";
		jdbcTemplateObject.update(sql, new Object[] { cafe_id, category_name, menu_name, "ice", "S", ice_small });
		jdbcTemplateObject.update(sql, new Object[] { cafe_id, category_name, menu_name, "hot", "S", hot_small });
		return true;

	}

	public boolean addMenu(ArrayList<String> pkArray, ArrayList<String> menu_nameArray,
			ArrayList<String> menu_sizeArray, ArrayList<String> hot_ice_noneArray, ArrayList<String> menu_priceArray) {
		int size = menu_nameArray.size();
		String cafe_id = pkArray.get(0);
		String category_name = pkArray.get(1);
		String menu_name = pkArray.get(2);
		String menu_image = pkArray.get(3);
		Object[] menu_nameObject = menu_nameArray.toArray();
		Object[] menu_sizeObject = menu_sizeArray.toArray();
		Object[] hot_ice_noneObject = hot_ice_noneArray.toArray();
		Object[] menu_priceObject = menu_priceArray.toArray();
		String sql = "";

		for (int i = 0; i < size; i++) {
			sql = "insert into cafe_menu(cafe_id, category_name, menu_name, menu_size, hot_ice_none, menu_price, menu_image)"
					+ "values (?,?,?,?,?,?,?)";
			jdbcTemplateObject.update(sql, new Object[] { cafe_id, category_name, menu_name, menu_sizeObject[i],
					hot_ice_noneObject[i], menu_priceObject[i], menu_image });
		}
		return true;
	}

	public boolean editMenu(ArrayList<String> pkArray, ArrayList<String> menu_nameArray,
			ArrayList<String> menu_sizeArray, ArrayList<String> hot_ice_noneArray, ArrayList<String> menu_priceArray) {
		System.out.println("editMenuDao!!!!!!!!!!!!!!!");
		int size = menu_nameArray.size();
		String cafe_id = pkArray.get(0);
		String category_name = pkArray.get(1);
		String menu_name = pkArray.get(2);
		Object[] menu_nameObject = menu_nameArray.toArray();
		Object[] menu_sizeObject = menu_sizeArray.toArray();
		Object[] hot_ice_noneObject = hot_ice_noneArray.toArray();
		Object[] menu_priceObject = menu_priceArray.toArray();
		String sql = "";
		System.out.println("editMenuDao!!!!!!!!!!!!!!!");
		for (int i = 0; i < size; i++) {
			sql = "insert into cafe_menu(cafe_id, category_name, menu_name, menu_size, hot_ice_none, menu_price,menu_image)"
					+ "values (?,?,?,?,?,?,1) on duplicate key update menu_name=?,menu_size=?, hot_ice_none=?,menu_price=?";
			jdbcTemplateObject.update(sql, new Object[] { cafe_id, category_name, menu_name, menu_sizeObject[i],
					hot_ice_noneObject[i], menu_priceObject[i], menu_name, menu_sizeObject[i], hot_ice_noneObject[i], menu_priceObject[i]});
		}
		return true;
	}

	public boolean editMenu_name(String cafe_id, String menu_name, String new_name) {
		String sql = "update cafe_menu set menu_name=? where cafe_id = ? and menu_name = ?";
		return jdbcTemplateObject.update(sql, new Object[] { new_name, cafe_id, menu_name }) == 1;
	}

	public boolean deleteMenu(String cafe_id, String menu_name) {
		String sql = "delete from cafe_menu where cafe_id = ? and menu_name = ?";
		return jdbcTemplateObject.update(sql, new Object[] { cafe_id, menu_name }) == 1;
	}
}
