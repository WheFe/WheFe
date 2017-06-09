package kr.ac.hansung.whefe.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Orderlist {
	private int orderlist_id;
	private String cafe_id;
	private String customer_id;
	private String menu_name;
	private String hot_ice_none;
	private String menu_size;
	private String option_name;
	private String menu_completed;
	private String token;
}
