package kr.ac.hansung.whefe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.whefe.dao.Cafe_menuDao;
import kr.ac.hansung.whefe.model.Cafe_menu;

@Service
public class Cafe_menuService {
	
	@Autowired
	private Cafe_menuDao cafe_menuDao;
	
	/*public List<Cafe_menu> getCafe_menu(String category_name) {
		return cafe_menuDao.getCafe_menu(category_name);
	}*/
	
	public List<Cafe_menu> getCafe_menu(String cafe_id) {
		return cafe_menuDao.getCafe_menu(cafe_id);
	}
	
	public List<Cafe_menu> getCafe_menu(String cafe_id, String category_name) {
		return cafe_menuDao.getCafe_menu(cafe_id, category_name);
	}
	
	public List<Cafe_menu> getCafe_menu() {
		return cafe_menuDao.getCafe_menu();
	}
	
	public boolean addMenu(String[] param) {
		return cafe_menuDao.addMenu(param);
	}
	
	public boolean addMenu(ArrayList<String> pkArray, ArrayList<String> menu_nameArray, ArrayList<String> menu_sizeArray, ArrayList<String> hot_ice_noneArray, ArrayList<String> menu_priceArray) {
		return cafe_menuDao.addMenu(pkArray, menu_nameArray, menu_sizeArray, hot_ice_noneArray, menu_priceArray);
	}
	public boolean editMenu(ArrayList<String> pkArray, ArrayList<String> menu_nameArray, ArrayList<String> menu_sizeArray, ArrayList<String> hot_ice_noneArray, ArrayList<String> menu_priceArray) {
		System.out.println("editMenuService!!!!!!!!!!!!!!!");
		return cafe_menuDao.editMenu(pkArray, menu_nameArray, menu_sizeArray, hot_ice_noneArray, menu_priceArray);
	}
	
	public List<Cafe_menu> getMenu_price(String cafe_id, String menu_name) {
		return cafe_menuDao.getMenu_price(cafe_id,menu_name);
	}
	
	public boolean editMenu_name(String cafe_id,String menu_name,String new_name) {
		return cafe_menuDao.editMenu_name(cafe_id,menu_name,new_name);
	}
	
	public boolean deleteMenu(String cafe_id,String menu_name) {
		return cafe_menuDao.deleteMenu(cafe_id,menu_name);
	}
}
