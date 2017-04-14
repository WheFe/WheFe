package kr.ac.hansung.whefe.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.whefe.model.Coupon;

@Repository
public class CouponDao {
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public List<Coupon> getCoupons() {
		String sqlStatement = "select * from cafe_coupon";

		return jdbcTemplateObject.query(sqlStatement, new RowMapper<Coupon>() {

			@Override
			public Coupon mapRow(ResultSet rs, int rowNum) throws SQLException {

				System.out.println("윤원짱짱");
				Coupon coupon = new Coupon();
				coupon.setCoupon_name(rs.getString("coupon_name"));
				coupon.setCafe_id(rs.getString("cafe_id"));
				coupon.setCoupon_num(rs.getInt("coupon_num"));
				coupon.setCoupon_price(rs.getInt("coupon_price"));
				coupon.setCoupon_start(rs.getDate("coupon_start"));
				coupon.setCoupon_end(rs.getDate("coupon_end"));
				coupon.setUse_ox(rs.getBoolean("use_ox"));

				return coupon;
			}

		});
	}
}