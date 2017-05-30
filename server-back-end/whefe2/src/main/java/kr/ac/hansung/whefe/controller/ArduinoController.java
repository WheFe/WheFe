package kr.ac.hansung.whefe.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.hansung.whefe.service.Cafe_infoService;
import kr.ac.hansung.whefe.service.OrderService;

@Controller
public class ArduinoController {
	
	@Autowired
	Cafe_infoService cafe_infoService;
	
	@Autowired
	OrderService orderService;
	
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public void countPeople(HttpServletRequest request, HttpServletResponse response){
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
        String count = request.getParameter("count").toString();
        String cafe_name = request.getParameter("cafe_name").toString();
        
        System.out.println("값들 : "+cafe_name + count);
        
        orderService.countPeople(cafe_name, count);
    }
}
