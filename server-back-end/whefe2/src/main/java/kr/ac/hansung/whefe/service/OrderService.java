package kr.ac.hansung.whefe.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.ac.hansung.whefe.dao.OrderDao;
import kr.ac.hansung.whefe.model.Order;
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    
    public void setOrderDao(OrderDao orderDao){
        this.orderDao = orderDao;
    }
    
    public List<Order> getOrders(){
        return orderDao.getOrders();
    }
    
    public Order getOrderForEdit(String menu_name){
        return orderDao.getOrderForEdit(menu_name);
    }
    
    public boolean editMenuCompleted(String menu_name){
        return orderDao.editMenuCompleted(menu_name);
    }
    
    public List<Order> getCompleteOrders(){
        return orderDao.getCompleteOrders();
    }
}