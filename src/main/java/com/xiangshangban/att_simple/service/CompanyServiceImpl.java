package com.xiangshangban.att_simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.att_simple.bean.Company;
import com.xiangshangban.att_simple.dao.CompanyDao;
@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private CompanyDao companyDao;
	
	@Override
	public Company selectCompany(String companyId) {
		Company selectByCompany = companyDao.selectByCompany(companyId);
		return selectByCompany;
	}

}
