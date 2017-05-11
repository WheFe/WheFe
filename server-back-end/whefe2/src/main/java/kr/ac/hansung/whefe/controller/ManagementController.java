package kr.ac.hansung.whefe.controller;

import static org.hamcrest.CoreMatchers.notNullValue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.hansung.whefe.model.Cafe_menu;
import kr.ac.hansung.whefe.model.Category;
import kr.ac.hansung.whefe.model.Coupon;
import kr.ac.hansung.whefe.model.Opt;
import kr.ac.hansung.whefe.service.Cafe_infoService;
import kr.ac.hansung.whefe.service.Cafe_menuService;
import kr.ac.hansung.whefe.service.CategoryService;
import kr.ac.hansung.whefe.service.CouponService;
import kr.ac.hansung.whefe.service.OptService;

@Controller
public class ManagementController {

	@Autowired
	private Cafe_infoService cafe_infoService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private Cafe_menuService cafe_menuService;
	@Autowired
	private OptService optService;
	@Autowired
	private CouponService couponService;
	///////////////////////////////////////////////////////////////////////////////////////////
	// 쿠폰 컨트롤러
	@RequestMapping(value = "/management/coupon")
	public String couponManagement(Model model) throws ParseException {
		System.out.println("Controller!!!!!!!!!!!!");
		List<Coupon> coupons = couponService.getCoupons();
		/*Date today = new Date();
		DateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
		Date coupon_end2 = sdFormat.parse("20100222");*/
		model.addAttribute("coupons", coupons);
		return "web-front-end/05.Coupon_Management";
	}
	
	@RequestMapping(value = "/management/coupon/addcoupon")
	public String addCoupon(Coupon coupon, HttpServletRequest request) {
		System.out.println(coupon.toString());
		if(!couponService.addCoupon(coupon)) {
			System.out.println("Adding coupon failed......");
		}
		return "redirect:/management/coupon";
	}
	
