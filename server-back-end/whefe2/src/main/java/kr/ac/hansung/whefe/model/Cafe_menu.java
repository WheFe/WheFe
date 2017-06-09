package kr.ac.hansung.whefe.model;

import org.springframework.web.multipart.MultipartFile;

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
	private MultipartFile menu_image;
	private String imageFilename;
	
	public Cafe_menu() {
		
	}
}
