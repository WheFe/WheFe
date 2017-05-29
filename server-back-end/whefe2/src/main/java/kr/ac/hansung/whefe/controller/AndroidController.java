package kr.ac.hansung.whefe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.hansung.whefe.model.Cafe_info;
import kr.ac.hansung.whefe.model.Cafe_menu;
import kr.ac.hansung.whefe.model.Category;
import kr.ac.hansung.whefe.model.Coupon;
import kr.ac.hansung.whefe.service.Cafe_infoService;
import kr.ac.hansung.whefe.service.Cafe_menuService;
import kr.ac.hansung.whefe.service.CategoryService;
import kr.ac.hansung.whefe.service.CouponService;
import kr.ac.hansung.whefe.service.Customer_infoService;

@RestController
@Controller
public class AndroidController {
	
	@Autowired
	private Customer_infoService customer_infoService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private Cafe_menuService cafe_menuService;
	@Autowired
	private Cafe_infoService cafe_infoService;
	@Autowired
	private CouponService couponService;
	
	@RequestMapping("/android/login")
	public Map<String,String> androidLogin(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		Map<String,String> result = new HashMap<String,String>();
		result.put("data1", "메모1입니다");
		result.put("data2", "메모2입니다");
		/*List<Customer_info> customers = customer_infoService.getCutomer_infoById(id);
		Customer_info customer_info = customers.get(0);
		if(id == customer_info.getCustomer_id()) {
			if(pw == customer_info.getCustomer_pw()) {
				success = true;
			}
		}*/
		System.out.println(id+" "+ pw);
		return result;
	}
	@RequestMapping("/android/category")
	public ResponseEntity<List<Category>> test(Model model, HttpServletRequest request) {
		String cafe_id = request.getParameter("cafe_id");
		Logger logger = Logger.getLogger("xxx");
		System.out.println("Yeah!");
		List<Category> categories = categoryService.getCategories(cafe_id);
		/*JSONObject jsonOBj = new JSONObject();
		jsonOBj.put("name","ddd");
		jsonOBj.put("number", 01010);
		String json = jsonOBj.toString();
		System.out.println(json);*/
		//return new ResponseEntity<JSONObject>(jsonOBj, HttpStatus.OK);
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}
	
	@RequestMapping("/android/menu")
	public ResponseEntity<List<Cafe_menu>> menu(HttpServletRequest request, Model model) {
		String cafe_id = request.getParameter("cafe_id");
		System.out.println(cafe_id +"!!!!!!!!!!!!!!!!!!");
		Logger logger = Logger.getLogger("xxx");
		System.out.println("Yeah2!");
		List<Cafe_menu> cafe_menu = cafe_menuService.getCafe_menu(cafe_id);
		/*JSONObject jsonOBj = new JSONObject();
		jsonOBj.put("name","ddd");
		jsonOBj.put("number", 01010);
		String json = jsonOBj.toString();
		System.out.println(json);*/
		//return new ResponseEntity<JSONObject>(jsonOBj, HttpStatus.OK);
		return new ResponseEntity<List<Cafe_menu>>(cafe_menu, HttpStatus.OK);
	}
	
	@RequestMapping("/android/cafeinfo")
	public ResponseEntity<List<Cafe_info>> getCafe_info() {
		List<Cafe_info> cafeinfos = cafe_infoService.getCafe_info();
		return new ResponseEntity<List<Cafe_info>>(cafeinfos, HttpStatus.OK);
	}
	
	@RequestMapping("/android/cafecoupon")
	public ResponseEntity<List<Coupon>> getCafe_coupon(HttpServletRequest request) {
		String cafe_id = request.getParameter("cafe_id");
		List<Coupon> coupons = couponService.getCoupons(cafe_id);
		return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
	}
}
