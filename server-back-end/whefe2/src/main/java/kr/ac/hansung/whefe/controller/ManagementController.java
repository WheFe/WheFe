package kr.ac.hansung.whefe.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import kr.ac.hansung.whefe.model.Cafe_info;
import kr.ac.hansung.whefe.model.Cafe_menu;
import kr.ac.hansung.whefe.model.Category;
import kr.ac.hansung.whefe.model.Coupon;
import kr.ac.hansung.whefe.model.ImageUpload;
import kr.ac.hansung.whefe.model.Opt;
import kr.ac.hansung.whefe.model.Order;
import kr.ac.hansung.whefe.service.Cafe_infoService;
import kr.ac.hansung.whefe.service.Cafe_menuService;
import kr.ac.hansung.whefe.service.CategoryService;
import kr.ac.hansung.whefe.service.CouponService;
import kr.ac.hansung.whefe.service.OptService;
import kr.ac.hansung.whefe.service.OrderService;

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
	@Autowired
	private OrderService orderService;

	///////////////////////////////////////////////////////////////////////////////////////////
	// 쿠폰 컨트롤러
	@RequestMapping(value = "/management/coupon")
	public String couponManagement(Model model,Principal principal) throws ParseException {
		System.out.println("Controller!!!!!!!!!!!!");
		String cafe_id = principal.getName();
		List<Coupon> coupons = couponService.getCoupons(cafe_id);
		/*
		 * Date today = new Date(); DateFormat sdFormat = new
		 * SimpleDateFormat("yyyyMMdd"); Date coupon_end2 =
		 * sdFormat.parse("20100222");
		 */
		model.addAttribute("coupons", coupons);
		return "web-front-end/05.Coupon_Management";
	}

	@RequestMapping(value = "/management/coupon/addcoupon")
	public String addCoupon(Coupon coupon, HttpServletRequest request,Principal principal) {
		System.out.println(coupon.toString());
		if (!couponService.addCoupon(coupon)) {
			System.out.println("Adding coupon failed......");
		}
		return "redirect:/management/coupon";
	}

	@RequestMapping(value = "/management/coupon/deletecoupon/{coupon_name}")
	public String deleteCoupon(@PathVariable String coupon_name, HttpServletRequest request) {
		String cafe_id = request.getParameter("cafe_id");
		if (!couponService.deleteCoupon(coupon_name, cafe_id)) {
			System.out.println("Deleting coupon Failed.........");

		}
		return "redirect:/management/coupon";
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	// 주문 컨트롤러

	@RequestMapping(value = "/management/order")
	public String orderManagement(Model model, Principal principal) {
		String cafe_id = principal.getName();
		List<Order> orders = orderService.getOrders(cafe_id);
		model.addAttribute("orders", orders);
		List<Order> completeOrders = orderService.getCompleteOrders(cafe_id);
		model.addAttribute("completeOrders", completeOrders);
		Cafe_info cafe_info = cafe_infoService.getCurrent(cafe_id);
		model.addAttribute("cafe_info", cafe_info);

		return "web-front-end/06.Order_Check";
	}

	/*
	 * @RequestMapping(value = "/management/order/{orderlist_id}") // primary
	 * key // 삽입해야할 듯 90% // 완성 public String orderComplete(@PathVariable String
	 * orderlist_id, Model model, Principal principal) { String cafe_id =
	 * principal.getName(); Order completeOrder =
	 * orderService.getOrderForEdit(orderlist_id, cafe_id);
	 * orderService.editMenuCompleted(orderlist_id, cafe_id);
	 * System.out.println("do edit");
	 * System.out.println("do call completed orderlist"); return
	 * "redirect:/management/order"; }
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/management/order/{orderlist_id}/{token}") 
	public String orderComplete(@PathVariable String orderlist_id, @PathVariable String token, Model model, Principal principal) {
		String cafe_id = principal.getName();
		Order completeOrder = orderService.getOrderForEdit(orderlist_id, cafe_id);
		orderService.editMenuCompleted(orderlist_id, cafe_id);
		System.out.println("do edit");
		System.out.println("do call completed orderlist");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "key=AAAAE9htXdE:APA91bGlZ_v-QMCrXDg9HAyiTgYrvxUmrmFcxmTnTXw2Ya3EwImD0yoOuJTmSRzR1N-iM8x9GtdTCew3x4wCOATQ3k40XT9J8GgyQ9cCQXFMbGDiEwKxv_Dy6GdMQDulQ-DP4DwWDGDw");
		String jsonObj = "{\n"
				+ " \"to\" : \""+token+"\",\n"
				+ " \"data\" : { \"message\" : \"HHHHHHHHH\" \n" + " }\n" + "}";

		HttpEntity request;
		request = new HttpEntity(jsonObj, headers);
		ResponseEntity<String> result = restTemplate.exchange("https://fcm.googleapis.com/fcm/send", HttpMethod.POST,
				request, String.class);
		System.out.println("[result.getBody()]  "+result.getBody());
		return "redirect:/management/order";
	}

	@RequestMapping(value = "/management/order/deleteOrder/{orderlist_id}") 
	public String orderDelete(@PathVariable String orderlist_id, Model model, Principal principal) {
		String cafe_id = principal.getName();
		// System.out.println("orderlist_id" +"!!!!!! " + orderlist_id);
		if (!orderService.deleteOrder(orderlist_id, cafe_id)) {
			System.out.println("Deleting MenuOrder cannot be done.");
		}

		return "redirect:/management/order";
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	// 카테고리 컨트롤러
	@RequestMapping(value = "/management/addcategory")
	public String addCategory(Category category, HttpServletRequest request, Principal principal) {
		String cafe_id = principal.getName();
		String name = request.getParameter("category_name");
		System.out.println(name + "!!!!!!!!!!!!!!!");
		if (!categoryService.addCategory(cafe_id, category)) {
			System.out.println("Adding Category cannot be done");
		}
		/*
		 * if (!categoryService.addCategory(category)) {
		 * System.out.println("Adding Category cannot be done"); }
		 */
		return "redirect:/management";
	}

	@RequestMapping(value = "/management/deletecategory/{category_name}")
	public String deleteCategory(@PathVariable String category_name, HttpServletRequest request, Principal principal) {
		String cafe_id = principal.getName();
		if (!categoryService.deleteCategory(cafe_id, category_name)) {
			System.out.println("Deleting Category cannot be done");
		}
		return "redirect:/management";
	}

	/*
	 * @RequestMapping(value =
	 * "/management/deletecategory",method=RequestMethod.GET) public String
	 * deleteCategory(HttpServletRequest request,HttpServletResponse response) {
	 * String category_name=request.getParameter("category");
	 * System.out.println("해당품목 : "+category_name); if
	 * (!categoryService.deleteCategory(category_name)) {
	 * System.out.println("Deleting Category cannot be done"); } return
	 * "redirect:/management"; }
	 */

	@RequestMapping(value = "/management/editcategory/{category_name}")
	public String editCategory(@PathVariable String category_name, HttpServletRequest request, Principal principal) {
		String cafe_id = principal.getName();
		String newName = request.getParameter("category_name");
		String originalName = request.getParameter("original");
		System.out.println(originalName + " !!!!!!!!!!!!! " + newName + " !!!!!!!!!!!!!!!!");
		if (!categoryService.editCategory(cafe_id, category_name, newName)) {
			System.out.println("editting Category cannot be done");
		}
		return "redirect:/management";
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	// 메뉴 컨트롤러

	/*
	 * @RequestMapping(value = "/management/menu/{category_name}") public String
	 * menuManagement(@PathVariable String category_name, Model model) {
	 * List<Cafe_menu> cafe_menu = cafe_menuService.getCafe_menu(category_name);
	 * if(cafe_menu==null) {
	 * System.out.println("cafe_menu NULL!!!!!!!!!!!!!!!!!!!!!!!"); }
	 * model.addAttribute("cafe_menu", cafe_menu);
	 * model.addAttribute("category_name", category_name); return
	 * "web-front-end/04.Menu-Detail-Management"; }
	 */
	@RequestMapping(value = "/management/menu/{category_name}")
	public String menuManagement(@PathVariable String category_name, Model model, Principal principal) {
		String cafe_id = principal.getName();
		List<Cafe_menu> cafe_menu = cafe_menuService.getCafe_menu(cafe_id, category_name);
		if (cafe_menu == null) {
			System.out.println("cafe_menu NULL!!!!!!!!!!!!!!!!!!!!!!!");
		}
		model.addAttribute("cafe_menu", cafe_menu);
		model.addAttribute("category_name", category_name);
		return "web-front-end/04.Menu-Detail-Management";
	}

	@RequestMapping(value = "/management/menu/addmenu", method = RequestMethod.POST)
	public String addMenu(@RequestParam("menu_image") MultipartFile menu_image, HttpServletRequest request,
			HttpServletResponse response) {
		// System.out.println(imageUpload.getMenu_image().getOriginalFilename()+"!!!!!!!!!!!!!!!!!!!");
		System.out.println(menu_image.getOriginalFilename() + "!!!!!!!!!!!!!!!!!!!");

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

		// hot_size
		String hot_small = request.getParameter("hot_small");
		if (hot_small != null) {
			menu_priceArray.add(hot_small);
			menu_nameArray.add("hot_small");
			hot_ice_noneArray.add("hot");
			menu_sizeArray.add("S");
		}
		String hot_medium = request.getParameter("hot_medium");
		if (hot_medium != null) {
			menu_priceArray.add(hot_medium);
			menu_nameArray.add("hot_medium");
			hot_ice_noneArray.add("hot");
			menu_sizeArray.add("M");

		}
		String hot_large = request.getParameter("hot_large");
		if (hot_large != null) {
			menu_priceArray.add(hot_large);
			menu_nameArray.add("hot_large");
			hot_ice_noneArray.add("hot");
			menu_sizeArray.add("L");

		}
		String hot_none = request.getParameter("hot_none");
		if (hot_none != null) {
			menu_priceArray.add(hot_none);
			menu_nameArray.add("hot_none");
			hot_ice_noneArray.add("hot");
			menu_sizeArray.add("only");
		}
		String ice_small = request.getParameter("ice_small");
		if (ice_small != null) {
			menu_priceArray.add(ice_small);
			menu_nameArray.add("ice_small");
			hot_ice_noneArray.add("ice");
			menu_sizeArray.add("S");
		}
		String ice_medium = request.getParameter("ice_medium");
		if (ice_medium != null) {
			menu_priceArray.add(ice_medium);
			menu_nameArray.add("ice_medium");
			hot_ice_noneArray.add("ice");
			menu_sizeArray.add("M");
		}
		String ice_large = request.getParameter("ice_large");
		if (ice_large != null) {
			menu_priceArray.add(ice_large);
			menu_nameArray.add("ice_large");
			hot_ice_noneArray.add("ice");
			menu_sizeArray.add("L");
		}
		String ice_none = request.getParameter("ice_none");
		if (ice_none != null) {
			menu_priceArray.add(ice_none);
			menu_nameArray.add("ice_none");
			hot_ice_noneArray.add("ice");
			menu_sizeArray.add("only");
		}
		String none_small = request.getParameter("none_small");
		if (none_small != null) {
			menu_priceArray.add(none_small);
			menu_nameArray.add("none_small");
			hot_ice_noneArray.add("none");
			menu_sizeArray.add("S");
		}
		String none_medium = request.getParameter("none_medium");
		if (none_medium != null) {
			menu_priceArray.add(none_medium);
			menu_nameArray.add("none_medium");
			hot_ice_noneArray.add("none");
			menu_sizeArray.add("M");
		}
		String none_large = request.getParameter("none_large");
		if (none_large != null) {
			menu_priceArray.add(none_large);
			menu_nameArray.add("none_large");
			hot_ice_noneArray.add("none");
			menu_sizeArray.add("L");

		}
		String none_none = request.getParameter("none_none");
		if (none_none != null) {
			menu_priceArray.add(none_none);
			menu_nameArray.add("none_none");
			hot_ice_noneArray.add("none");
			menu_sizeArray.add("only");
		}
		/*
		 * String menu_image = request.getParameter("menu_image");
		 * System.out.println(menu_image+"!!!!!!!!!!!!!!!!!!!!!!!");
		 */
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Path savePath = Paths.get(rootDirectory + "\\resources\\images\\" + menu_image.getOriginalFilename());
		if (menu_image != null && !menu_image.isEmpty()) {
			try {
				menu_image.transferTo(new File(menu_image.toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String imageFilename = menu_image.getOriginalFilename();
		pkArray.add(imageFilename);

		if (menu_image.isEmpty() == false) {
			System.out.println("---------file start ---------");
			System.out.println("name: " + menu_image.getName());
			System.out.println("filename: " + menu_image.getOriginalFilename());
			System.out.println("size: " + menu_image.getSize());
			System.out.println("---------file end ---------");

		}

		if (!cafe_menuService.addMenu(pkArray, menu_nameArray, menu_sizeArray, hot_ice_noneArray, menu_priceArray)) {

		}
		return "redirect:/management/menu/" + category_name;
	}

	@RequestMapping(value = "/management/menu/editmenuname")
	public String editMenu_name(HttpServletRequest request, Principal principal) {
		String cafe_id = principal.getName();
		String new_name = request.getParameter("new_name");
		String menu_name = request.getParameter("menu_name");
		String category_name = request.getParameter("category_name");
		cafe_menuService.editMenu_name(cafe_id, menu_name, new_name);
		return "redirect:/management/menu/" + category_name;

	}

	@RequestMapping(value = "/management/menu/deletemenu")
	public String deleteMenu(HttpServletRequest request, Principal principal) {
		String cafe_id = principal.getName();
		String menu_name = request.getParameter("menu_name");
		String category_name = request.getParameter("category_name");
		// String menu_name = request.getParameter("menu_name");
		cafe_menuService.deleteMenu(cafe_id, menu_name);
		return "redirect:/management/menu/" + category_name;
	}

	@RequestMapping(value = "/management/menu/editmenu")
	public String editMM() {
		return "web-front-end/Price-Management";
	}

	@RequestMapping(value = "/management/menu/editmenu/{menu_name}/{category_name}")
	public String editMenu(@PathVariable String menu_name, @PathVariable String category_name, Model model, Principal principal) {
		String cafe_id = principal.getName();
		String hot_small = "";
		String hot_medium = "";
		String hot_large = "";
		String hot_none = "";
		String ice_small = "";
		String ice_medium = "";
		String ice_large = "";
		String ice_none = "";
		String none_small = "";
		String none_medium = "";
		String none_large = "";
		String none_none = "";

		List<Cafe_menu> menus = cafe_menuService.getMenu_price(cafe_id, menu_name);
		for (int i = 0; i < menus.size(); i++) {
			System.out.println(menus.get(i).toString());
		}
		for (int i = 0; i < menus.size(); i++) {
			if (menus.get(i).getHot_ice_none().equals("hot")) {
				if (menus.get(i).getMenu_size().equals("S")) {
					hot_small = menus.get(i).getMenu_price();
				} else if (menus.get(i).getMenu_size().equals("M")) {
					hot_medium = menus.get(i).getMenu_price();
				} else if (menus.get(i).getMenu_size().equals("L")) {
					hot_large = menus.get(i).getMenu_price();
				} else if (menus.get(i).getMenu_size().equals("only")) {
					System.out.println("hot_none!!!!!!!!!!!!!!!!!!");
					hot_none = menus.get(i).getMenu_price();
				}
			}
			if (menus.get(i).getHot_ice_none().equals("ice")) {
				if (menus.get(i).getMenu_size().equals("S")) {
					ice_small = menus.get(i).getMenu_price();
				} else if (menus.get(i).getMenu_size().equals("M")) {
					ice_medium = menus.get(i).getMenu_price();
				} else if (menus.get(i).getMenu_size().equals("L")) {
					ice_large = menus.get(i).getMenu_price();
				} else if (menus.get(i).getMenu_size().equals("only")) {
					ice_none = menus.get(i).getMenu_price();
				}
			}
			if (menus.get(i).getHot_ice_none().equals("none")) {
				if (menus.get(i).getMenu_size().equals("S")) {
					none_small = menus.get(i).getMenu_price();
				} else if (menus.get(i).getMenu_size().equals("M")) {
					none_medium = menus.get(i).getMenu_price();
				} else if (menus.get(i).getMenu_size().equals("L")) {
					none_large = menus.get(i).getMenu_price();
				} else if (menus.get(i).getMenu_size().equals("only")) {
					none_none = menus.get(i).getMenu_price();
				}
			}
		}
		model.addAttribute("hot_small", hot_small);
		model.addAttribute("hot_medium", hot_medium);
		model.addAttribute("hot_large", hot_large);
		model.addAttribute("hot_none", hot_none);
		model.addAttribute("ice_small", ice_small);
		model.addAttribute("ice_medium", ice_medium);
		model.addAttribute("ice_large", ice_large);
		model.addAttribute("ice_none", ice_none);
		model.addAttribute("none_small", none_small);
		model.addAttribute("none_medium", none_medium);
		model.addAttribute("none_large", none_large);
		model.addAttribute("none_none", none_none);
		model.addAttribute("menu_name", menu_name);
		model.addAttribute("cafe_id", cafe_id);
		//model.addAttribute("category_name", category_name);

		return "web-front-end/Price-Management";
	}
	@RequestMapping(value = "/management/menu/editmenus", method = RequestMethod.POST)
	public String editMenuPost(HttpServletRequest request, HttpServletResponse response, Principal principal) {
		System.out.println("가격수정하기!");
		ArrayList<String> menu_priceArray = new ArrayList<String>();
		ArrayList<String> menu_nameArray = new ArrayList<String>();
		ArrayList<String> hot_ice_noneArray = new ArrayList<String>();
		ArrayList<String> menu_sizeArray = new ArrayList<String>();
		ArrayList<String> pkArray = new ArrayList<String>();
		
		String cafe_id = request.getParameter("cafe_id");
		//String cafe_id = principal.getName();
		pkArray.add(cafe_id);

		String category_name = request.getParameter("category_name");
		pkArray.add(category_name);

		String menu_name = request.getParameter("menu_name");
		pkArray.add(menu_name);

		// hot_size
		String hot_small = request.getParameter("hot_small");
		if (hot_small != null) {
			menu_priceArray.add(hot_small);
			menu_nameArray.add("hot_small");
			hot_ice_noneArray.add("hot");
			menu_sizeArray.add("S");
		}
		String hot_medium = request.getParameter("hot_medium");
		if (hot_medium != null) {
			menu_priceArray.add(hot_medium);
			menu_nameArray.add("hot_medium");
			hot_ice_noneArray.add("hot");
			menu_sizeArray.add("M");

		}
		String hot_large = request.getParameter("hot_large");
		if (hot_large != null) {
			menu_priceArray.add(hot_large);
			menu_nameArray.add("hot_large");
			hot_ice_noneArray.add("hot");
			menu_sizeArray.add("L");

		}
		String hot_none = request.getParameter("hot_none");
		if (hot_none != null) {
			menu_priceArray.add(hot_none);
			menu_nameArray.add("hot_none");
			hot_ice_noneArray.add("hot");
			menu_sizeArray.add("only");
		}
		String ice_small = request.getParameter("ice_small");
		if (ice_small != null) {
			menu_priceArray.add(ice_small);
			menu_nameArray.add("ice_small");
			hot_ice_noneArray.add("ice");
			menu_sizeArray.add("S");
		}
		String ice_medium = request.getParameter("ice_medium");
		if (ice_medium != null) {
			menu_priceArray.add(ice_medium);
			menu_nameArray.add("ice_medium");
			hot_ice_noneArray.add("ice");
			menu_sizeArray.add("M");
		}
		String ice_large = request.getParameter("ice_large");
		if (ice_large != null) {
			menu_priceArray.add(ice_large);
			menu_nameArray.add("ice_large");
			hot_ice_noneArray.add("ice");
			menu_sizeArray.add("L");
		}
		String ice_none = request.getParameter("ice_none");
		if (ice_none != null) {
			menu_priceArray.add(ice_none);
			menu_nameArray.add("ice_none");
			hot_ice_noneArray.add("ice");
			menu_sizeArray.add("only");
		}
		String none_small = request.getParameter("none_small");
		if (none_small != null) {
			menu_priceArray.add(none_small);
			menu_nameArray.add("none_small");
			hot_ice_noneArray.add("none");
			menu_sizeArray.add("S");
		}
		String none_medium = request.getParameter("none_medium");
		if (none_medium != null) {
			menu_priceArray.add(none_medium);
			menu_nameArray.add("none_medium");
			hot_ice_noneArray.add("none");
			menu_sizeArray.add("M");
		}
		String none_large = request.getParameter("none_large");
		if (none_large != null) {
			menu_priceArray.add(none_large);
			menu_nameArray.add("none_large");
			hot_ice_noneArray.add("none");
			menu_sizeArray.add("L");

		}
		String none_none = request.getParameter("none_none");
		if (none_none != null) {
			menu_priceArray.add(none_none);
			menu_nameArray.add("none_none");
			hot_ice_noneArray.add("none");
			menu_sizeArray.add("only");
		}

		if (!cafe_menuService.editMenu(pkArray, menu_nameArray, menu_sizeArray, hot_ice_noneArray, menu_priceArray)) {

		}
		return "redirect:/management/menu/" + category_name;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	// 옵션 컨트롤러

	@RequestMapping(value = "/management/option/{category_name}")
	public String optionManagement(Model model, @PathVariable String category_name, Principal principal) {
		String cafe_id = principal.getName();
		List<Opt> opt = optService.getOpt(cafe_id,category_name);
		model.addAttribute("opt", opt);
		model.addAttribute("category_name", category_name);
		return "web-front-end/03-01.Menu_Category_Option_Management";
	}

	@RequestMapping(value = "/management/option/addoption")
	public String addOption(Opt opt, HttpServletRequest request, HttpServletResponse response, Principal principal) {
		String cafe_id = principal.getName();
		String option_price = request.getParameter("option_price");
		String opt_price = opt.getOption_price();
		String option_name = request.getParameter("option_name");
		String opt_name = opt.getOption_name();
		String category_name = request.getParameter("category_name");
		opt.setCafe_id(cafe_id);

		if (!optService.addOption(opt)) {
			System.out.println("addOption failed......");
		}
		return "redirect:/management/option/" + category_name;
	}

	@RequestMapping(value = "/management/option/editoption/{original}")
	public String editOption(@PathVariable String original, HttpServletRequest request, Principal principal) {
		String cafe_id = principal.getName();
		String category_name = request.getParameter("category_name");
		String option_name = request.getParameter("option_name");
		String option_price = request.getParameter("option_price");
		System.out.println(category_name + " !!!" + original + "!!!!!!!!!!! " + option_name + "!!! " + option_price);

		if (!optService.editOption(cafe_id, original, option_name, option_price, category_name)) {
			System.out.println("editOption failed.....");
		}
		return "redirect:/management/option/" + category_name;
	}

	@RequestMapping(value = "/management/option/deleteoption/{original}")
	public String deleteOption(@PathVariable String original, HttpServletRequest request, Principal principal) {
		String cafe_id = principal.getName();
		System.out.println(original + "!!!!!!!!!!!!!!!!!!!");
		String category_name = request.getParameter("category_name");
		if (!optService.deleteOption(cafe_id, original)) {
			System.out.println("deleting option failed.....");
		}
		return "redirect:/management/option/" + category_name;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value = "/cafeinfo")
	public String cafeinfo(HttpServletRequest request, Model model, Principal principal) {
		String cafe_id = request.getParameter("cafe_id");
		Cafe_info cafe_info = new Cafe_info();
		cafe_info = cafe_infoService.getCafe_info(cafe_id);
		model.addAttribute("cafe_info", cafe_info);

		return "web-front-end/UserInfo";
	}

	@RequestMapping(value = "/cafeinfo", method = RequestMethod.POST)
	public String cafeinfoPost(HttpServletRequest request) {
		System.out.println("management/cafeinfo/{cafe_id} " + request.getParameter("cafe_id") + "!!!!!!!!!!!!!");
		Cafe_info cafe_info = new Cafe_info();
		cafe_info.setCafe_id(request.getParameter("cafe_id"));
		cafe_info.setCafe_pw(request.getParameter("cafe_pw"));
		cafe_info.setCafe_name(request.getParameter("cafe_name"));
		cafe_info.setCafe_tel(request.getParameter("cafe_tel"));
		cafe_info.setCafe_address(request.getParameter("cafe_address"));
		cafe_info.setCafe_open(request.getParameter("cafe_open"));
		cafe_info.setCafe_end(request.getParameter("cafe_end"));
		cafe_info.setCafe_intro(request.getParameter("cafe_intro"));
		cafe_info.setCafe_max(request.getParameter("cafe_max"));
		/*
		 * MultipartFile cafe_image = cafe_info.getCafe_image1(); String
		 * rootDirectory =
		 * request.getSession().getServletContext().getRealPath("/");
		 * 
		 * Path savePath = Paths.get(rootDirectory + "\\resources\\images\\" +
		 * cafe_image.getOriginalFilename()); if (cafe_image != null &&
		 * !cafe_image.isEmpty()) { try { cafe_image.transferTo(new
		 * File(savePath.toString())); } catch (Exception e) {
		 * e.printStackTrace(); } }
		 * 
		 * cafe_info.setImageFilename1(cafe_image.getOriginalFilename());
		 * 
		 * if (cafe_image.isEmpty() == false) {
		 * System.out.println("---------file start ---------");
		 * System.out.println("name: " + cafe_image.getName());
		 * System.out.println("filename: " + cafe_image.getOriginalFilename());
		 * System.out.println("size: " + cafe_image.getSize());
		 * System.out.println("---------file end ---------");
		 * 
		 * }
		 */
		if (!cafe_infoService.editCafe_info(cafe_info)) {
			System.out.println("Adding info cannot be done");
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/image")
	public String viewImage(Model model, HttpServletRequest request) {
		System.out.println();
		List<Cafe_info> cafe_info = cafe_infoService.getCafe_info();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		System.out.println(rootDirectory + "!!!!!!!!!!!!!!!!!!!!!!");
		model.addAttribute("cafe_info", cafe_info.get(cafe_info.size() - 1));
		return "web-front-end/image";
	}
}
