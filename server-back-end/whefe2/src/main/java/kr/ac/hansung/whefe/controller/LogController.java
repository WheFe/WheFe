package kr.ac.hansung.whefe.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.ac.hansung.whefe.model.Cafe_info;
import kr.ac.hansung.whefe.model.Category;
import kr.ac.hansung.whefe.service.Cafe_infoService;
import kr.ac.hansung.whefe.service.CategoryService;

@Controller
public class LogController {
	@Autowired
	private Cafe_infoService cafe_infoService;
	@Autowired
	private CategoryService categoryService;

	/*
	 * public void setCafe_infoService(Cafe_infoService cafe_infoService) {
	 * this.cafe_infoService = cafe_infoService; }
	 */

	@RequestMapping("/login") //
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {

		if (error != null) {
			model.addAttribute("error", "Invalid ID and PASSWORD");
		}
		if (logout != null) {
			model.addAttribute("logout", "You have been logged out successfully");
		}

		return "web-front-end/01.First_Page";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPost() {
		return "/management";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET) // 로그아웃 할 때
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	@RequestMapping("/login/signup") // 회원가입 창 띄울 때
	public String signup(Model model, HttpServletRequest request) {
		return "web-front-end/02.Sign_Up3";
	}

	@RequestMapping(value = "/login/signup", method = RequestMethod.POST) // 때
	public String signupPost(Cafe_info cafe_info, HttpServletRequest request) {

		System.out.println(cafe_info.toString());
		MultipartFile cafe_image = cafe_info.getCafe_image();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Path savePath = Paths.get(rootDirectory + "\\resources\\images\\" + cafe_image.getOriginalFilename());
		if (cafe_image != null && !cafe_image.isEmpty()) {
			try {
				cafe_image.transferTo(new File(savePath.toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		cafe_info.setImageFilename(cafe_image.getOriginalFilename());

		if (cafe_image.isEmpty() == false) {
			System.out.println("---------file start ---------");
			System.out.println("name: " + cafe_image.getName());
			System.out.println("filename: " + cafe_image.getOriginalFilename());
			System.out.println("size: " + cafe_image.getSize());
			System.out.println("---------file end ---------");

		}

		if (!cafe_infoService.addCafe_info(cafe_info)) {
			System.out.println("Adding info cannot be done");
		}
		return "redirect:/login";
	}

	@RequestMapping("/login/denied") // 권한 없을 때
	public String denied(Model model, Authentication auth, HttpServletRequest req) {
		return "web-front-end/denied";
	}

	@RequestMapping(value = "/management")
	public String loginSuccess(Model model, Principal principal) {
		String cafe_id= principal.getName();
		//List<Category> categories = categoryService.getCategories();
		List<Category> categories = categoryService.getCategories(cafe_id);
		model.addAttribute("categories", categories);
		model.addAttribute("Test", "test");
		return "web-front-end/03.Menu-Management";
	}

	/*
	 * @RequestMapping("/login/duplicationCheck") public @ResponseBody Object
	 * duplicationCheck(@ModelAttribute("cafe_info") Cafe_info cafe_info) throws
	 * Exception {
	 * 
	 * Cafe_info resultVO = cafe_infoService.selectAdmin(cafe_info);
	 * 
	 * return resultVO; }
	 */
	/*
	 * @RequestMapping(value="/android") public String
	 * androidTest2(HttpServletRequest request) {
	 * System.out.println(request.toString()); System.out.println("안드로이드!!");
	 * return "androidTest"; }
	 */

	/*
	 * @RequestMapping(value="/android") public String
	 * androidTest3(HttpServletRequest request) { System.out.println("안드로이드!!");
	 * return "androidTest"; }
	 */

	/*@RequestMapping("/android")
	public String androidTest_HttpURLConnection(HttpServletRequest request) throws UnsupportedEncodingException {
		System.out.println("android Test!!!");
		request.setCharacterEncoding("UTF-8");
		// 서버측으로 넘어온 데이터를 추출
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String key1 = request.getParameter("key1");
		String key2 = request.getParameter("key2");
		System.out.println("request : " + id + " " + pw + "!!!!!!!!!!");
		System.out.println("request : " + key1 + " " + key2 + "!!!!!!!!!!");
		return "web-front-end/android";
	}

	@RequestMapping(value = "/android", method = RequestMethod.POST)
	public String androidTest_HttpURLConnectionPost(HttpServletRequest request) throws UnsupportedEncodingException {
		System.out.println("android Test!!!");
		request.setCharacterEncoding("UTF-8");
		// 서버측으로 넘어온 데이터를 추출
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		System.out.println("request : " + id + " " + pw + "!!!!!!!!!!");
		return "web-front-end/android";
	}

	@RequestMapping("/android2")
	public void androidTest() {
		System.out.println("android Test2!!!");
	}

	@RequestMapping("/android3")
	public void androidTestWithRequest(HttpServletRequest request) {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		System.out.println(id + "!!!!!!!!!!!!!!" + pwd);
	}*/

}
