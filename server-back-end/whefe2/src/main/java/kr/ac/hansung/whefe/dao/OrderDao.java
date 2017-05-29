package kr.ac.hansung.whefe.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import kr.ac.hansung.whefe.model.Order;
@Repository
public class OrderDao {
    private JdbcTemplate jdbcTemplateObject;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }
    public List<Order> getOrders() {
        String sqlStatement = "select * from orderList where menu_completed = 0";
        return jdbcTemplateObject.query(sqlStatement, new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet rs, int rouNum) throws SQLException {
                Order order = new Order();
                order.setMenu_name(rs.getString("menu_name"));
                order.setCustomer_name(rs.getString("customer_id"));
                order.setHotIceNone(rs.getString("hot_ice_none"));
                order.setMenu_size(rs.getString("menu_size"));
                order.setOption_info(rs.getString("option_name"));
                order.setMenu_quantity(rs.getInt("menu_quantity"));
                order.setMenu_completed(rs.getBoolean("menu_completed"));
                return order;
            }
        });
    }
    
    public Order getOrderForEdit(String menu_name) {
        String sqlStatement = "select * from orderList where menu_name = ?";
        return jdbcTemplateObject.queryForObject(sqlStatement, new Object[]{menu_name} ,new RowMapper<Order>(){
            @Override
            public Order mapRow(ResultSet rs, int rouNum) throws SQLException {
                Order completeOrder = new Order();
                
                completeOrder.setMenu_name(rs.getString("menu_name"));
                completeOrder.setCustomer_name(rs.getString("customer_id"));
                completeOrder.setHotIceNone(rs.getString("hot_ice_none"));
                completeOrder.setMenu_size(rs.getString("menu_size"));
                completeOrder.setOption_info(rs.getString("option_name"));
                completeOrder.setMenu_quantity(rs.getInt("menu_quantity"));
                completeOrder.setMenu_completed(rs.getBoolean("menu_completed"));
                return completeOrder;
            }
        });
    }
    
    public boolean editMenuCompleted(String menu_name){
        String sqlStatement = "update orderlist set menu_completed = '1' where menu_name = ?";
        return ((jdbcTemplateObject.update(sqlStatement, menu_name))==1);
        
    }
    public List<Order> getCompleteOrders() {
        String sqlStatement = "select * from orderlist where menu_completed = 1";
        return jdbcTemplateObject.query(sqlStatement, new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet rs, int rouNum) throws SQLException {
                Order completeOrders = new Order();
                completeOrders.setMenu_name(rs.getString("menu_name"));
                completeOrders.setCustomer_name(rs.getString("customer_id"));
                completeOrders.setHotIceNone(rs.getString("hot_ice_none"));
                completeOrders.setMenu_size(rs.getString("menu_size"));
                completeOrders.setOption_info(rs.getString("option_name"));
                completeOrders.setMenu_quantity(rs.getInt("menu_quantity"));
                return completeOrders;
            }
        });
    }
    
}