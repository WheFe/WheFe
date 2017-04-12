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
				opt.setOption_price(rs.getInt("option_price"));
				
				return opt;
			}

		});
	}
	
	public boolean addOption(Opt opt) {
		String sql = "insert into opt(option_name, option_price, category_name) values (?,?,?)";
		
		String option_name = opt.getOption_name();
		int option_price = opt.getOption_price();
		String category_name = opt.getCategory_name();
		return jdbcTemplateObject.update(sql, new Object[] {option_name, option_price, category_name})==1;
	}
}
