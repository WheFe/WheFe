package kr.ac.hansung.whefe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.whefe.dao.Customer_infoDao;
import kr.ac.hansung.whefe.model.Customer_info;

@Service
public class Customer_infoService {

	private Customer_infoDao customer_infoDao;
	@Autowired
	public void setCustomer_infoDao(Customer_infoDao customer_infoDao) {
		this.customer_infoDao = customer_infoDao;
	}
	
	public List<Customer_info> getCutomer_infoById(String id) {
		return customer_infoDao.getCustomer_infoById(id);
	}
	
}