	@RequestMapping(value="/management/coupon/deletecoupon/{coupon_name}")
	public String deleteCoupon(@PathVariable String coupon_name, HttpServletRequest request) {
		String cafe_id = request.getParameter("cafe_id");
		if(!couponService.deleteCoupon(coupon_name, cafe_id)) {
			System.out.println("Deleting coupon Failed.........");
			
		}
		return "redirect:/management/coupon";
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	// 주문 컨트롤러
	@RequestMapping(value = "/management/order")
	public String orderManagement() {
		return "web-front-end/06.Order_Check";
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	// 카테고리 컨트롤러
	@RequestMapping(value = "/management/addcategory")
	public String addCategory(Category category, HttpServletRequest request) {

		String name = request.getParameter("category_name");
		System.out.println(name + "!!!!!!!!!!!!!!!");
		if (!categoryService.addCategory(category)) {
			System.out.println("Adding Category cannot be done");
		}
		return "redirect:/management";
	}

	@RequestMapping(value = "/management/deletecategory/{category_name}")
	public String deleteCategory(@PathVariable String category_name, HttpServletRequest request) {
		if (!categoryService.deleteCategory(category_name)) {
			System.out.println("Deleting Category cannot be done");
		}
		return "redirect:/management";
	}

	@RequestMapping(value = "/management/editcategory/{category_name}")
	public String editCategory(@PathVariable String category_name, HttpServletRequest request) {
		String newName = request.getParameter("category_name");
		String originalName = request.getParameter("original");
		System.out.println(originalName + " !!!!!!!!!!!!! " + newName + " !!!!!!!!!!!!!!!!");
		if (!categoryService.editCategory(category_name, newName)) {
			System.out.println("editting Category cannot be done");
		}
		return "redirect:/management";
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	// 메뉴 컨트롤러
	
	@RequestMapping(value = "/management/menu/{category_name}")
	public String menuManagement(@PathVariable String category_name, Model model) {
		List<Cafe_menu> cafe_menu = cafe_menuService.getCafe_menu(category_name);
		if(cafe_menu==null) {
			System.out.println("cafe_menu NULL!!!!!!!!!!!!!!!!!!!!!!!");
		}
		model.addAttribute("cafe_menu", cafe_menu);
		model.addAttribute("category_name", category_name);
		return "web-front-end/04.Menu-Detail-Management";
	}
	
	@RequestMapping(value = "/management/menu/addmenu")
	public String addMenu(HttpServletRequest request, HttpServletResponse response) {
		
		ArrayList<String> menu_priceArray = new ArrayList<String>();
		ArrayList<String> menu_nameArray = new ArrayList<String>();
		ArrayList<String> hot_ice_noneArray = new ArrayList<String>();
		ArrayList<String> menu_sizeArray = new ArrayList<String>();
		ArrayList<String> pkArray = new ArrayList<String>();
		
		String cafe_id = request.getParameter("cafe_id");
		pkArray.add(cafe_id);
		
		String category_name = request.getParameter("category_name");
		pkArray.add(category_name);
		
		String menu_name = request.getParameter("menu_name");
		pkArray.add(menu_name);
		
		//hot_size
		String hot_small = request.getParameter("hot_small");
		if(hot_small!="") {
			menu_priceArray.add(hot_small);
			menu_nameArray.add("hot_small");
			hot_ice_noneArray.add("hot");
			menu_sizeArray.add("S");
		}
		String hot_medium = request.getParameter("hot_medium");
		if(hot_medium!="") {
			menu_priceArray.add(hot_medium);
			menu_nameArray.add("hot_medium");
			hot_ice_noneArray.add("hot");
			menu_sizeArray.add("M");
			
		}
		String hot_large = request.getParameter("hot_large");
		if(hot_large!="") {
			menu_priceArray.add(hot_large);
			menu_nameArray.add("hot_large");
			hot_ice_noneArray.add("hot");
			menu_sizeArray.add("L");
			
		}
		String hot_none = request.getParameter("hot_none");
		if(hot_none!="") {
			menu_priceArray.add(hot_none);
			menu_nameArray.add("hot_none");
			hot_ice_noneArray.add("hot");
			menu_sizeArray.add("only");
		}
		String ice_small = request.getParameter("ice_small");
		if(ice_small!="") {
			menu_priceArray.add(ice_small);
			menu_nameArray.add("ice_small");
			hot_ice_noneArray.add("ice");
			menu_sizeArray.add("S");
		}
		String ice_medium = request.getParameter("ice_medium");
		if(ice_medium!="") {
			menu_priceArray.add(ice_medium);
			menu_nameArray.add("ice_medium");
			hot_ice_noneArray.add("ice");
			menu_sizeArray.add("M");
		}
		String ice_large = request.getParameter("ice_large");
		if(ice_large!="") {
			menu_priceArray.add(ice_large);
			menu_nameArray.add("ice_large");
			hot_ice_noneArray.add("ice");
			menu_sizeArray.add("L");
		}
		String ice_none = request.getParameter("ice_none");
		if(ice_none!="") {
			menu_priceArray.add(ice_none);
			menu_nameArray.add("ice_none");
			hot_ice_noneArray.add("ice");
			menu_sizeArray.add("only");
		}
		String none_small = request.getParameter("none_small");
		if(none_small!="") {
			menu_priceArray.add(none_small);
			menu_nameArray.add("none_small");
			hot_ice_noneArray.add("none");
			menu_sizeArray.add("S");
		}
		String none_medium = request.getParameter("none_medium");
		if(none_medium!="") {
			menu_priceArray.add(none_medium);
			menu_nameArray.add("none_medium");
			hot_ice_noneArray.add("none");
			menu_sizeArray.add("M");
		}
		String none_large = request.getParameter("none_large");
		if(none_large!="") {
			menu_priceArray.add(none_large);
			menu_nameArray.add("none_large");
			hot_ice_noneArray.add("none");
			menu_sizeArray.add("L");
			
		}
		String none_none = request.getParameter("none_none");
		if(none_none!="") {
			menu_priceArray.add(none_none);
			menu_nameArray.add("none_none");
			hot_ice_noneArray.add("none");
			menu_sizeArray.add("only");
		}
		
		
		if(!cafe_menuService.addMenu(pkArray, menu_nameArray, menu_sizeArray, hot_ice_noneArray, menu_priceArray)) {
			
		}
		return "redirect:/management/menu/"+ category_name;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	// 옵션 컨트롤러
	
	@RequestMapping(value = "/management/option/{category_name}")
	public String optionManagement(Model model, @PathVariable String category_name) {
		List<Opt> opt = optService.getOpt(category_name);
		model.addAttribute("opt", opt);
		model.addAttribute("category_name", category_name);
		return "web-front-end/03-01.Menu_Category_Option_Management";
	}

	@RequestMapping(value = "/management/option/addoption")
	public String addOption(Opt opt, HttpServletRequest request, HttpServletResponse response) {
		String option_price = request.getParameter("option_price");
		int opt_price = opt.getOption_price();
		String option_name = request.getParameter("option_name");
		String opt_name = opt.getOption_name();
		String category_name = request.getParameter("category_name");

		if (!optService.addOption(opt)) {
			System.out.println("addOption failed......");
		}
		return "redirect:/management/option/" + category_name;
	}

	@RequestMapping(value = "/management/option/editoption/{original}")
	public String editOption(@PathVariable String original, HttpServletRequest request) {

		String category_name = request.getParameter("category_name");
		String option_name = request.getParameter("option_name");
		String option_price = request.getParameter("option_price");
		System.out.println(category_name + " !!!" + original + "!!!!!!!!!!! " + option_name + "!!! " +option_price);

		if (!optService.editOption(original, option_name, option_price, category_name)) {
			System.out.println("editOption failed.....");
		}
		return "redirect:/management/option/"+ category_name;
	}
	
	@RequestMapping(value="/management/option/deleteoption/{original}")
	public String deleteOption(@PathVariable String original, HttpServletRequest request) {
		System.out.println(original+"!!!!!!!!!!!!!!!!!!!");
		String category_name = request.getParameter("category_name");
		if(!optService.deleteOption(original)) {
			System.out.println("deleting option failed.....");
		}
		return "redirect:/management/option/" + category_name;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////

}
