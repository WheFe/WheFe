package kr.ac.hansung.whefe.model;

import java.sql.Date;
import java.sql.Time;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Cafe_info {
	private String cafe_id;
	private String cafe_pw;
	private String cafe_name;
	private String cafe_address;
	private String cafe_tel;
	private String cafe_curr;
	private String cafe_max;
	private String cafe_open;
	private String cafe_end;
	private String cafe_intro;
	private MultipartFile cafe_image1;
	private MultipartFile cafe_image2;
	private MultipartFile cafe_image3;
	private String imageFilename1;
	private String imageFilename2;
	private String imageFilename3;
	
	public Cafe_info() {
		
	}
}
