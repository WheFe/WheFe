package kr.ac.hansung.whefe.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.whefe.model.Cafe_info;
import kr.ac.hansung.whefe.model.Cafe_menu;

@Repository
public class Cafe_infoDao {

	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public boolean addProduct(Cafe_info cafe_info) {
		String cafe_id = cafe_info.getCafe_id();
		String cafe_pw = cafe_info.getCafe_pw();
		String cafe_name = cafe_info.getCafe_name();
		String cafe_tel = cafe_info.getCafe_tel();
		String cafe_address = cafe_info.getCafe_address();
		String cafe_max = cafe_info.getCafe_max();
		String cafe_open = cafe_info.getCafe_open();
		String cafe_end = cafe_info.getCafe_end();
		String cafe_intro = cafe_info.getCafe_intro();
		String cafe_image1 = cafe_info.getImageFilename1();
		String cafe_image2 = cafe_info.getImageFilename2();
		String cafe_image3 = cafe_info.getImageFilename3();
		System.out.println(cafe_info.toString());
		String sqlStatement = "insert into cafe_info (cafe_id,cafe_pw,cafe_name,cafe_tel,cafe_address,cafe_max,cafe_open,cafe_end,cafe_intro,cafe_image1,cafe_image2,cafe_image3,cafe_curr,enabled) values (?,?,?,?,?,?,?,?,?,?,?,?,0,1)";
		String sql2 = "insert into cafe_authorities (cafe_id, authority) values (?, 'ROLE_ADMIN')";
		jdbcTemplateObject.update(sqlStatement, new Object[] { cafe_id, cafe_pw, cafe_name, cafe_tel, cafe_address,
				cafe_max, cafe_open, cafe_end, cafe_intro, cafe_image1, cafe_image2, cafe_image3 });
		jdbcTemplateObject.update(sql2, new Object[] { cafe_id });

		return true;
	}

	public boolean editCafe_info(Cafe_info cafe_info) {
		String cafe_id = cafe_info.getCafe_id();
		String cafe_pw = cafe_info.getCafe_pw();
		String cafe_name = cafe_info.getCafe_name();
		String cafe_tel = cafe_info.getCafe_tel();
		String cafe_address = cafe_info.getCafe_address();
		String cafe_max = cafe_info.getCafe_max();
		String cafe_open = cafe_info.getCafe_open();
		String cafe_end = cafe_info.getCafe_end();
		String cafe_intro = cafe_info.getCafe_intro();
		//String cafe_image1 = cafe_info.getImageFilename1();
		//String cafe_image2 = cafe_info.getImageFilename2();
		//String cafe_image3 = cafe_info.getImageFilename3();
		String sql = "update cafe_info set cafe_pw=?,cafe_name=?,cafe_tel=?,"
				+ "cafe_address=?,cafe_max=?,cafe_open=?,cafe_end=?,"
				+ "cafe_intro=? where cafe_id=?";
		jdbcTemplateObject.update(sql, new Object[] { cafe_pw, cafe_name, cafe_tel, cafe_address, cafe_max, cafe_open,
				cafe_end, cafe_intro, cafe_id });
		/*jdbcTemplateObject.update(sql, new Object[] { cafe_pw, cafe_name, cafe_tel, cafe_address, cafe_max, cafe_open,
				cafe_end, cafe_intro, cafe_image1, cafe_image2, cafe_image3, cafe_id });*/
		return true;
	}

	public List<Cafe_info> getCafe_info() {
		String sql = "select * from cafe_info";
		return jdbcTemplateObject.query(sql, new RowMapper<Cafe_info>() {
			@Override
			public Cafe_info mapRow(ResultSet rs, int rowNum) throws SQLException {

				Cafe_info cafe_info = new Cafe_info();
				cafe_info.setCafe_id(rs.getString("cafe_id"));
				cafe_info.setCafe_name(rs.getString("cafe_name"));
				cafe_info.setCafe_address(rs.getString("cafe_address"));
				cafe_info.setCafe_tel(rs.getString("cafe_tel"));
				cafe_info.setCafe_max(rs.getString("cafe_max"));
				cafe_info.setCafe_curr(rs.getString("cafe_curr"));
				cafe_info.setCafe_open(rs.getString("cafe_open"));
				cafe_info.setCafe_end(rs.getString("cafe_end"));
				cafe_info.setCafe_intro(rs.getString("cafe_intro"));
				cafe_info.setImageFilename1(rs.getString("cafe_image1"));
				cafe_info.setImageFilename2(rs.getString("cafe_image2"));
				cafe_info.setImageFilename3(rs.getString("cafe_image3"));
				return cafe_info;
			}
		});
	}
	
	public Cafe_info getCafe_info(String cafe_id) {
		String sql = "select * from cafe_info where cafe_id=?";
		return jdbcTemplateObject.queryForObject(sql, new Object[] {cafe_id}, new RowMapper<Cafe_info>() {
			@Override
			public Cafe_info mapRow(ResultSet rs, int rowNum) throws SQLException {
				Cafe_info cafe_info = new Cafe_info();
				cafe_info.setCafe_id(rs.getString("cafe_id"));
				cafe_info.setCafe_name(rs.getString("cafe_name"));
				cafe_info.setCafe_address(rs.getString("cafe_address"));
				cafe_info.setCafe_tel(rs.getString("cafe_tel"));
				cafe_info.setCafe_open(rs.getString("cafe_open"));
				cafe_info.setCafe_end(rs.getString("cafe_end"));
				cafe_info.setCafe_intro(rs.getString("cafe_intro"));
				cafe_info.setCafe_max(rs.getString("cafe_max"));
				
				return cafe_info;
			}
		});
	}

	public Cafe_info getCurrent(String cafe_id) {
		String sql = "select * from cafe_info where cafe_id = ?";

		return jdbcTemplateObject.queryForObject(sql, new Object[] { cafe_id }, new RowMapper<Cafe_info>() {

			@Override
			public Cafe_info mapRow(ResultSet rs, int rouNum) throws SQLException {

				Cafe_info cafe_info = new Cafe_info();

				cafe_info.setCafe_curr(rs.getString("cafe_curr"));
				cafe_info.setCafe_max(rs.getString("cafe_max"));

				return cafe_info;
			}
		});
	}
}
