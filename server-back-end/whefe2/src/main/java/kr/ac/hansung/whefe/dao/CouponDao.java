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

				Coupon coupon = new Coupon();
				coupon.setCoupon_name(rs.getString("coupon_name"));
				coupon.setCafe_id(rs.getString("cafe_id"));
				// coupon.setCoupon_num(rs.getInt("coupon_num"));				//추가
				//coupon.setCoupon_price(rs.getInt("coupon_price"));
				coupon.setCoupon_price(rs.getString("coupon_price"));		//추가
				coupon.setCoupon_start(rs.getString("coupon_start"));
				coupon.setCoupon_end(rs.getString("coupon_end"));
				coupon.setUse_ox(rs.getBoolean("use_ox"));
				
				return coupon;
			}

		});
	}
	
	public List<Coupon> getCoupons(String cafe_id) {
		String sqlStatement = "select * from cafe_coupon whefe cafe_id=?";
		
		return jdbcTemplateObject.query(sqlStatement, new Object[] {cafe_id}, new RowMapper<Coupon>() {

			@Override
			public Coupon mapRow(ResultSet rs, int rowNum) throws SQLException {

				Coupon coupon = new Coupon();
				coupon.setCoupon_name(rs.getString("coupon_name"));
				coupon.setCafe_id(rs.getString("cafe_id"));
				// coupon.setCoupon_num(rs.getInt("coupon_num"));				//추가
				//coupon.setCoupon_price(rs.getInt("coupon_price"));
				coupon.setCoupon_price(rs.getString("coupon_price"));		//추가
				coupon.setCoupon_start(rs.getString("coupon_start"));
				coupon.setCoupon_end(rs.getString("coupon_end"));
				coupon.setUse_ox(rs.getBoolean("use_ox"));
				
				return coupon;
			}

		});
	}
	
	public boolean addCoupon(Coupon coupon) {
		String sql = "insert into cafe_coupon(coupon_name,cafe_id, coupon_num, coupon_price,coupon_start,coupon_end,use_ox) values (?,?,3,?,?,?,?)";
		Object[] object = {coupon.getCoupon_name(), coupon.getCafe_id(), coupon.getCoupon_price(), coupon.getCoupon_start(), coupon.getCoupon_end(),coupon.isUse_ox()};
		return (jdbcTemplateObject.update(sql, object))==1;
	}
	
	public boolean deleteCoupon(String coupon_name, String cafe_id) {
		String sql = "delete from cafe_coupon where coupon_name=? and cafe_id=?";
		Object[] object = {coupon_name, cafe_id};
		return (jdbcTemplateObject.update(sql, object))==1;
	}
}