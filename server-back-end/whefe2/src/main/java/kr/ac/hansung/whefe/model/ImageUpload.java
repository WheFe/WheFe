package kr.ac.hansung.whefe.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ImageUpload {
	private MultipartFile menu_image;
	private String imageFilename;
	
	public ImageUpload() {
		
	}
	
}
