package kr.ac.hansung.whefe.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;
import com.google.code.geocoder.model.LatLng;

import kr.ac.hansung.whefe.dao.Cafe_infoDao;
import kr.ac.hansung.whefe.model.Cafe_info;

@Service
public class Cafe_infoService {
	
	
	private Cafe_infoDao cafe_infoDao;
	@Autowired
	public void setCafe_infoDao(Cafe_infoDao cafe_infoDao) {
		this.cafe_infoDao = cafe_infoDao;
	}
	
	public boolean addCafe_info(Cafe_info cafe_info) {
		System.out.println("Service!!!!!!" + cafe_info.toString());
		String location = "경기도 성남시 분당구 삼평동";
		//Float[] coords = geoCoding(location);
		//System.out.println(location + ": " + coords[0] + ", " + coords[1]);

		return cafe_infoDao.addProduct(cafe_info);
	}
	
	public boolean editCafe_info(Cafe_info cafe_info) {
		return cafe_infoDao.editCafe_info(cafe_info);
	}
	public List<Cafe_info> getCafe_info() {
		return cafe_infoDao.getCafe_info();
	}
	public Cafe_info selectAdmin(Cafe_info cafe_info) {
		System.out.println("selectAdmin!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return null;
	}
	public static Float[] geoCoding(String location) {
		if (location == null)  
		return null;
				       
		Geocoder geocoder = new Geocoder();
		// setAddress : 변환하려는 주소 (경기도 성남시 분당구 등)
		// setLanguate : 인코딩 설정
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(location).setLanguage("ko").getGeocoderRequest();
		GeocodeResponse geocoderResponse;

		try {
		geocoderResponse = geocoder.geocode(geocoderRequest);
		if (geocoderResponse.getStatus() == GeocoderStatus.OK & !geocoderResponse.getResults().isEmpty()) {

		GeocoderResult geocoderResult=geocoderResponse.getResults().iterator().next();
		LatLng latitudeLongitude = geocoderResult.getGeometry().getLocation();
						  
		Float[] coords = new Float[2];
		coords[0] = latitudeLongitude.getLat().floatValue();
		coords[1] = latitudeLongitude.getLng().floatValue();
		return coords;
		}
		} catch (IOException ex) {
		ex.printStackTrace();
		}
		return null;
		}





}
