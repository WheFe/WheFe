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
import kr.ac.hansung.whefe.model.Opt;

@Repository
public class OptDao {
	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	public List<Opt> getOpt(String category_name) {
		String sql = "select * from opt where category_name=" + "\"" + category_name + "\"";
		return jdbcTemplateObject.query(sql, new RowMapper<Opt>() {

			@Override
			public Opt mapRow(ResultSet rs, int rowNum) throws SQLException {

				Opt opt = new Opt();
				opt.setOption_name(rs.getString("option_name"));
				opt.setOption_price(rs.getString("option_price"));
				
				return opt;
			}

		});
	}
	
	public List<Opt> getOpt(String cafe_id, String category_name) {
		String sql = "select * from opt where cafe_id = ? and category_name = ?";
		return jdbcTemplateObject.query(sql, new Object[] {cafe_id,category_name}, new RowMapper<Opt>() {
			@Override
			public Opt mapRow(ResultSet rs, int rowNum) throws SQLException {
				Opt opt = new Opt();
				opt.setCafe_id(rs.getString("cafe_id"));
				opt.setCategory_name(rs.getString("category_name"));
				opt.setOption_name(rs.getString("option_name"));
				opt.setOption_price(rs.getString("option_price"));
				
				return opt;
			}

		});
	}
	
	public boolean addOption(Opt opt) {
		String sql = "insert into opt(cafe_id, option_name, option_price, category_name) values (?,?,?,?)";
		String cafe_id = opt.getCafe_id();
		String option_name = opt.getOption_name();
		String option_price = opt.getOption_price();
		String category_name = opt.getCategory_name();
		return jdbcTemplateObject.update(sql, new Object[] {cafe_id, option_name, option_price, category_name})==1;
	}
	
	public boolean editOption(String cafe_id, String original, String option_name, String option_price, String category_name) {
		//String sql = "update opt set option_name=?, option_price=? where option_name=?";
		//String sql = "update opt set option_name='ddddddddddddd' where option_price='500'";
		String sql2 = "update opt set option_name=?, option_price=? where cafe_id=? and option_name=?";
		
		String sql = "update opt "+ "set option_name = " + "'" + option_name+ "', " +" option_price = " + "'"+ option_price + "' where option_name=" + "'"+ original +"'"  ;
		//return jdbcTemplateObject.update(sql, new Object[] {option_name, option_price, original})==1;
		//return jdbcTemplateObject.update(sql)==1;
		return jdbcTemplateObject.update(sql2, new Object[] {option_name, option_price, cafe_id, original })==1;
	}
	
	public boolean deleteOption(String cafe_id, String original) {
		String sql = "delete from opt where cafe_id = ? and option_name = ?";
		return (jdbcTemplateObject.update(sql, new Object[] {cafe_id, original})==1);
	}
	
	
}
