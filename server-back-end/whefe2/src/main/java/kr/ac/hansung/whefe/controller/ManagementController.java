package kr.ac.hansung.whefe.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

	/*
	 * @RequestMapping(value = "/management/menu/{category_name}") public String
	 * menuManagement2(@PathVariable String category_name, Model model) {
	 * List<Cafe_menu> cafe_menu = cafe_menuService.getCafe_menu(category_name);
	 * System.out.println(cafe_menu.get(0)); model.addAttribute("cafe_menu",
	 * cafe_menu); model.addAttribute("category_name", category_name); return
	 * "web-front-end/04.Menu-Detail-Management"; }
	 */

	@RequestMapping(value = "/management/menu/addmenu")
	public String addMenu(HttpServletRequest request, HttpServletResponse response) {
		String cafe_id = request.getParameter("cafe_id");
		String category_name = request.getParameter("category_name");
		String menu_name = request.getParameter("menu_name");
		String hot_small = request.getParameter("hot_small");
		String ice_small = request.getParameter("ice_small");
		System.out.println(cafe_id + "!!!!!!!!!!!!!!!!!!!!!" + category_name + "!!!!!!!!!!!!!!!!!!!!");

		String[] param = { cafe_id, category_name, menu_name, hot_small, ice_small };

		if (!cafe_menuService.addMenu(param)) {
			System.out.println("addMenu failed......");
		}
		return "redirect:/management";
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
