package kr.ac.hansung.whefe.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import kr.ac.hansung.whefe.model.Cafe_info;
import kr.ac.hansung.whefe.service.Cafe_infoService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	private Cafe_infoService cafe_infoService;
	
	/*@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		return "web-front-end/home";
	}*/
	
	@RequestMapping(value="/pop")
	public String test() {
		return "web-front-end/pop";
	}
	
	@RequestMapping(value="/register")
	public String registerUser() {
		return "web-front-end/02.Sign_Up3";
	}
	
	@RequestMapping(value="/register", method= RequestMethod.POST)
	public String registerUserPost(Cafe_info cafe_info,HttpServletRequest req) {
		System.out.println(req.getParameter("cafe_address")+" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println(cafe_info.toString());
		if (!cafe_infoService.addCafe_info(cafe_info)) {
			System.out.println("Adding info cannot be done");
		}
		/*MultipartFile cafe_image = cafe_info.getCafe_image();
		if(cafe_image.isEmpty()==false) {
			System.out.println("---------file start ---------");
			System.out.println("name: " + cafe_image.getName());
			System.out.println("filename: " + cafe_image.getOriginalFilename());
			System.out.println("size: " + cafe_image.getSize());
			System.out.println("---------file end ---------");
			
		}*/
		return "redirect:/";
	}
	
	
	
}
