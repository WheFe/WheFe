package kr.ac.hansung.whefe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.whefe.dao.OptDao;
import kr.ac.hansung.whefe.model.Opt;

@Service
public class OptService {

		@Autowired
		private OptDao optDao;
		
		public void setOptDao(OptDao optDao) {
			this.optDao = optDao;
		}
		
		public List<Opt> getOpt(String category_name) {
			return optDao.getOpt(category_name);
		}
		
		public List<Opt> getOpt(String cafe_id, String category_name) {
			return optDao.getOpt(cafe_id, category_name);
		}
		
		public boolean addOption(Opt opt) {
			return optDao.addOption(opt);
		}
		
		public boolean editOption(String cafe_id, String original, String option_name, String option_price, String category_name) {
			return optDao.editOption(cafe_id, original, option_name, option_price, category_name);
		}
		
		public boolean deleteOption(String cafe_id, String original) {
			return optDao.deleteOption(cafe_id, original);
		}
}
