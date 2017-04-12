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
}
