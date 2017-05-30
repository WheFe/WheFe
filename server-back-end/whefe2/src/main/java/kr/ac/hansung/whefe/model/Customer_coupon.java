package kr.ac.hansung.whefe.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Customer_coupon {

	private String customer_id;
	private String cafe_id;
	private String coupon_name;
	private String coupon_num;
}
