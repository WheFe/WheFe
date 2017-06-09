package kr.ac.hansung.whefe.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Order {
	private int orderlist_id;
	private String cafe_id;
	private String customer_id;
	private String menu_name;
	private String category_name;
	private String option_info;
	private String menu_size;
	private String hotIceNone;
	private int menu_quantity;
	private boolean menu_completed;
	private String token;
}
