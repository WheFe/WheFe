package kr.ac.hansung.whefe.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Customer_info {
	private String customer_id;
	private String customer_pw;
	private String customer_name;
	private String customer_phone;
	
	public Customer_info() {
		
	}
}
