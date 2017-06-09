package kr.ac.hansung.whefe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.whefe.dao.OrderDao;
import kr.ac.hansung.whefe.model.Order;
import kr.ac.hansung.whefe.model.Orderlist;

@Service
public class OrderService {

   @Autowired
   private OrderDao orderDao;
   
   public void setOrderDao(OrderDao orderDao){
      this.orderDao = orderDao;
   }
   
   public boolean addOrderlist(Orderlist orderlist) {
	   return orderDao.addOrderlist(orderlist);
   }
   
   public List<Order> getOrders(String cafe_id){
      return orderDao.getOrders(cafe_id);
   }
   
   public Order getOrderForEdit(String orderlist_id, String cafe_id){
      return orderDao.getOrderForEdit(orderlist_id, cafe_id);
   }
   
   public boolean editMenuCompleted(String orderlist_id, String cafe_id){
      return orderDao.editMenuCompleted(orderlist_id, cafe_id);
   }
   
   public List<Order> getCompleteOrders(String cafe_id){
      return orderDao.getCompleteOrders(cafe_id);
   }
   
   public boolean deleteOrder(String orderlist_id, String cafe_id){
      return orderDao.deleteOrder(orderlist_id, cafe_id);
   }
   
   public void countPeople(String cafe_id, String count){
      orderDao.countPeople(cafe_id, count);
   }
}