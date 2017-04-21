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
		
		public List<Cafe_menu> getCafe_menu(String category_name) {
			String sql = "select distinct menu_name from cafe_menu where category_name = " + "\""+category_name +"\"";
			return jdbcTemplateObject.query(sql, new RowMapper<Cafe_menu>() {

				@Override
				public Cafe_menu mapRow(ResultSet rs, int rowNum) throws SQLException {

					Cafe_menu cafe_menu = new Cafe_menu();
					/*cafe_menu.setCategory_name(rs.getString("category_name"));
					cafe_menu.setCafe_id(rs.getString("cafe_id"));
					cafe_menu.setHot_ice_none(rs.getString("hot_ice_none"));*/
					cafe_menu.setMenu_name(rs.getString("menu_name"));
					/*cafe_menu.setMenu_price(rs.getInt("menu_price"));
					cafe_menu.setMenu_size(rs.getString("menu_size"));*/
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
			jdbcTemplateObject.update(sql, new Object[] {cafe_id, category_name, menu_name, "ice", "S", ice_small});
			jdbcTemplateObject.update(sql, new Object[] {cafe_id, category_name, menu_name, "hot", "S", hot_small});
			return true;
			
		}
		
		public boolean addMenu(ArrayList<String> pkArray, ArrayList<String> menu_nameArray, ArrayList<String> menu_sizeArray, ArrayList<String> hot_ice_noneArray, ArrayList<String> menu_priceArray) {
			int size = menu_nameArray.size();
			String cafe_id = pkArray.get(0);
			String category_name = pkArray.get(1);
			String menu_name = pkArray.get(2);
			Object[] menu_nameObject = menu_nameArray.toArray();
			Object[] menu_sizeObject = menu_sizeArray.toArray();
			Object[] hot_ice_noneObject = hot_ice_noneArray.toArray();
			Object[] menu_priceObject = menu_priceArray.toArray();
			String sql="";
			
			for(int i=0;i<size;i++) {
			sql = "insert into cafe_menu(cafe_id, category_name, menu_name, hot_ice_none, menu_size, menu_price)"
					+ "values (?,?,?,?,?,?)";
			jdbcTemplateObject.update(sql, new Object[] {cafe_id, category_name, menu_name, menu_sizeObject[i], hot_ice_noneObject[i], menu_priceObject[i]});
			}
			return false;
		}
}
