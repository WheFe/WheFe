package kr.ac.hansung.whefe.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.hansung.whefe.service.Cafe_infoService;

@Controller
public class ArduinoController {
	
	@Autowired
	Cafe_infoService cafe_infoService;
	
	@RequestMapping(value="/arduino")
	public String setCafe_curr(HttpServletRequest request) {
		System.out.println("setCafe_curr() Success!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return "web-front-end/test";
	}
}
