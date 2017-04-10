package kr.ac.hansung.whefe.controller;


import java.util.List;

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
	
	/*public void setCafe_infoService(Cafe_infoService cafe_infoService) {
		this.cafe_infoService = cafe_infoService;
	}*/
	
	@RequestMapping("/login") // 
	public String login(@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required=false) String logout, Model model) {
		
		if(error != null) {
			model.addAttribute("error", "Invalid ID and PASSWORD");
		}
		if(logout != null) {
			model.addAttribute("logout", "You have been logged out successfully");
		}
		
		return "web-front-end/01.First_Page";
	}
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginPost() {
		return "/management";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET) // 로그아웃 할 때
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";
	}
	
	@RequestMapping("/login/signup") // 회원가입 창 띄울 때
	public String signup(Model model, HttpServletRequest request) {
		
		return "02.Sign_Up";
	}
	
	@RequestMapping(value="/login/signup", method=RequestMethod.POST) // 회원가입 요청 할 때
	public String signupPost(Cafe_info cafe_info) {
		System.out.println(cafe_info.toString());
		if(!cafe_infoService.addCafe_info(cafe_info)) {
			System.out.println("Adding info cannot be done");
		}
		return "redirect:/login";
	}
	
	@RequestMapping("/login/denied") // 권한 없을 때
	public String denied() {
		return "denied";
	}
	@RequestMapping(value="/management")
	public String loginSuccess(Model model) {
		System.out.println("Controller!!!!!!!!!!!!");
		List<Category> categories = categoryService.getCategories();
		model.addAttribute("categories",categories);
		model.addAttribute("Test", "test");
		return "web-front-end/03.Menu-Management";
	}
	/*@RequestMapping("/login/duplicationCheck")
	public @ResponseBody Object duplicationCheck(@ModelAttribute("cafe_info") Cafe_info cafe_info) throws Exception {

		Cafe_info resultVO = cafe_infoService.selectAdmin(cafe_info);

		return resultVO;
	}*/
	@RequestMapping("/android")
	public void androidTest() {
		System.out.println("안드로이드");
	}
	
	@RequestMapping("/android2")
	public void androidTest(HttpServletRequest request) {
		System.out.println(request.getParameter("test"));
	}
}
