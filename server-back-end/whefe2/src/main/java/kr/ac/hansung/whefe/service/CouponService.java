package kr.ac.hansung.whefe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.whefe.dao.CouponDao;
import kr.ac.hansung.whefe.model.Coupon;

@Service
public class CouponService {
	
	
	@Autowired
	private CouponDao couponDao;
	
	public void setCouponDao(CouponDao couponDao) {
		this.couponDao = couponDao;
	}

	public List<Coupon> getCoupons() {
		System.out.println("Service!!!!!!!!!!!!!!");
		return couponDao.getCoupons();
	}
	
	public List<Coupon> getCoupons(String cafe_id) {
		System.out.println("Service!!!!!!!!!!!!!!");
		return couponDao.getCoupons(cafe_id);
	}
	
	public boolean addCoupon(Coupon coupon) {
		return couponDao.addCoupon(coupon);
	}

	public boolean deleteCoupon(String coupon_name, String cafe_id) {
		return couponDao.deleteCoupon(coupon_name, cafe_id);
	}
}
