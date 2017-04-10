package kr.ac.hansung.whefe.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.hansung.whefe.model.Category;
import kr.ac.hansung.whefe.service.Cafe_infoService;
import kr.ac.hansung.whefe.service.CategoryService;

@Controller
public class ManagementController {
	
	@Autowired
	private Cafe_infoService cafe_infoService;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value="/management/coupon")
	public String couponManagement() {
		return "web-front-end/05.Coupon_Menagement";
	}
	
	@RequestMapping(value="/management/order")
	public String orderManagement() {
		return "web-front-end/06.Order_Check";
	}
	
	@RequestMapping(value="/management/addcategory")
	public String addCategory(Category category, HttpServletRequest request) {
		
		String name =request.getParameter("category_name");
		System.out.println(name+"!!!!!!!!!!!!!!!");
		//categoryService.addCategory();
		return "";
	}
}
