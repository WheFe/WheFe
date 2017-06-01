package kr.ac.hansung.whefe.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Opt {

	private String cafe_id;
	private String option_name;
	//private int option_price;
	private String option_price;
	private String category_name;
	
	public Opt() {
		
	}
}
