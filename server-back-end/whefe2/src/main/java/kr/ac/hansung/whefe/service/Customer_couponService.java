package kr.ac.hansung.whefe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.whefe.dao.Customer_couponDao;
import kr.ac.hansung.whefe.model.Coupon;
import kr.ac.hansung.whefe.model.Customer_coupon;

@Service
public class Customer_couponService {

	@Autowired
	Customer_couponDao customer_couponDao;

	public List<Customer_coupon> getCustomer_coupon(String customer_id) {
		return customer_couponDao.getCustomer_coupon(customer_id);
	}
	
	public List<Customer_coupon> getCustomer_coupon(String cafe_id, String customer_id) {
		return customer_couponDao.getCustomer_coupon(cafe_id, customer_id);
	}
	
	public boolean disableCoupon(String customer_id, String cafe_id, String coupon_name) {
		return customer_couponDao.disableCoupon(customer_id, cafe_id, coupon_name);
	}
	
	public boolean downloadCoupon(Customer_coupon customer_coupon) {
		return customer_couponDao.downloadCoupon(customer_coupon);
	}
}
