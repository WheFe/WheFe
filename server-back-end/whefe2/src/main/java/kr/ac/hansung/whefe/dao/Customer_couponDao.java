package kr.ac.hansung.whefe.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.whefe.model.Customer_coupon;
import kr.ac.hansung.whefe.model.Customer_info;

@Repository
public class Customer_couponDao {
	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	public List<Customer_coupon> getCustomer_coupon(String customer_id) {
		String sql = "select * from customer_coupon where customer_id=?";
		return jdbcTemplateObject.query(sql, new Object[] {customer_id},new RowMapper<Customer_coupon>() {

			@Override
			public Customer_coupon mapRow(ResultSet rs, int rowNum) throws SQLException {

				Customer_coupon customer_coupon = new Customer_coupon();
				customer_coupon.setCafe_id(rs.getString("cafe_id"));
				customer_coupon.setCustomer_id(rs.getString("customer_id"));
				customer_coupon.setCoupon_name(rs.getString("coupon_name"));
				customer_coupon.setCoupon_num(rs.getString("coupon_num"));
				
				return customer_coupon;
			}

		});

	}
	
	public List<Customer_coupon> getCustomer_coupon(String cafe_id, String customer_id) {
		String sql = "select * from customer_coupon where cafe_id=? and customer_id=?";
		return jdbcTemplateObject.query(sql, new Object[] {cafe_id,customer_id},new RowMapper<Customer_coupon>() {

			@Override
			public Customer_coupon mapRow(ResultSet rs, int rowNum) throws SQLException {

				Customer_coupon customer_coupon = new Customer_coupon();
				customer_coupon.setCafe_id(rs.getString("cafe_id"));
				customer_coupon.setCustomer_id(rs.getString("customer_id"));
				customer_coupon.setCoupon_name(rs.getString("coupon_name"));
				customer_coupon.setCoupon_num(rs.getString("coupon_num"));
				
				return customer_coupon;
			}

		});

	}
}
