package kr.ac.hansung.whefe.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.ac.hansung.whefe.model.Customer_info;
import kr.ac.hansung.whefe.service.Customer_infoService;

@Controller
public class AndroidController {
	
	@Autowired
	private Customer_infoService customer_infoService;
	
	@RequestMapping("/android/login")
	@ResponseBody
	public Map<String,String> androidLogin(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		Map<String,String> result = new HashMap<String,String>();
		result.put("data1", "메모1입니다");
		result.put("data2", "메모2입니다");
		/*List<Customer_info> customers = customer_infoService.getCutomer_infoById(id);
		Customer_info customer_info = customers.get(0);
		if(id == customer_info.getCustomer_id()) {
			if(pw == customer_info.getCustomer_pw()) {
				success = true;
			}
		}*/
		System.out.println(id+" "+ pw);
		return result;
	}
}
