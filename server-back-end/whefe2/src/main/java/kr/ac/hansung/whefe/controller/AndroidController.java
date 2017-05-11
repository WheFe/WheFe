package kr.ac.hansung.whefe.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AndroidController {

	@RequestMapping("/android/login")
	public String androidLogin(HttpServletRequest request) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		System.out.println(id+" " + pw);
		return "web-front-end/android";
	}
}
