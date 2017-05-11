package kr.ac.hansung.whefe.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.whefe.model.Customer_info;
import kr.ac.hansung.whefe.model.Opt;

@Repository
public class Customer_infoDao {

	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public List<Customer_info> getCustomer_infoById(String id) {
		String sql = "select * from customer_info where customer_id=" + "\"" + id + "\"";
		return jdbcTemplateObject.query(sql, new RowMapper<Customer_info>() {

			@Override
			public Customer_info mapRow(ResultSet rs, int rowNum) throws SQLException {

				Customer_info customer_info = new Customer_info();
				customer_info.setCustomer_id(rs.getString("customer_id"));
				customer_info.setCustomer_pw(rs.getString("customer_pw"));
				customer_info.setCustomer_phone(rs.getString("customer_phone"));
				customer_info.setCustomer_name(rs.getString("customer_name"));
				
				return customer_info;
			}

		});

	}
}
