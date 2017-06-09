package kr.ac.hansung.whefe.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import kr.ac.hansung.whefe.model.Cafe_info;
import kr.ac.hansung.whefe.model.Cafe_menu;
import kr.ac.hansung.whefe.model.Category;
import kr.ac.hansung.whefe.model.Coupon;
import kr.ac.hansung.whefe.model.Customer_coupon;
import kr.ac.hansung.whefe.model.Opt;
import kr.ac.hansung.whefe.model.Orderlist;
import kr.ac.hansung.whefe.service.Cafe_infoService;
import kr.ac.hansung.whefe.service.Cafe_menuService;
import kr.ac.hansung.whefe.service.CategoryService;
import kr.ac.hansung.whefe.service.CouponService;
import kr.ac.hansung.whefe.service.Customer_couponService;
import kr.ac.hansung.whefe.service.Customer_infoService;
import kr.ac.hansung.whefe.service.OptService;
import kr.ac.hansung.whefe.service.OrderService;

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
	@Autowired
	private Customer_couponService customer_couponService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OptService optService;

	

	String apiKey = "AIzaSyBrAlcCFvem2Z_Rdq4TLCgiJ5yVB5BEYkE";
	String result = null;

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/android/fcmtest")
	public String sendGcm() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "key=AAAAE9htXdE:APA91bGlZ_v-QMCrXDg9HAyiTgYrvxUmrmFcxmTnTXw2Ya3EwImD0yoOuJTmSRzR1N-iM8x9GtdTCew3x4wCOATQ3k40XT9J8GgyQ9cCQXFMbGDiEwKxv_Dy6GdMQDulQ-DP4DwWDGDw");
		String jsonObj = "{\n"
				+ " \"to\" : \"d7cslhutwU8:APA91bF0RBnbWtRdUQhohWkqtQgfIO4DFGYf1fn9ryfoEf9mhCahnWe2LqVBE_cR-c2rNhxEoMlmJPrek_MCRgsk9-pcJxzdjP0IfvBKlfLj8wwxwRVsvGuMXG_rXoDaPG1CWLkYx5fM\",\n"
				+ " \"data\" : { \"message\" : \"음료가 준비 되었습니다\" \n" + " }\n" + "}";

		HttpEntity request;
		request = new HttpEntity(jsonObj, headers);
		ResponseEntity<String> result = restTemplate.exchange("https://fcm.googleapis.com/fcm/send", HttpMethod.POST,
				request, String.class);
		return result.getBody();
	}

	
	
	/*@RequestMapping("/android/fcm")
	public String fcmPush() {
		//String parameters = "{\"data\" : {\"message\" : {\"title\":\"test\",\"content\":\"test content\",\"imgUrl\":\"\",\"link\":\"\"}},\"to\" : \"AIzaSyBrAlcCFvem2Z_Rdq4TLCgiJ5yVB5BEYkE\"}";

		try {
			result = sendPost(url, parameters);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}*/

	public String sendPost(String url, String parameters) throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };
		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(null, trustAllCerts, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Authorization", "key=AAAAE9htXdE:APA91bGlZ_v-QMCrXDg9HAyiTgYrvxUmrmFcxmTnTXw2Ya3EwImD0yoOuJTmSRzR1N-iM8x9GtdTCew3x4wCOATQ3k40XT9J8GgyQ9cCQXFMbGDiEwKxv_Dy6GdMQDulQ-DP4DwWDGDw");
		String urlParameters = parameters;

		// post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.write(urlParameters.getBytes("UTF-8"));
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		StringBuffer response = new StringBuffer();

		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		}
		// result
		System.out.println(response.toString());
		return response.toString();
	}

	@RequestMapping("/android/login")
	public Map<String, String> androidLogin(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		Map<String, String> result = new HashMap<String, String>();
		result.put("data1", "메모1입니다");
		result.put("data2", "메모2입니다");
		/*
		 * List<Customer_info> customers =
		 * customer_infoService.getCutomer_infoById(id); Customer_info
		 * customer_info = customers.get(0); if(id ==
		 * customer_info.getCustomer_id()) { if(pw ==
		 * customer_info.getCustomer_pw()) { success = true; } }
		 */
		System.out.println(id + " " + pw);
		return result;
	}

	@RequestMapping("/android/category")
	public ResponseEntity<List<Category>> test(Model model, HttpServletRequest request) {
		String cafe_id = request.getParameter("cafe_id");
		System.out.println("============="+cafe_id + "에 오신 것을 환영합니다=============");
		Logger logger = Logger.getLogger("xxx");
		System.out.println("=============앱이 카테고리에 접근하였습니다=============");
		List<Category> categories = categoryService.getCategories(cafe_id);
		/*
		 * JSONObject jsonOBj = new JSONObject(); jsonOBj.put("name","ddd");
		 * jsonOBj.put("number", 01010); String json = jsonOBj.toString();
		 * System.out.println(json);
		 */
		// return new ResponseEntity<JSONObject>(jsonOBj, HttpStatus.OK);
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}

	@RequestMapping("/android/menu")
	public ResponseEntity<List<Cafe_menu>> menu(HttpServletRequest request, Model model) {
		String cafe_id = request.getParameter("cafe_id");
		Logger logger = Logger.getLogger("xxx");
		System.out.println("=============앱이 메뉴에 접근하였습니다=============");
		List<Cafe_menu> cafe_menu = cafe_menuService.getCafe_menu(cafe_id);
		/*
		 * JSONObject jsonOBj = new JSONObject(); jsonOBj.put("name","ddd");
		 * jsonOBj.put("number", 01010); String json = jsonOBj.toString();
		 * System.out.println(json);
		 */
		// return new ResponseEntity<JSONObject>(jsonOBj, HttpStatus.OK);
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

	@RequestMapping("/android/customercoupon")
	public ResponseEntity<List<Customer_coupon>> getCustomer_coupon(HttpServletRequest request) {
		String customer_id = request.getParameter("customer_id");
		List<Customer_coupon> coupons = customer_couponService.getCustomer_coupon(customer_id);
		return new ResponseEntity<List<Customer_coupon>>(coupons, HttpStatus.OK);
	}
	
	@RequestMapping("/android/downloadcoupon")
	public String coupondownload(HttpServletRequest request) {
		String json = request.getParameter("coupon");
		//String ex = "[{\"customer_id\":\"jsg\",\"cafe_id\":\"grazie\",\"coupon_name\":\"대박할인\",\"coupon_num\":\"000\"};
		
		JSONArray arr = new JSONArray(json);
		JSONObject obj = arr.getJSONObject(0);
		Customer_coupon coupon = new Customer_coupon();
		coupon.setCafe_id(obj.getString("cafe_id"));
		coupon.setCafe_name(obj.getString("cafe_name"));
		coupon.setCustomer_id(obj.getString("customer_id"));
		coupon.setCoupon_name(obj.getString("coupon_name"));
		coupon.setCoupon_price(obj.getString("coupon_price"));
		coupon.setCoupon_start(obj.getString("coupon_start"));
		coupon.setCoupon_end(obj.getString("coupon_end"));
		
		customer_couponService.downloadCoupon(coupon);
		
		return "";
	}

	@RequestMapping("/android/usablecoupon")
	public ResponseEntity<List<Customer_coupon>> getOwncoupon(HttpServletRequest request) {
		String customer_id = request.getParameter("customer_id");
		String cafe_id = request.getParameter("cafe_id");
		List<Customer_coupon> coupons = customer_couponService.getCustomer_coupon(cafe_id, customer_id);
		return new ResponseEntity<List<Customer_coupon>>(coupons, HttpStatus.OK);
	}

	@RequestMapping("/android/option")
	public ResponseEntity<List<Opt>> getOption(HttpServletRequest request) {
		String cafe_id = request.getParameter("cafe_id");
		String category_name = request.getParameter("category_name");
		List<Opt> options = optService.getOpt(cafe_id, category_name);
		return new ResponseEntity<List<Opt>>(options, HttpStatus.OK);
	}
	
	@RequestMapping("/android/orderlists")
	public String getOrderlist(HttpServletRequest request) {
		String customer_id=null;
		String cafe_id = null;
		String menu_name;
		String hot_ice_none;
		String menu_size;
		String option_name;
		System.out.println("/android/orderlists");
		String token = request.getParameter("token");
		System.out.println(token+"!!!!!!!!!!!!!!!");
		String json = request.getParameter("orderlist");
		System.out.println(json+"!!!!!!!!!!!!!!!!!!!!!!!");
		//String ex = "[{\"customer_id\":\"jsg\",\"cafe_id\":\"grazie\",\"menu_name\":\"딸기 버블티\",\"hot_ice_none\":\"Iced\",\"menu_size\":\"only\",\"option_name\":\"\"},{\"customer_id\":\"jsg\",\"cafe_id\":\"grazie\",\"menu_name\":\"딸기주스\",\"hot_ice_none\":\"Iced\",\"menu_size\":\"only\",\"option_name\":\"\"},{\"coupon\":\"-500원\"}]";
		JSONArray jsonArray = new JSONArray(json);
		// orderlistJson
		org.json.JSONObject obj;
		for (int i = 0; i < jsonArray.length() - 1; i++) {
			obj = jsonArray.getJSONObject(i);
			System.out.println("orderlistJsonObj: " + obj.toString());
			// orderlist에 jsonObj추가
			Orderlist orderlist = new Orderlist();
			customer_id = obj.getString("customer_id");
			cafe_id = obj.getString("cafe_id");
			menu_name = obj.getString("menu_name");
			hot_ice_none = obj.getString("hot_ice_none");
			if (hot_ice_none.equals("Iced")) {
				hot_ice_none = "ice";
			}
			menu_size = obj.getString("menu_size");
			option_name = obj.getString("option_name");
			orderlist.setCafe_id(cafe_id);
			orderlist.setCustomer_id(customer_id);
			orderlist.setMenu_name(menu_name);
			orderlist.setMenu_size(menu_size);
			orderlist.setHot_ice_none(hot_ice_none);
			orderlist.setOption_name(option_name);
			orderlist.setToken(token);
			orderService.addOrderlist(orderlist);
		}
		// couponJson
		org.json.JSONObject couponJsonObj = jsonArray.getJSONObject(jsonArray.length() - 1);

		System.out.println("couponJSonObj: " + couponJsonObj.toString());
		String coupon_name = couponJsonObj.getString("coupon");
		if(!coupon_name.equals("선택해주세요.")) {
			customer_couponService.disableCoupon(customer_id, cafe_id, coupon_name);
		}

		return "";
	}

}
