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

   public List<Order> getOrders(String cafe_id) {
      String sqlStatement = "select * from orderList where menu_completed = 0 and cafe_id = ? ";

      return jdbcTemplateObject.query(sqlStatement, new Object[]{cafe_id}, new RowMapper<Order>() {

         @Override
         public Order mapRow(ResultSet rs, int rouNum) throws SQLException {

            Order order = new Order();
            order.setOrderlist_id(rs.getInt("orderlist_id"));
            order.setMenu_name(rs.getString("menu_name"));
            order.setCustomer_id(rs.getString("customer_id"));
            order.setHotIceNone(rs.getString("hot_ice_none"));
            order.setMenu_size(rs.getString("menu_size"));
            order.setOption_info(rs.getString("option_name"));
            order.setCategory_name(rs.getString("category_name"));
            order.setCafe_id(rs.getString("cafe_id"));

            return order;
         }
      });
   }
   
   public Order getOrderForEdit(String orderlist_id, String cafe_id) {
      String sqlStatement = "select * from orderList where orderlist_id = ? and cafe_id = ?";

      return jdbcTemplateObject.queryForObject(sqlStatement, new Object[]{orderlist_id, cafe_id} ,new RowMapper<Order>(){

         @Override
         public Order mapRow(ResultSet rs, int rouNum) throws SQLException {

            Order completeOrder = new Order();
            completeOrder.setOrderlist_id(rs.getInt("orderlist_id"));
            completeOrder.setMenu_name(rs.getString("menu_name"));
            completeOrder.setCustomer_id(rs.getString("customer_id"));
            completeOrder.setHotIceNone(rs.getString("hot_ice_none"));
            completeOrder.setMenu_size(rs.getString("menu_size"));
            completeOrder.setOption_info(rs.getString("option_name"));
            completeOrder.setCategory_name(rs.getString("category_name"));
            completeOrder.setCafe_id(rs.getString("cafe_id"));

            return completeOrder;
         }
      });
   }
   
   public boolean editMenuCompleted(String orderlist_id, String cafe_id){
      String sqlStatement = "update orderlist set menu_completed = '1' where orderlist_id = ? and cafe_id = ?";
      return ((jdbcTemplateObject.update(sqlStatement, orderlist_id, cafe_id))==1);
      
   }

   public List<Order> getCompleteOrders(String cafe_id) {
      String sqlStatement = "select * from orderlist where menu_completed = 1 and cafe_id = ?";

      return jdbcTemplateObject.query(sqlStatement, new Object[]{cafe_id}, new RowMapper<Order>() {

         @Override
         public Order mapRow(ResultSet rs, int rouNum) throws SQLException {

            Order completeOrders = new Order();
            completeOrders.setOrderlist_id(rs.getInt("orderlist_id"));
            completeOrders.setMenu_name(rs.getString("menu_name"));
            completeOrders.setCustomer_id(rs.getString("customer_id"));
            completeOrders.setHotIceNone(rs.getString("hot_ice_none"));
            completeOrders.setMenu_size(rs.getString("menu_size"));
            completeOrders.setOption_info(rs.getString("option_name"));
            completeOrders.setCategory_name(rs.getString("category_name"));
            completeOrders.setCafe_id(rs.getString("cafe_id"));

            return completeOrders;
         }
      });
   }
   
   public boolean deleteOrder(String orderlist_id, String cafe_id){
      String sql = "delete from orderList where orderlist_id = ? and cafe_id = ?";
      
      return (jdbcTemplateObject.update(sql, (String)orderlist_id, cafe_id) == 1);
   }
   
   public boolean countPeople(String cafe_id, String count){
      String sql = "update cafe_info set cafe_cur = ? where cafe_id = ?";
      
      return (jdbcTemplateObject.update(sql, count ,cafe_id) ==1);
   }
   
}