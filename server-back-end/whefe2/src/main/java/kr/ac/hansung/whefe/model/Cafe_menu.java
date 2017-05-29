package kr.ac.hansung.whefe.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Cafe_menu {
	private String cafe_id;
	private String menu_name;
	private String hot_ice_none;
	private String menu_size;
	private String menu_price;
	// private String menu_price;
	private String category_name;
	private String menu_image;
	
	public Cafe_menu() {
		
	}
}
